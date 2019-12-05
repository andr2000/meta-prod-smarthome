FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://history-bind.sh \
"

FILES_${PN} += "${sysconfdir}/profile.d/history-bind.sh"

MNT_POINT ??= "mmcblk0p"
MNT_DEV = "${@(d.getVar('MNT_POINT'))[:-1]}"

do_install_append() {
	install -d ${D}${sysconfdir}/profile.d/
	install -m 0755 ${WORKDIR}/history-bind.sh ${D}${sysconfdir}/profile.d/

	echo "/dev/${MNT_POINT}4 ${SMARTHOME_RPI_MNT_PERSIST} auto defaults,sync,rw 0 0" >> ${D}${sysconfdir}/fstab
}

pkg_postinst_ontarget_${PN} () {
    echo "---------------- Resize persistent partition to 100% ----------------"
    pnum=`cat /etc/fstab | grep -o '.*${MNT_POINT}[0-9].*persist' | grep -o '[0-9]\+' | tail -1`
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
