DESCRIPTION = "ebusd is a daemon for handling communication with eBUS \
               devices connected to a 2-wire bus system ("energy bus" \
               used by numerous heating systems)."
SECTION = "extras"
LICENSE = "GPLv3"
PR = "r0"

DEPENDS = "mosquitto"
RDEPENDS_ebusd = "bash"

LIC_FILES_CHKSUM = "file://LICENSE;md5=66e1eeb3afdf47b310a8c763864b70c8"

S = "${WORKDIR}/git"

inherit autotools update-rc.d

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/john30/ebusd.git;protocol=https;branch=master"

INITSCRIPT_NAME = "ebusd"
INITSCRIPT_PARAMS = "defaults 99"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0744 ${S}/contrib/debian/init.d/ebusd ${D}${sysconfdir}/init.d/
}
