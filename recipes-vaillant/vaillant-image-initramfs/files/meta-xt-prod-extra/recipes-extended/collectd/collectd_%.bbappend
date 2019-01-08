DEPENDS += "mosquitto"

do_install_append() {
    # Update configuration file to read config from ${VAILLANT_MNT_SECRET}
    sed -i '/CONFIGFILE=/ c\CONFIGFILE=${VAILLANT_MNT_SECRET}/etc/collectd.conf' ${D}${sysconfdir}/init.d/collectd
    sed -i "s/VAILLANT_MNT_DATA/${VAILLANT_MNT_DATA}/g" ${D}${sysconfdir}/init.d/collectd
}

