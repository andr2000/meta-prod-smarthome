FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://wpa_supplicant.conf \
"

FILES_${PN} += " \
    ${VAILLANT_MNT_SECRET}${sysconfdir}/* \
"

do_install_append () {
    install -d ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}
    install -m 600 ${WORKDIR}/wpa_supplicant.conf ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf

    sed -i "s#SMARTHOME_TELEMETRY_SSID#${SMARTHOME_TELEMETRY_SSID}#g" ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf
    sed -i "s#SMARTHOME_TELEMETRY_PWD#${SMARTHOME_TELEMETRY_PWD}#g" ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/wpa_supplicant.conf
}
