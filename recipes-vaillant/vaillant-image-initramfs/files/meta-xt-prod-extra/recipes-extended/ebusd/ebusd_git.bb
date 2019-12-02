DESCRIPTION = "ebusd is a daemon for handling communication with eBUS \
               devices connected to a 2-wire bus system ("energy bus" \
               used by numerous heating systems)."
SECTION = "extras"
LICENSE = "GPLv3"
PR = "r0"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "mosquitto"

RDEPENDS_${PN} = "\
    bash \
    logrotate \
    libmosquitto1 \
    ebusd-configuration \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=66e1eeb3afdf47b310a8c763864b70c8"

S = "${WORKDIR}/git"

inherit autotools update-rc.d systemd

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/john30/ebusd.git;protocol=https;branch=master \
    file://ebusd \
"

INITSCRIPT_NAME = "ebusd"
INITSCRIPT_PARAMS = "defaults 99"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "ebusd.service"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0744 ${S}/contrib/debian/init.d/ebusd ${D}${sysconfdir}/init.d/

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/contrib/debian/systemd/ebusd.service ${D}${systemd_unitdir}/system

    install -d ${D}${sysconfdir}/default
    install -m 0744 ${S}/../ebusd ${D}${sysconfdir}/default/ebusd
    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${D}${sysconfdir}/default/ebusd

    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0644 ${S}/contrib/etc/logrotate.d/ebusd ${D}${sysconfdir}/logrotate.d/
}
