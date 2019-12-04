HOMEASSISTANT_CONFIG_DIR ?= "${localstatedir}/lib/homeassistant"

VOLATILE_BINDS += "\
    ${SMARTHOME_RPI_MNT_PERSIST}${sysconfdir}/${HOMEASSISTANT_CONFIG_DIR} ${sysconfdir}/${HOMEASSISTANT_CONFIG_DIR}\n\
"
