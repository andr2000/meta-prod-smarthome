SUMMARY = "andr2000 scripts and other addons"

LICENSE = "CLOSED"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "\
    file://overlay_mount.sh \
    file://overlay_unpack.sh \
"

S = "${WORKDIR}"

FILES_${PN} += " \
    /andr2000/* \
"

RDEPENDS_${PN} += "\
    bash \
"

do_install() {
    install -d ${D}/andr2000/
    install -m 0744 ${WORKDIR}/overlay_mount.sh ${D}/andr2000/
    install -m 0744 ${WORKDIR}/overlay_unpack.sh ${D}/andr2000/
}
