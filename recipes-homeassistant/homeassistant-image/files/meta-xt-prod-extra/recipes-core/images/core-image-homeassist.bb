SUMMARY = "Home Assistant domain"

require ${TOPDIR}/../meta-xt-rpi-common/inc/image-rpi-common.inc

HOMEASSISTANT_SUPPORT = " \
    python3-homeassistant \
"

APPDAEMON_SUPPORT = " \
    app-container-appdaemon \
    runc-opencontainers \
    netns \
"

VAILLANT_SUPPORT = " \
    ebusd \
    mosquitto \
    sqlite3 \
    ufh-controller \
    wpantund \
"

VAILLANT_OPTIONAL += " \
    andr2000-addons \
"

IMAGE_INSTALL += " \
    ${HOMEASSISTANT_SUPPORT} \
    ${APPDAEMON_SUPPORT} \
    ${VAILLANT_SUPPORT} \
    ${VAILLANT_OPTIONAL} \
"
