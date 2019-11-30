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
    bash \
    ${HOMEASSISTANT_SUPPORT} \
    ${APPDAEMON_SUPPORT} \
"

PACKAGE_OVERLAY_ROOTFS_INSTALL += " \
    rsync \
"

WKS_FILE = "sdimage-homeassistant.wks"

# Do not update /etc/fstab
WIC_CREATE_EXTRA_ARGS_append = " --no-fstab-update"

# Main root file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_main=${IMAGE_ROOTFS}"

# Overlay file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_overlay=${IMAGE_ROOTFS}/../rootfs-overlay"

# Secret file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_secret=${IMAGE_ROOTFS}/../rootfs-secret"
