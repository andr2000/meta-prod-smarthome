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

WKS_FILE = "sdimage-homeassistant.wks"

# Do not update /etc/fstab
WIC_CREATE_EXTRA_ARGS_append = " --no-fstab-update"

# Main root file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_main=${IMAGE_ROOTFS}"

# Overlay file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_overlay=${IMAGE_ROOTFS}/../rootfs-overlay"

# Secret file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_secret=${IMAGE_ROOTFS}/../rootfs-secret"
