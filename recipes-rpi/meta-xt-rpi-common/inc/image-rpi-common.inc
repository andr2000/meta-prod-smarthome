LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit deploy
inherit core-image
inherit overlay_rootfs

DEPENDS += "bcm2835-bootfiles"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = ""

BAD_RECOMMENDATIONS += "busybox-syslog"

CORE_OS = " \
    kernel-modules \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    term-prompt \
    tzdata \
"
WIFI_SUPPORT = " \
    crda \
    iw \
    linux-firmware-raspbian \
    wpa-supplicant \
"

DEV_EXTRAS = " \
    ntp \
    ntp-tickadj \
    serialecho  \
    screen \
"

EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    devmem2 \
    dosfstools \
    ethtool \
    findutils \
    i2c-tools \
    iproute2 \
    iptables \
    less \
    netcat \
    procps \
    rndaddtoentcnt \
    rng-tools \
    sysfsutils \
    unzip \
    util-linux \
    wget \
    zip \
"
# These packages are removed from the rootfs and installed
# into the overlay
PACKAGE_OVERLAY_ROOTFS_INSTALL += " \
    git \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_EXTRAS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${WIFI_SUPPORT} \
    ${PACKAGE_OVERLAY_ROOTFS_INSTALL} \
"

# Exclude from initramfs contents of the rootfs-overlay
PACKAGE_EXCLUDE += "${PACKAGE_OVERLAY_ROOTFS_INSTALL}"

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

ROOTFS_POSTPROCESS_COMMAND += " \
    disable_bootlogd ; \
 "

create_mnt_points() {
    mkdir -p ${IMAGE_ROOTFS}${SMARTHOME_RPI_MNT_OVERLAY}
    mkdir -p ${IMAGE_ROOTFS}${SMARTHOME_RPI_MNT_SECRET}
    mkdir -p ${IMAGE_ROOTFS}${SMARTHOME_RPI_MNT_DATA}
}

IMAGE_PREPROCESS_COMMAND += " create_mnt_points; "

# Force do rootfs as we might have moved /mnt/{data|secret} already
do_rootfs[nostamp] = "1"

install_mk_sdcard_script() {
    local LAYERDIR=${TOPDIR}/../meta-xt-prod-extra
    install -m 0755 ${TOPDIR}/../meta-xt-rpi-common/doc/mk_sdcard_image_vaillant.sh ${DEPLOY_DIR}/
}

do_image_complete_append () {
    bb.build.exec_func("install_mk_sdcard_script", d)
}
