FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS += "mosquitto"

SRC_URI_append = " \
    file://collectd.conf \
"

do_install_append() {
    install -d ${D}/${sysconfdir}
    install -m 0600 ${WORKDIR}/collectd.conf ${D}/${sysconfdir}/collectd.conf
    sed -i "s#SMARTHOME_RPI_MNT_PERSIST#${SMARTHOME_RPI_MNT_PERSIST}#g" ${D}/${sysconfdir}/collectd.conf
}

