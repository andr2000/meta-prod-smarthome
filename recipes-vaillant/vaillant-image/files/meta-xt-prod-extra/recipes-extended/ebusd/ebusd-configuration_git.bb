DESCRIPTION = "This repository serves vendor specific configuration files \
               for ebusd: https://github.com/john30/ebusdebusd"
SECTION = "extras"
LICENSE = "GPLv3"
PR = "r0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=66e1eeb3afdf47b310a8c763864b70c8"

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/john30/ebusd-configuration.git;protocol=https;branch=master"

FILES_${PN} += "\
    ${sysconfdir}/* \
"

do_install_append() {
    install -d ${D}${sysconfdir}/ebusd
    cp -r ${S}/latest/* ${D}${sysconfdir}/ebusd/
}
