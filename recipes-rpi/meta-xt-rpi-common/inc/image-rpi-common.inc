LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit deploy
inherit core-image

DEPENDS += "bcm2835-bootfiles"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_INSTALL_append = " initscripts-readonly-rootfs-overlay"

IMAGE_FSTYPES = "tar.bz2 wic wic.bmap"

WKS_FILE = "sdimage-smarthome.wks"

# Do not update /etc/fstab
WIC_CREATE_EXTRA_ARGS_append = " --no-fstab-update"

IMAGE_LINGUAS = ""

BAD_RECOMMENDATIONS += "busybox-syslog"

CORE_OS = " \
    kernel-modules \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    systemd-machine-units \
    tzdata \
    cronie \
    logrotate \
    secrets \
"
WIFI_SUPPORT = " \
    crda \
    iw \
    linux-firmware-raspbian \
    wpa-supplicant \
"

DEV_EXTRAS = " \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "ntp", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "", "ntp-tickadj", d)} \
    serialecho  \
    screen \
"

EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    devmem2 \
    dosfstools \
    ethtool \
    findutils \
    git \
    i2c-tools \
    iproute2 \
    iptables \
    less \
    netcat \
    procps \
    rndaddtoentcnt \
    rng-tools \
    strace \
    sysfsutils \
    unzip \
    util-linux \
    wget \
    zip \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_EXTRAS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${WIFI_SUPPORT} \
"

create_mnt_points() {
    mkdir -p ${IMAGE_ROOTFS}${SMARTHOME_RPI_MNT_PERSIST}
}

IMAGE_PREPROCESS_COMMAND += " create_mnt_points; "
