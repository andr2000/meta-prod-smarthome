#!/bin/bash -e

MOUNT_POINT="/tmp/mntpoint"
CUR_STEP=1

usage()
{
	echo "`basename "$0"` <-p image-folder> <-d image-file> [-s image-size-gb]"
	echo "	-p image-folder	Yocto deploy folder where artifacts live"
	echo "	-d image-file	Output image file or physical device"
	echo "	-s image-size	Optional, image size in GB"

	exit 1
}

print_step()
{
	local caption=$1
	echo "###############################################################################"
	echo "Step $CUR_STEP: $caption"
	echo "###############################################################################"
	((CUR_STEP++))
}

###############################################################################
# Inflate image
###############################################################################
inflate_image()
{
	local dev=$1
	local size_gb=$2

	print_step "Inflate image"

	if  [ -b "$dev" ] ; then
		echo "Using physical block device $dev"
		return 0
	fi

	echo "Inflating image file at $dev of size ${size_gb}GB"

	local inflate=1
	if [ -e $1 ] ; then
		echo ""
		read -r -p "File $dev exists, remove it? [y/N]:" yesno
		case "$yesno" in
		[yY])
			sudo rm -f $dev || exit 1
		;;
		*)
			echo "Reusing existing image file"
			inflate=0
		;;
		esac
	fi
	if [[ $inflate == 1 ]] ; then
		sudo dd if=/dev/zero of=$dev bs=1M count=$(($size_gb*1024)) || exit 1
	fi
}

###############################################################################
# Partition image
###############################################################################
partition_image()
{
	print_step "Make partitions"

	sudo parted -s $1 mklabel msdos

	# Skip first 1MiB
	# 1. Boot partition
	# 2. Secrets's partition
	# 3. All the reset
	sudo parted -s $1 mkpart primary fat32 1MiB 256MiB
	sudo parted -s $1 mkpart primary ext4 256MiB 264MiB
	sudo parted -s $1 mkpart primary ext4 264MiB 100%

	sudo partprobe $1
}

###############################################################################
# Make file system
###############################################################################

mkfs_one()
{
	local img_output_file=$1
	local loop_base=$2
	local part=$3
	local label=$4
	local loop_dev="${loop_base}p${part}"

	print_step "Making ext4 filesystem for $label"

	sudo mkfs.ext4 -O ^64bit -F $loop_dev -L $label
}

mkfs_boot()
{
	local img_output_file=$1
	local loop_base=$2
	local part=1
	local label=BOOT
	local loop_dev="${loop_base}p${part}"

	print_step "Making FAT32 filesystem for $label"

	sudo mkfs.vfat -F 32 $loop_dev -n $label
}

mkfs_secret()
{
	local img_output_file=$1
	local loop_dev=$2

	mkfs_one $img_output_file $loop_dev 2 secret
}

mkfs_data()
{
	local img_output_file=$1
	local loop_dev=$2

	mkfs_one $img_output_file $loop_dev 3 data
}

mkfs_image()
{
	local img_output_file=$1
	local loop_dev=$2
	sudo losetup -P $loop_dev $img_output_file

	mkfs_boot $img_output_file $loop_dev
	mkfs_secret $img_output_file $loop_dev
	mkfs_data $img_output_file $loop_dev
}

###############################################################################
# Mount partition
###############################################################################

mount_part()
{
	local loop_base=$1
	local img_output_file=$2
	local part=$3
	local mntpoint=$4
	local loop_dev=${loop_base}p${part}

	mkdir -p "${mntpoint}" || true
	sudo mount $loop_dev "${mntpoint}"
}

umount_part()
{
	local loop_base=$1
	local part=$2
	local loop_dev=${loop_base}p${part}

	sudo umount $loop_dev
}

###############################################################################
# Unpack
###############################################################################

###############################################################################
# This comes from meta-rpi
###############################################################################
get_bootfiles()
{
	local MACHINE=$1

	case "${MACHINE}" in
		raspberrypi|raspberrypi0|raspberrypi0-wifi|raspberrypi-cm)
			DTBS="bcm2708-rpi-0-w.dtb \
			      bcm2708-rpi-b.dtb \
			      bcm2708-rpi-b-plus.dtb \
			      bcm2708-rpi-cm.dtb"
		;;
		raspberrypi2|raspberrypi3|raspberrypi-cm3)
			DTBS="bcm2709-rpi-2-b.dtb \
			      bcm2710-rpi-3-b.dtb \
			      bcm2710-rpi-cm3.dtb"
		;;
		*)
			echo "Invalid MACHINE: ${MACHINE}"
			exit 1
	esac

	BOOTLDRFILES="bootcode.bin \
		      cmdline.txt \
		      config.txt \
		      fixup_cd.dat \
		      fixup.dat \
		      fixup_db.dat \
		      fixup_x.dat \
		      start_cd.elf \
		      start_db.elf \
		      start.elf \
		      start_x.elf"

	export DTBS BOOTLDRFILES
}

unpack_boot()
{
	local db_base_folder=$1
	local loop_base=$2
	local img_output_file=$3

	local part=1

	print_step "Unpacking BOOT"

	local valliant_name=`ls $db_base_folder | grep valliant`
	local valliant_root=$db_base_folder/$valliant_name

	local Image=`find $valliant_root -name zImage`
	local initrd=`find $valliant_root -name initrd`
	local initrd_dest=`readlink $initrd`
	local deploy_dir=`dirname $initrd`
	local MACHINE=`echo $initrd_dest | sed -e 's/\([^\.]*\).*/\1/;s/-[0-9]*$//' | sed 's/^.*\(raspberry.*\).*$/\1/'`

	echo "MACHINE is $MACHINE"
	echo "Valliant kernel image is at $Image"
	echo "Valliant initramfs is at $initrd"

	get_bootfiles $MACHINE

	mount_part $loop_base $img_output_file $part $MOUNT_POINT

	sudo mkdir "${MOUNT_POINT}/overlays" || true

	for f in $Image $initrd ; do
		sudo cp -L $f "${MOUNT_POINT}/"
	done

	for f in $BOOTLDRFILES ; do
		sudo cp -L $deploy_dir/bcm2835-bootfiles/$f "${MOUNT_POINT}/"
	done

	for f in $DTBS ; do
		sudo cp -L $deploy_dir/$f "${MOUNT_POINT}/"
	done

	umount_part $loop_base $part
}

###############################################################################
# This is from /usr/libexec/openssh/sshd_check_keys
###############################################################################
generate_key() {
	local FILE=$1
	local TYPE=$2
	local DIR="$(dirname "$FILE")"

	sudo ssh-keygen -q -f "${FILE}.tmp" -N '' -t $TYPE

	# Atomically rename file public key
	sudo mv -f "${FILE}.tmp.pub" "${FILE}.pub"

	# This sync does double duty: Ensuring that the data in the temporary
	# private key file is on disk before the rename, and ensuring that the
	# public key rename is completed before the private key rename, since we
	# switch on the existence of the private key to trigger key generation.
	# This does mean it is possible for the public key to exist, but be garbage
	# but this is OK because in that case the private key won't exist and the
	# keys will be regenerated.
	#
	# In the event that sync understands arguments that limit what it tries to
	# fsync(), we provided them. If it does not, it will simply call sync()
	# which is just as well
	sync "${FILE}.pub" "$DIR" "${FILE}.tmp"

	sudo mv "${FILE}.tmp" "$FILE"

	# sync to ensure the atomic rename is committed
	sync "$DIR"
}

unpack_secret()
{
	local loop_base=$2
	local img_output_file=$3
	local part=2

	print_step "Unpacking secrets"

	mount_part $loop_base $img_output_file $part $MOUNT_POINT

	local ssh_dir="${MOUNT_POINT}/ssh"
	sudo mkdir "$ssh_dir" || true

	echo "Generating ssh RSA key..."
	generate_key $ssh_dir/ssh_host_rsa_key rsa

	echo "Generating ssh ECDSA key..."
	generate_key $ssh_dir/ssh_host_ecdsa_key ecdsa

	echo "Generating ssh DSA key..."
	generate_key $ssh_dir/ssh_host_dsa_key dsa

	echo "Generating ssh ED25519 key..."
	generate_key $ssh_dir/ssh_host_ed25519_key ed25519

	umount_part $loop_base $part
}

unpack_image()
{
	local db_base_folder=$1
	local loop_dev=$2
	local img_output_file=$3

	unpack_boot $db_base_folder $loop_dev $img_output_file
	unpack_secret $db_base_folder $loop_dev $img_output_file
}

###############################################################################
# Common
###############################################################################

make_image()
{
	local db_base_folder=$1
	local img_output_file=$2
	local image_sg_gb=${3:-5}
	local loop_dev="/dev/loop0"

	print_step "Preparing image at ${img_output_file}"

	if [ ! -d "${MOUNT_POINT}" ]; then
		mkdir "${MOUNT_POINT}"
	else
		sudo umount -f ${MOUNT_POINT} || true
	fi
	ls ${img_output_file}?* | xargs -n1 sudo umount -l -f || true

	inflate_image $img_output_file $image_sg_gb
	partition_image $img_output_file
	mkfs_image $img_output_file $loop_dev
	unpack_image $db_base_folder $loop_dev $img_output_file

	sync
	sudo losetup -d $loop_dev || true
	print_step "Done"
}

print_step "Parsing input parameters"

while getopts ":p:d:s:u:" opt; do
	case $opt in
		p) ARG_DEPLOY_PATH="$OPTARG"
		;;
		d) ARG_DEPLOY_DEV="$OPTARG"
		;;
		s) ARG_IMG_SIZE_GB="$OPTARG"
		;;
		\?) echo "Invalid option -$OPTARG" >&2
		exit 1
		;;
	esac
done

if [ -z "${ARG_DEPLOY_PATH}" ]; then
	echo "No path to deploy directory passed with -p option"
	usage
fi

if [ -z "${ARG_DEPLOY_DEV}" ]; then
	echo "No device/file name passed with -d option"
	usage
fi

echo "Using deploy path: \"$ARG_DEPLOY_PATH\""
echo "Using device     : \"$ARG_DEPLOY_DEV\""

make_image $ARG_DEPLOY_PATH $ARG_DEPLOY_DEV $ARG_IMG_SIZE_GB

