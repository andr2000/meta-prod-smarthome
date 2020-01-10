DESCRIPTION = "wpantund: Wireless Network Interface Daemon for Low-Power Wireless SoCs "
HOMEPAGE = "https://github.com/openthread/wpantund"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e7820bc7f7d1638a6b54fc2e8d7fb103"

DEPENDS += "autoconf-archive-native"
DEPENDS += "dbus readline boost"

RDEPENDS_${PN} += "dbus readline boost"

SRC_URI  = "git://github.com/openthread/wpantund.git;protocol=https"
# Tag full/0.07.01 based on this commit
SRCREV = "87c90eedce0c75cb68a1cbc34ff36223400862f1"

S = "${WORKDIR}/git/"

inherit autotools pkgconfig systemd

SRC_URI += "\
    file://wpantund.service \
    file://wpantund \
    file://wpantund.conf \
"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "wpantund.service"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0744 ${WORKDIR}/wpantund ${D}${sysconfdir}/default/wpantund
    sed -i "s#SMARTHOME_RPI_MNT_PERSIST#${SMARTHOME_RPI_MNT_PERSIST}#g" ${D}${sysconfdir}/default/wpantund
    install -m 0744 ${WORKDIR}/wpantund.conf ${D}${sysconfdir}

    # Install systemd unit files
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/wpantund.service ${D}${systemd_unitdir}/system
}
