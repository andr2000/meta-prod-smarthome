FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://history-bind.sh \
"

hostname = "vaillant"

FILES_${PN} += "${sysconfdir}/profile.d/history-bind.sh"

do_install_append() {
	install -d ${D}${sysconfdir}/profile.d/
	install -m 0755 ${WORKDIR}/history-bind.sh ${D}${sysconfdir}/profile.d/
}
