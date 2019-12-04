SUMMARY = "Home Assistant domain"

require ${TOPDIR}/../meta-xt-rpi-common/inc/image-rpi-common.inc

IMAGE_FSTYPES = "tar.bz2 wic wic.bmap"


HOMEASSISTANT_SUPPORT = " \
    python3-homeassistant \
"

APPDAEMON_SUPPORT = " \
    app-container-appdaemon \
    runc-opencontainers \
    netns \
"

IMAGE_INSTALL += " \
    andr2000-addons \
    ${HOMEASSISTANT_SUPPORT} \
    ${APPDAEMON_SUPPORT} \
"

WKS_FILE = "sdimage-homeassistant.wks"
