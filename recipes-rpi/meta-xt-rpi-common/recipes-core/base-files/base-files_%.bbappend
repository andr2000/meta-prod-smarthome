FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://history-bind.sh \
"

FILES_${PN} += "${sysconfdir}/profile.d/history-bind.sh"

MNT_POINT ??= "mmcblk0p"
MNT_DEV = "${@(d.getVar('MNT_POINT'))[:-1]}"

do_install_append() {
	install -d ${D}${sysconfdir}/profile.d/
	install -m 0755 ${WORKDIR}/history-bind.sh ${D}${sysconfdir}/profile.d/

	echo "overlay /usr overlay defaults,x-systemd.requires=mnt-overlay.mount,lowerdir=/usr,upperdir=${SMARTHOME_RPI_MNT_OVERLAY}/usr,workdir=${SMARTHOME_RPI_MNT_OVERLAY}/workdir 0 2" >> ${D}${sysconfdir}/fstab
}
