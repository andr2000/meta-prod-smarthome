FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://history-bind.sh \
"

hostname = "vaillant"

FILES_${PN} += "${sysconfdir}/profile.d/history-bind.sh"

VAILLANT_MNT_POINT ?= "mmcblk0p"

do_install_append() {
	install -d ${D}${sysconfdir}/profile.d/
	install -m 0755 ${WORKDIR}/history-bind.sh ${D}${sysconfdir}/profile.d/

	echo "/dev/${VAILLANT_MNT_POINT}2 ${VAILLANT_MNT_SECRET} auto defaults,sync,ro 0  0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${VAILLANT_MNT_POINT}3 ${VAILLANT_MNT_DATA} auto defaults,sync 0  1" >> ${D}${sysconfdir}/fstab
}
