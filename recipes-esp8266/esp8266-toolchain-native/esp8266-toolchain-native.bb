DESCRIPTION = "ESP8266 toolchain"
LICENSE = "CLOSED"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit native

PR = "r0"

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"

ENV_BASE_NAME="environment-setup"

# Fetch recursively
SRC_URI = " \
    gitsm://github.com/pfalcon/esp-open-sdk.git \
    file://${ENV_BASE_NAME} \
    file://0001-Add-vendor-SDK-2.2.0.patch \
    file://0002-Add-support-for-SDK-version-3.0.0.patch \
"
PROVIDES = "${PN}"

TARGET_NAME = "xtensa-lx106-elf"
TARGET_PREFIX = "${TARGET_NAME}-"

#sudo apt-get install make unrar-free autoconf automake libtool gcc g++ gperf \
#    flex bison texinfo gawk ncurses-dev libexpat-dev python-dev python python-serial \
#    sed git unzip bash help2man wget bzip2

do_compile() {
    cd ${S}
    export MAKE=`which make`
    unset CC CFLAGS CXXFLAGS LDFLAGS
    make STANDALONE=y
}

do_install() {
    install -d ${D}
    cp -rf ${S}/${TARGET_NAME} ${D}/
    install -d ${D}/sdk
    cp -rfL ${S}/sdk ${D}/

    # Write environment setup file
    local SRC_ENV_FILE="${S}/../${ENV_BASE_NAME}"
    sed -i "s!REPLACE_TARGET_NAME!${TARGET_NAME}!g" ${SRC_ENV_FILE}
    install -m 0744 ${SRC_ENV_FILE} "${D}/${ENV_BASE_NAME}-${TARGET_NAME}"

    # Scipt in shared folder is used by recipes, so change
    # the default toolchain path to point to the real toolchain/SDK
    # built during this build.
    sed -i "s!=\"REPLACE_DEFAULT_PATH!"=\"${D}"!g" ${SRC_ENV_FILE}

    install -d "${XT_SHARED_ROOTFS_DIR}/${PN}"
    install -m 0744 ${SRC_ENV_FILE} "${XT_SHARED_ROOTFS_DIR}/${PN}/${ENV_BASE_NAME}-${TARGET_NAME}"
}



do_populate_sdk() {
    install -d ${DEPLOY_DIR}/${PN}
    tar cjf ${DEPLOY_DIR}/${PN}/${PN}.tar.bz2 -C ${D} .
}

FILES_${PN} = "\
    ${D}/* \
"

