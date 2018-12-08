SUMMARY = "Mediatek prebuilt binaries and firmware"

LICENSE = "CLOSED"

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/andr2000/orangepi-4g-iot-bin.git \
"

S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}/${sysconfdir}/firmware
    cp -rf ${S}/firmware ${D}/${sysconfdir}/

    install -d ${D}/system/etc/
    ln -s /etc/firmware ${D}/system/etc
}

FILES_${PN} = " \
    ${base_prefix}/* \
    ${sysconfdir}/firmware \
"
