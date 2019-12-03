SUMMARY = "Yet another one under-floor heating controller"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit pypi setuptools3 update-rc.d systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Install at this specific location, so we can overlayfs /usr
PYTHON_SITEPACKAGES_DIR = "/usr/local/bin/ufh-controller"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = "\
    bash \
    ebusd \
    logrotate \
    initscripts \
    python3-sqlite3 \
    python3-logging \
    python3-io \
    python3-fcntl \
    python3-daemonize \
    python3-configparser \
    python3-pickle \
    python3-threading \
"

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/andr2000/ufh-controller.git;protocol=https;branch=master \
    file://ufh-controller \
    file://ufh-controller.conf \
"

INITSCRIPT_NAME = "ufh-controller"
INITSCRIPT_PARAMS = "defaults 99"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "ufh-controller.service"

FILES_${PN} += " \
    ${PYTHON_SITEPACKAGES_DIR}/* \
"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0744 ${S}/../ufh-controller ${D}${sysconfdir}/default/ufh-controller

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/etc/systemd/ufh-controller.service ${D}${systemd_unitdir}/system

    install -d ${D}${sysconfdir}/ufh-controller
    local CONF_FILE=${D}${sysconfdir}/ufh-controller/ufh-controller.conf
    install -m 0744 ${S}/../ufh-controller.conf ${CONF_FILE}
    sed -i "s#SMARTHOME_RPI_MNT_PERSIST#${SMARTHOME_RPI_MNT_PERSIST}#g" ${CONF_FILE}

    chmod +x ${D}/${PYTHON_SITEPACKAGES_DIR}/ufh-controller.py

    # setup.py installs an empty folder which makes
    # bitbake complain of installed, but not shipped
    rm -rf ${D}/usr/share
}
