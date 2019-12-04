APPDAEMON_CONFIG_DIR ?= "${localstatedir}/lib/appdaemon"

VOLATILE_BINDS += "\
    ${SMARTHOME_RPI_MNT_PERSIST}${sysconfdir}/${APPDAEMON_CONFIG_DIR} ${sysconfdir}/${APPDAEMON_CONFIG_DIR}\n\
"
