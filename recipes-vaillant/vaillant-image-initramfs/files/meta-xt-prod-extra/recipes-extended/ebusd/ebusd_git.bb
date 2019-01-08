DESCRIPTION = "ebusd is a daemon for handling communication with eBUS \
               devices connected to a 2-wire bus system ("energy bus" \
               used by numerous heating systems)."
SECTION = "extras"
LICENSE = "GPLv3"
PR = "r0"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "mosquitto"
RDEPENDS_${PN} = "bash logrotate initscripts"

LIC_FILES_CHKSUM = "file://LICENSE;md5=66e1eeb3afdf47b310a8c763864b70c8"

S = "${WORKDIR}/git"

inherit autotools update-rc.d

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/john30/ebusd.git;protocol=https;branch=master \
    file://0001-Use-init.d-functions-for-non-LSB-distributions.patch \
    file://ebusd \
    file://ebusd-real \
"

INITSCRIPT_NAME = "ebusd"
INITSCRIPT_PARAMS = "defaults 99"

FILES_${PN} += " \
    ${VAILLANT_MNT_SECRET}${sysconfdir}/default/* \
"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0744 ${S}/contrib/debian/init.d/ebusd ${D}${sysconfdir}/init.d/

    install -d ${D}${sysconfdir}/default
    install -m 0744 ${S}/../ebusd ${D}${sysconfdir}/default/ebusd
    sed -i "s#VAILLANT_MNT_SECRET#${VAILLANT_MNT_SECRET}#g" ${D}${sysconfdir}/default/ebusd

    install -d ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/default
    install -m 0744 ${S}/../ebusd-real ${D}${VAILLANT_MNT_SECRET}/${sysconfdir}/default/ebusd

    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0644 ${S}/contrib/etc/logrotate.d/ebusd ${D}${sysconfdir}/logrotate.d/
}
