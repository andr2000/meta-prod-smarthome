# Based on: "Building Container Images with OpenEmbedded and
# the Yocto Project" by Scott Murray <scott.murray@konsulko.com>
# Copyright 2018 Konsulko Group CC BY-SA 3.0 US
SUMMARY = "Package appdaemon container image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit systemd

DEPENDS = "app-container-image-appdaemon"

FILESEXTRAPATHS_prepend = "${DEPLOY_DIR}/images/${MACHINE}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

APPDAEMON_CONFIG_DIR ?= "${localstatedir}/lib/appdaemon"
APPDAEMON_CONFIG_DIR[doc] = "Configuration directory used by appdaemon."

SRC_URI = "\
    file://app-container-image-appdaemon-${MACHINE}.tar.bz2;unpack=0 \
    file://config.json \
    file://appdaemon.service \
"

SRC_URI[md5sums] = ""

do_fetch[deptask] = "do_image_complete"

do_compile[noexec] = "1"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "appdaemon.service"

FILES_${PN} += "\
    ${APPDAEMON_CONFIG_DIR}/appdaemon \
"

# Force updating external configuration files
do_install[nostamp] = "1"

do_install () {
    install -d ${D}/var/lib/machines/appdaemon/rootfs
    tar --extract --bzip2 --numeric-owner --preserve-permissions \
        --preserve-order --totals --xattrs-include='*' \
        --directory=${D}/var/lib/machines/appdaemon/rootfs/ \
        --file=${WORKDIR}/app-container-image-appdaemon-${MACHINE}.tar.bz2

    install -m 0644 ${WORKDIR}/config.json ${D}/var/lib/machines/appdaemon/

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/appdaemon.service ${D}${systemd_unitdir}/system
    # Install apps and secrets
    install -d ${D}${APPDAEMON_CONFIG_DIR}
    if [ ! -z "${HOMEASSISTANT_APP_SECRETS_DIR}" ]; then
        echo "Using appdaemon secrets from ${HOMEASSISTANT_APP_SECRETS_DIR}..."
        cp -rf ${HOMEASSISTANT_APP_SECRETS_DIR}/appdaemon/. ${D}/${APPDAEMON_CONFIG_DIR}/
    fi
    # remove git leftovers if any
    rm -rf ${D}/${APPDAEMON_CONFIG_DIR}/.git* || true
}

# We are installing into /var/lib..., so silence QA check which
# cannot find runtime dependencies at /usr/lib
INSANE_SKIP_${PN} = "ldflags file-rdeps"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
