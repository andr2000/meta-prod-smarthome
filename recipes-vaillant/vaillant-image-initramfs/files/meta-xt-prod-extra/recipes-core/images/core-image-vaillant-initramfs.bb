SUMMARY = "Valliant controlling domain"

require ${TOPDIR}/../meta-xt-rpi-common/inc/image-rpi-common.inc

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES} wic wic.bmap"

DEV_EXTRAS += " \
    experimental \
"

VAILLANT_SUPPORT = " \
    ebusd \
    mosquitto \
    sqlite3 \
    ufh-controller \
"

# These packages are removed from the initramfs and installed
# into the overlay
PACKAGE_OVERLAY_ROOTFS_INSTALL += " \
    andr2000-addons \
"

IMAGE_INSTALL += " \
    ${VAILLANT_SUPPORT} \
"

# Allow big rootfs as it may contain /opt + /mnt/{data|secret}
IMAGE_ROOTFS_SIZE = "1048576"
INITRAMFS_MAXSIZE = "1048576"

make_initrd_symlink() {
    ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio.gz ${DEPLOY_DIR_IMAGE}/initrd
}

IMAGE_POSTPROCESS_COMMAND += " make_initrd_symlink; "

WKS_FILE = "sdimage-vaillant.wks"

# Do not update /etc/fstab
WIC_CREATE_EXTRA_ARGS_append = " --no-fstab-update"

# Main root file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_main=${IMAGE_ROOTFS}"

# Overlay file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_overlay=${IMAGE_ROOTFS}/../rootfs-overlay"

# Secret file system
WIC_CREATE_EXTRA_ARGS_append = " --rootfs-dir rootfs_secret=${IMAGE_ROOTFS}/../rootfs-secret"
