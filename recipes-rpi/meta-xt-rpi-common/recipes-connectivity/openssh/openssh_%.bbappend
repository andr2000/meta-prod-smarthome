FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append() {
    install -d ${D}${sysconfdir}/ssh
    install -m 644 ${WORKDIR}/sshd_config ${D}${sysconfdir}/ssh/sshd_config
}
