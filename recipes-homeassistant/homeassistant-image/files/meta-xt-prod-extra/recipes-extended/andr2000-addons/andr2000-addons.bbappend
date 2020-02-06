FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://vaillant_read_setmode.sh \
    file://vaillant_test_cmds.sh \
"

do_install_append() {
    install -d ${D}/andr2000/
    install -m 0744 ${WORKDIR}/vaillant_read_setmode.sh ${D}/andr2000/
    install -m 0744 ${WORKDIR}/vaillant_test_cmds.sh ${D}/andr2000/
}
