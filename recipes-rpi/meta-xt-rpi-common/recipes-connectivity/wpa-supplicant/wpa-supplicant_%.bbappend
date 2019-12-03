FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://wpa_supplicant.conf \
    file://wpa_supplicant@wlan0.service \
"

SYSTEMD_AUTO_ENABLE = "enable"

SYSTEMD_SERVICE_${PN}_append = "\
    wpa_supplicant@wlan0.service \
"

do_install_append () {
    install -d ${D}/${sysconfdir}
    install -m 600 ${WORKDIR}/wpa_supplicant.conf ${D}/${sysconfdir}/wpa_supplicant.conf

    sed -i "s#SMARTHOME_TELEMETRY_SSID#${SMARTHOME_TELEMETRY_SSID}#g" ${D}/${sysconfdir}/wpa_supplicant.conf
    # TODO: Use ~ as delimeter as password may contain special symbols
    sed -i "s~SMARTHOME_TELEMETRY_PWD~${SMARTHOME_TELEMETRY_PWD}~g" ${D}/${sysconfdir}/wpa_supplicant.conf

    install -d ${D}/${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/wpa_supplicant@wlan0.service ${D}/${systemd_unitdir}/system
}
