FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append() {
    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${D}${sysconfdir}/network/interfaces
}
