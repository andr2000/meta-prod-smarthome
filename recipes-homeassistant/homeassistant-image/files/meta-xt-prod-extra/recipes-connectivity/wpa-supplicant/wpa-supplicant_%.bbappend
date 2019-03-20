FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://wpa_supplicant@wlan0.service \
"

FILES_${PN} += " \
    /lib/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service \
    /lib/systemd/system/wpa_supplicant@wlan0.service \
"

do_install_append () {
    install -d ${D}/${systemd_unitdir}/system
    install -m 644 ${S}/../wpa_supplicant@wlan0.service ${D}/${systemd_unitdir}/system
    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${D}/${systemd_unitdir}/system/wpa_supplicant@wlan0.service

    # if SYSTEMD_SERVICE_${PN}/SYSTEMD_AUTO_ENABLE are used then we have
    # uneeded services. Install ours manually then.
    install -d ${D}/${systemd_unitdir}/system/multi-user.target.wants/
    ln -sf /lib/systemd/system/wpa_supplicant@wlan0.service ${D}/${systemd_unitdir}/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}
