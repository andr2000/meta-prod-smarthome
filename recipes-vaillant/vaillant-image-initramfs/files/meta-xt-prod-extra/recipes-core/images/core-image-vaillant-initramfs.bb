SUMMARY = "Valliant controlling domain"
LICENSE = "MIT"

inherit deploy
inherit core-image

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
    python3 \
"

TSDB_SUPPORT += " \
    github.com-influxdata-influxdb \
    github.com-influxdata-telegraf \
    grafana-go \
"
IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_EXTRAS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${WIFI_SUPPORT} \
    ${VAILLANT_SUPPORT} \
    ${TSDB_SUPPORT} \
"

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
 "

IMAGE_ROOTFS_SIZE = "65535"

make_initrd_symlink() {
    ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio.gz ${DEPLOY_DIR_IMAGE}/initrd
}

IMAGE_POSTPROCESS_COMMAND += " make_initrd_symlink; "

create_mnt_points() {
    mkdir -p ${IMAGE_ROOTFS}/mnt/secret
    mkdir -p ${IMAGE_ROOTFS}/mnt/data
}

IMAGE_PREPROCESS_COMMAND += " create_mnt_points; "
