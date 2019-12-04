hostname = "homeassist"

MNT_POINT ?= "mmcblk0p"

do_install_append() {
    echo "/dev/${MNT_POINT}3 ${SMARTHOME_RPI_MNT_PERSIST} auto defaults,sync,rw 0 0" >> ${D}${sysconfdir}/fstab
}
