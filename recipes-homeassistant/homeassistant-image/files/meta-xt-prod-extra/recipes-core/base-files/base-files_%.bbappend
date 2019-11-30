hostname = "homeassist"

MNT_POINT ?= "mmcblk0p"

pkg_postinst_ontarget_${PN} () {
    echo "---------------- Resize data partition to 100% ----------------"
    pnum=`cat /etc/fstab | grep -o '.*${MNT_POINT}[0-9].*data' | grep -o '[0-9]\+' | tail -1`
    if [ -z ${pnum} ]; then
        echo "No data partition found."
        exit 1
    fi
    /bin/umount -f /dev/${MNT_POINT}${pnum} || true
    echo ", +" | /usr/sbin/sfdisk --force -N $pnum /dev/${MNT_DEV}
    /usr/sbin/partx -u /dev/${MNT_DEV}
    /sbin/mkfs.ext4 -F -O 64bit /dev/${MNT_POINT}${pnum}
    /bin/mount /dev/${MNT_POINT}${pnum}
}

RDEPENDS_${PN} += "\
    e2fsprogs-mke2fs \
"

do_install_append() {
	echo "/dev/${MNT_POINT}5 ${SMARTHOME_RPI_MNT_OVERLAY} auto defaults,sync,rw 0 0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${MNT_POINT}6 ${SMARTHOME_RPI_MNT_SECRET} auto defaults,sync,ro 0 0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${MNT_POINT}4 ${SMARTHOME_RPI_MNT_DATA} auto defaults,sync 0 1" >> ${D}${sysconfdir}/fstab
}
