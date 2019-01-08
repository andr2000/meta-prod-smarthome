FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS += "mosquitto"

SRC_URI = " \
    file://collectd.conf \
"

do_install_append() {
    install -d ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}
    install -m 0600 ${WORKDIR}/collectd.conf ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/collectd.conf
    sed -i "s#VAILLANT_MNT_DATA#${VAILLANT_MNT_DATA}#g" ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/collectd.conf
}

