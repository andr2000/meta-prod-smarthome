SUMMARY = "Valliant controlling domain"

require ${TOPDIR}/../meta-xt-rpi-common/inc/image-rpi-common.inc

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
    ${VAILLANT_SUPPORT} \
    ${VAILLANT_OPTIONAL} \
"
