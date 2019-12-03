DESCRIPTION = "wpantund: Wireless Network Interface Daemon for Low-Power Wireless SoCs "
HOMEPAGE = "https://github.com/openthread/wpantund"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS += "autoconf-archive-native"
DEPENDS += "dbus readline boost"

RDEPENDS_${PN} += "dbus readline boost"

SRC_URI  = "git://github.com/openthread/wpantund.git;protocol=https"
# Tag full/0.07.01 based on this commit
SRCREV = "85d5e61b0138b362d02e244e122c8f5aaddafe09"

S = "${WORKDIR}/git/"

inherit autotools pkgconfig systemd

SRC_URI += "\
    file://wpantund.service \
    file://wpantund \
"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "wpantund.service"

do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0744 ${WORKDIR}/wpantund ${D}${sysconfdir}/default/wpantund
    sed -i "s#SMARTHOME_RPI_MNT_PERSIST#${SMARTHOME_RPI_MNT_PERSIST}#g" ${D}${sysconfdir}/default/wpantund

    # Install systemd unit files
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/wpantund.service ${D}${systemd_unitdir}/system
}
