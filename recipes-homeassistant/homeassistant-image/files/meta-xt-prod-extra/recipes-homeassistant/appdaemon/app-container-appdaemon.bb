# Based on: "Building Container Images with OpenEmbedded and
# the Yocto Project" by Scott Murray <scott.murray@konsulko.com>
# Copyright 2018 Konsulko Group CC BY-SA 3.0 US
SUMMARY = "Package appdaemon container image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "app-container-image-appdaemon"

FILESEXTRAPATHS_prepend = "${DEPLOY_DIR}/images/${MACHINE}:"

SRC_URI = "\
    file://app-container-image-appdaemon-${MACHINE}.ext4 \
"

SRC_URI[md5sums] = ""

do_fetch[deptask] = "do_image_complete"

do_compile[noexec] = "1"

do_install () {
    install -d ${D}/var/lib/machines
    install ${WORKDIR}/app-container-image-appdaemon-${MACHINE}.ext4 ${D}/var/lib/machines
}

RDEPENDS_${PN} += "systemd-container"
