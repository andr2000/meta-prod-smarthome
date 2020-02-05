FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://watchdog.conf \
"

do_install_append() {
    install -Dm 0644 ${WORKDIR}/watchdog.conf ${D}${sysconfdir}/watchdog.conf
}

