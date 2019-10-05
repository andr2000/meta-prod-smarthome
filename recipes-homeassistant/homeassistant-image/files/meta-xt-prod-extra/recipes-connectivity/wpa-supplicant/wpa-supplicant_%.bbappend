FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://wpa_supplicant@wlan0.service \
"

SYSTEMD_AUTO_ENABLE = "enable"

SYSTEMD_SERVICE_${PN}_append = "\
    wpa_supplicant@wlan0.service \
"

do_install_prepend () {
    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${S}/../wpa_supplicant@wlan0.service
    install -d ${D}/${systemd_unitdir}/system
    install -m 644 ${S}/../wpa_supplicant@wlan0.service ${D}/${systemd_unitdir}/system
}
