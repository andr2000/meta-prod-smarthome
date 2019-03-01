SUMMARY = "Home Assistant domain"

require ${TOPDIR}/../meta-xt-rpi-common/inc/image-rpi-common.inc

HOMEASSISTANT_SUPPORT = " \
    python3-homeassistant \
    python3-appdaemon \
"

IMAGE_INSTALL += " \
    ${HOMEASSISTANT_SUPPORT} \
"

