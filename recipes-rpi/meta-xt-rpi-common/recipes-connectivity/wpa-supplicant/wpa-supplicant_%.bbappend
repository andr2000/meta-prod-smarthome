FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://wpa_supplicant.conf \
"

FILES_${PN} += " \
    ${SMARTHOME_RPI_MNT_SECRET}${sysconfdir}/* \
"

do_install_append () {
    install -d ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}
    install -m 600 ${WORKDIR}/wpa_supplicant.conf ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf

    sed -i "s#SMARTHOME_TELEMETRY_SSID#${SMARTHOME_TELEMETRY_SSID}#g" ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf
    # TODO: Use ~ as delimeter as password may contain special symbols
    sed -i "s~SMARTHOME_TELEMETRY_PWD~${SMARTHOME_TELEMETRY_PWD}~g" ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf
}
