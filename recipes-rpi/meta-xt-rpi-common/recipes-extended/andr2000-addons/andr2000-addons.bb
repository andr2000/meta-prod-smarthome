SUMMARY = "andr2000 scripts and other addons"

LICENSE = "CLOSED"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "\
    file://overlay_mount.sh \
    file://overlay_unpack.sh \
    file://resize_persist.sh \
"

S = "${WORKDIR}"

FILES_${PN} += " \
    /andr2000/* \
"

RDEPENDS_${PN} += "\
    bash \
    e2fsprogs-mke2fs \
"

do_install() {
    install -d ${D}/andr2000/
    install -m 0744 ${WORKDIR}/overlay_mount.sh ${D}/andr2000/
    install -m 0744 ${WORKDIR}/overlay_unpack.sh ${D}/andr2000/
    install -m 0744 ${WORKDIR}/resize_persist.sh ${D}/andr2000/
}
