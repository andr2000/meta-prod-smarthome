DESCRIPTION = "An esp8266 rom creation tool"
LICENSE = "GPLv3"

inherit native esp8266_host

PR = "r0"

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"

# Fetch recursively
SRC_URI = " \
    git://github.com/raburton/esptool2.git \
"

PROVIDES = "${PN}"

do_compile() {
    cd ${S}
    make
}

do_install() {
    install -d ${D}
    install -m 0744 ${B}/esptool2 "${D}/"

    install -d ${ESP8266_HOST_INSTALL_DIR}
    install -m 0744 ${B}/esptool2 ${ESP8266_HOST_INSTALL_DIR}/
}

FILES_${PN} = "\
    ${D}/* \
"

