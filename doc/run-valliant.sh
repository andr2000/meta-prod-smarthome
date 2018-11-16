#!/bin/bash -e
URL=https://raw.githubusercontent.com/dhruvvyas90/qemu-rpi-kernel/master/
KERNEL=kernel-qemu-4.14.50-stretch
DTB=versatile-pb.dtb

# This is the kernel that runs under QEMU + VersatilePB
if [ ! -f ${KERNEL} ]; then
    curl -OL ${URL}/${KERNEL}
fi

if [ ! -f ${DTB} ]; then
    curl -OL ${URL}/${DTB}
fi

usage()
{
    me=`basename "$0"`

    echo "${me} -i|--initrd <initrd>"
    echo "    initrd        path to cpio.gz initrd file"

    if [ -n "$1" ]; then
        echo "$1"
    fi

    exit 1
}

run_qemu()
{
    qemu-system-arm \
        -M versatilepb \
        -append "rw earlyprintk loglevel=8 console=ttyAMA0,115200 dwc_otg.lpm_enable=0 root=/dev/ram" \
        -cpu arm1176 \
        -kernel ${KERNEL} \
        -dtb ${DTB} \
        -initrd ${INITRD} \
        -m 256M \
        -nographic \
        -net user,hostfwd=tcp::5022-:22 \
        -net nic \
        -hda /dev/loop0 \
        ;
    }

OPTIONS=i
LONGOPTS=initrd:

! PARSED=$(getopt --options=$OPTIONS --longoptions=$LONGOPTS --name "$0" -- "$@")
if [[ ${PIPESTATUS[0]} -ne 0 ]]; then
    usage
    exit 1
fi

eval set -- "$PARSED"

INITRD=""

while true; do
    case "$1" in
        -i|--initrd)
            INITRD="$2"
            shift 2
            ;;
        --)
            shift
            break
            ;;
        *)
            usage
            exit 1
            ;;
    esac
done

echo "Using initrd file: '$INITRD'"
echo "Use Ctrl-A X to terminate QEMU"
echo "Use ssh root@localhost -p 5022 to connect to the target"

run_qemu
