FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://wpa_supplicant-wext@.service \
"

FILES_${PN} += " \
    /lib/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service \
"

SYSTEMD_SERVICE_${PN} += " \
    wpa_supplicant-wext@.service \
"

do_install_append () {
    install -d ${D}/${systemd_unitdir}/system
    install -m 644 ${S}/../wpa_supplicant-wext@.service ${D}/${systemd_unitdir}/system
    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${D}/${systemd_unitdir}/system/wpa_supplicant-wext@.service

    install -d ${D}/${systemd_unitdir}/system/multi-user.target.wants/
    ln -sf /lib/systemd/system/wpa_supplicant-wext@.service ${D}/${systemd_unitdir}/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}
