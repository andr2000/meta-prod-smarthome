FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

FILES_${PN} += " \
    ${SMARTHOME_RPI_MNT_SECRET}/ssh \
"

do_install_append() {
    # This folder will contain ssh keys
    install -d ${D}${SMARTHOME_RPI_MNT_SECRET}/ssh

    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${D}${sysconfdir}/ssh/sshd_config
}
