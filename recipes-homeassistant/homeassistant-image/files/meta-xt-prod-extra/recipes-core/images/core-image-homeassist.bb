SUMMARY = "Home Assistant domain"

require ${TOPDIR}/../meta-xt-rpi-common/inc/image-rpi-common.inc

IMAGE_FSTYPES = "tar.bz2 wic wic.bmap"


HOMEASSISTANT_SUPPORT = " \
    python3-homeassistant \
    python3-appdaemon \
"

IMAGE_INSTALL += " \
    systemd-machine-units \
    ${HOMEASSISTANT_SUPPORT} \
"

