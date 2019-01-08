FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append() {
    sed -i "s#VAILLANT_MNT_SECRET#${VAILLANT_MNT_SECRET}#g" ${D}${sysconfdir}/network/interfaces
}
