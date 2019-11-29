FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://wpa_supplicant.conf \
    file://wpa_supplicant@wlan0.service \
"

FILES_${PN} += " \
    ${SMARTHOME_RPI_MNT_SECRET}${sysconfdir}/* \
"

SYSTEMD_AUTO_ENABLE = "enable"

SYSTEMD_SERVICE_${PN}_append = "\
    wpa_supplicant@wlan0.service \
"

do_install_append () {
    install -d ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}
    install -m 600 ${WORKDIR}/wpa_supplicant.conf ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf

    sed -i "s#SMARTHOME_TELEMETRY_SSID#${SMARTHOME_TELEMETRY_SSID}#g" ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf
    # TODO: Use ~ as delimeter as password may contain special symbols
    sed -i "s~SMARTHOME_TELEMETRY_PWD~${SMARTHOME_TELEMETRY_PWD}~g" ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf

    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${S}/../wpa_supplicant@wlan0.service
    install -d ${D}/${systemd_unitdir}/system
    install -m 644 ${S}/../wpa_supplicant@wlan0.service ${D}/${systemd_unitdir}/system
}
