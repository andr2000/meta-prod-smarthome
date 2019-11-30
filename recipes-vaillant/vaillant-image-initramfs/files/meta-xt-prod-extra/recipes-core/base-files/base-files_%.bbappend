hostname = "vaillant"

MNT_POINT ?= "mmcblk0p"

do_install_append() {
	echo "/dev/${MNT_POINT}2 ${SMARTHOME_RPI_MNT_OVERLAY} auto defaults,sync,rw 0 0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${MNT_POINT}3 ${SMARTHOME_RPI_MNT_SECRET} auto defaults,sync,ro 0 0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${MNT_POINT}4 ${SMARTHOME_RPI_MNT_DATA} auto defaults,sync 0 1" >> ${D}${sysconfdir}/fstab
}
