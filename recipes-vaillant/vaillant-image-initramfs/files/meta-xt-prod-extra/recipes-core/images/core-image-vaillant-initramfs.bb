SUMMARY = "Valliant controlling domain"
LICENSE = "MIT"

inherit deploy
inherit core-image
inherit opt_component_image

DEPENDS += "bcm2835-bootfiles"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = ""

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

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
    experimental \
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
VAILLANT_SUPPORT = " \
    ebusd \
    ebusd-configuration \
    mosquitto \
    python \
"

TSDB_SUPPORT += " \
    github.com-influxdata-influxdb \
    github.com-influxdata-telegraf \
    grafana-go \
"

# These packages are removed from the initramfs and isnatlled
# into the overlay
PACKAGE_OVERLAY_ROOTFS_INSTALL += " \
    ${TSDB_SUPPORT} \
    python3 \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_EXTRAS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${WIFI_SUPPORT} \
    ${VAILLANT_SUPPORT} \
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

# Allow big rootfs as it may contain /opt + /mnt/{data|secret}
IMAGE_ROOTFS_SIZE = "1048576"
INITRAMFS_MAXSIZE = "32768"

make_initrd_symlink() {
    ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio.gz ${DEPLOY_DIR_IMAGE}/initrd
}

IMAGE_POSTPROCESS_COMMAND += " make_initrd_symlink; "

create_mnt_points() {
    mkdir -p ${IMAGE_ROOTFS}${VAILLANT_MNT_SECRET}
    mkdir -p ${IMAGE_ROOTFS}${VAILLANT_MNT_DATA}
}

IMAGE_PREPROCESS_COMMAND += " create_mnt_points; "

# Force do rootfs as we might have moved /opt and /mnt/{data|secret} already
do_rootfs[nostamp] = "1"
