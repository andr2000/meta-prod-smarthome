# Move HA config
HOMEASSISTANT_CONFIG_DIR = "${datadir}/homeassistant"

SRC_URI += "\
    git://github.com/andr2000/homeassistant-config.git;destsuffix=${WORKDIR}/homeassistant-config;branch=master;rev=master \
"

PACKAGES += "homeassistant-configuration"

FILES_homeassistant-configuration += "\
    ${HOMEASSISTANT_CONFIG_DIR} \
"

do_install_append() {
    install -d ${D}${HOMEASSISTANT_CONFIG_DIR}
    cp -rf ${WORKDIR}/homeassistant-config/* ${D}${HOMEASSISTANT_CONFIG_DIR}/
}

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-ebusdpy \
"
