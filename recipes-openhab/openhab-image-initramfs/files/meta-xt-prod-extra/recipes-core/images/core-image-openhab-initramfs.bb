SUMMARY = "OpenHab domain"
LICENSE = "MIT"

inherit deploy
inherit core-image
inherit extrausers

EXTRA_USERS_PARAMS = "usermod -P root root;"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = ""

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

BAD_RECOMMENDATIONS += "busybox-syslog"

DEPENDS += "mkbootimg-tools-native"

CORE_OS = " \
    kernel-modules \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    tzdata \
"

WIFI_SUPPORT = " \
    crda \
    iw \
    wpa-supplicant \
"

DEV_EXTRAS = " \
    ntp \
    ntp-tickadj \
"

EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    dosfstools \
    ethtool \
    findutils \
    iproute2 \
    iptables \
    less \
    netcat \
    procps \
    rng-tools \
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

# Kyiv
set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EET-2EEST ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
 "

IMAGE_ROOTFS_SIZE = "65535"

create_mnt_points() {
    mkdir -p ${IMAGE_ROOTFS}/mnt/secret
    mkdir -p ${IMAGE_ROOTFS}/mnt/data
}

IMAGE_PREPROCESS_COMMAND += " create_mnt_points; "

make_initrd_symlink() {
    rm -f ${DEPLOY_DIR_IMAGE}/initrd
    ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio.gz ${DEPLOY_DIR_IMAGE}/initrd
}

MKBOOTIMG_ARGS ?= ""

make_boot_image() {
    mkbootimg -o ${DEPLOY_DIR_IMAGE}/boot.img \
        --kernel ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} \
        --ramdisk ${DEPLOYDIR}-image-complete/${IMAGE_LINK_NAME}.cpio.gz \
        --cmdline "bootopt=64S3,32N2,32N2" --board "1543837483" \
        --base 0x40000000 --pagesize 2048 --ramdisk_offset 0x04000000 \
        --tags_offset 0x0e000000 \
        ${MKBOOTIMG_ARGS}
}

IMAGE_POSTPROCESS_COMMAND += " make_initrd_symlink; make_boot_image; "
