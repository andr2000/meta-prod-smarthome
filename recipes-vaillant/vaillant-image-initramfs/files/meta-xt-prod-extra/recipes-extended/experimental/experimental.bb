# This is the machinery to run an experimental code
# from within initramfs image

SUMMARY = "Run experimental code from within initramfs"
LICENSE = "CLOSED"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

RDEPENDS_${PN} = "bash initscripts"

SRC_URI = " \
    file://experimental \
"

inherit update-rc.d

INITSCRIPT_NAME = "experimental"
INITSCRIPT_PARAMS = "defaults 99"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/experimental ${D}${sysconfdir}/init.d/experimental
    sed -i "s#VAILLANT_MNT_DATA#${VAILLANT_MNT_DATA}#g" ${D}${sysconfdir}/init.d/experimental
}

FILES_${PN} = " \
    ${sysconfdir}/init.d \
"
