DEPENDS += "mosquitto"

do_install_append() {
    # Update configuration file to read config from /mnt/secret
    sed -i '/CONFIGFILE=/ c\CONFIGFILE=/mnt/secret/etc/collectd.conf' ${D}${sysconfdir}/init.d/collectd
}

