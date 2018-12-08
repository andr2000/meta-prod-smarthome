SUMMARY = "Mediatek combo tool for Orangepi 4G IOT MT6737m SOC"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit update-rc.d

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/abbradar/wmt.git \
    file://0001-Add-_GNU_SOURCE-to-CFLAGS-to-avoid.patch \
    file://0002-wmt_loader-fix-chip-ID-before-initializing-the-drive.patch \
    file://0003-stp-Allow-selecting-Chip-SOC-ID-via-makefile.patch \
    file://wmt.init \
"

S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}${sbindir}
    install -m 0755 ${B}/wmt_loader ${D}${sbindir}/
    install -m 0755 ${B}/stp_uart_launcher ${D}${sbindir}/

    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/wmt.init ${D}${sysconfdir}/init.d/wmt
}

FILES_${PN} = " \
    ${sbindir}/wmt_loader \
    ${sbindir}/stp_uart_launcher \
    ${sysconfdir}/init.d \
"

INITSCRIPT_NAME = "wmt"
INITSCRIPT_PARAMS = "defaults 05"

INSANE_SKIP_${PN} = "ldflags"
