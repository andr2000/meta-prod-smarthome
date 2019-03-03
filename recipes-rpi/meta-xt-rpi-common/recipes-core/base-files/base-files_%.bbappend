FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://history-bind.sh \
"

FILES_${PN} += "${sysconfdir}/profile.d/history-bind.sh"

MNT_POINT ??= "mmcblk0p"

do_install_append() {
	install -d ${D}${sysconfdir}/profile.d/
	install -m 0755 ${WORKDIR}/history-bind.sh ${D}${sysconfdir}/profile.d/

	echo "/dev/${MNT_POINT}5 ${SMARTHOME_RPI_MNT_OVERLAY} auto defaults,sync,rw 0 0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${MNT_POINT}6 ${SMARTHOME_RPI_MNT_SECRET} auto defaults,sync,ro 0 0" >> ${D}${sysconfdir}/fstab
	echo "/dev/${MNT_POINT}7 ${SMARTHOME_RPI_MNT_DATA} auto defaults,sync 0 1" >> ${D}${sysconfdir}/fstab
	echo "overlay /usr overlay defaults,lowerdir=/usr,upperdir=${SMARTHOME_RPI_MNT_OVERLAY}/usr,workdir=${SMARTHOME_RPI_MNT_OVERLAY}/workdir 0 2" >> ${D}${sysconfdir}/fstab
}
