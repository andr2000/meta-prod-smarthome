FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS += "mosquitto"

SRC_URI_append = " \
    file://collectd.conf \
"

FILES_${PN} += " \
    ${SMARTHOME_RPI_MNT_SECRET}${sysconfdir}/collectd.conf \
"

do_install_append() {
    install -d ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}
    install -m 0600 ${WORKDIR}/collectd.conf ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/collectd.conf
    sed -i "s#SMARTHOME_RPI_MNT_DATA#${SMARTHOME_RPI_MNT_DATA}#g" ${D}${SMARTHOME_RPI_MNT_SECRET}/${sysconfdir}/collectd.conf
}

