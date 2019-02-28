# This is the machinery to run experimental code
# from within initramfs image

SUMMARY = "Start experimental code from within initramfs"
LICENSE = "CLOSED"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

RDEPENDS_${PN} = "bash initscripts"

SRC_URI = " \
    file://${PN} \
    file://boot_count.sh \
"

inherit update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 99"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/${PN} ${D}${sysconfdir}/init.d/${PN}
    sed -i "s#SMARTHOME_RPI_MNT_SECRET#${SMARTHOME_RPI_MNT_SECRET}#g" ${D}${sysconfdir}/init.d/${PN}

    install -d ${D}${SMARTHOME_RPI_MNT_SECRET}/${PN}
    install -m 0755 ${S}/boot_count.sh ${D}${SMARTHOME_RPI_MNT_SECRET}/${PN}/
    sed -i "s#SMARTHOME_RPI_MNT_DATA#${SMARTHOME_RPI_MNT_DATA}#g" ${D}${SMARTHOME_RPI_MNT_SECRET}/${PN}/boot_count.sh
}

FILES_${PN} = " \
    ${sysconfdir}/init.d \
    ${SMARTHOME_RPI_MNT_SECRET}/${PN}/* \
"
