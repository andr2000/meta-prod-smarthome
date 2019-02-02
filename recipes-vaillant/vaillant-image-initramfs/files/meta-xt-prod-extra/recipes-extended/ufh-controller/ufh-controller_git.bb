SUMMARY = "Yet another one under-floor heating controller"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit pypi setuptools3 update-rc.d

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Install at this specific location.
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
"

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/andr2000/ufh-controller.git;protocol=https;branch=master \
    file://ufh-controller \
    file://ufh-controller.conf \
"

INITSCRIPT_NAME = "ufh-controller"
INITSCRIPT_PARAMS = "defaults 99"

FILES_${PN} += " \
    ${VAILLANT_MNT_SECRET}${sysconfdir}/* \
    ${PYTHON_SITEPACKAGES_DIR}/* \
"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0744 ${S}/../ufh-controller ${D}${sysconfdir}/default/ufh-controller
    sed -i "s#VAILLANT_MNT_SECRET#${VAILLANT_MNT_SECRET}#g" ${D}${sysconfdir}/default/ufh-controller

    install -d ${D}${sysconfdir}/ufh-controller
    local CONF_FILE=${D}${sysconfdir}/ufh-controller/ufh-controller.conf
    install -m 0744 ${S}/../ufh-controller.conf ${CONF_FILE}

    sed -i "s#VAILLANT_MNT_SECRET#${VAILLANT_MNT_SECRET}#g" ${CONF_FILE}
    sed -i "s#VAILLANT_MNT_DATA#${VAILLANT_MNT_DATA}#g" ${CONF_FILE}
    sed -i "s#TELEGRAM_BOT_TOKEN#${TELEGRAM_BOT_TOKEN}#g" ${CONF_FILE}
    sed -i "s#TELEGRAM_CHAT_ID#${TELEGRAM_CHAT_ID}#g" ${CONF_FILE}

    chmod +x ${D}/${PYTHON_SITEPACKAGES_DIR}/ufh-controller.py

    # setup.py installs an empty folder which makes
    # bitbake complain of installed, but not shipped
    rm -rf ${D}/usr/share
}