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
    # copy all including hidden files
    cp -rf ${WORKDIR}/homeassistant-config/. ${D}${HOMEASSISTANT_CONFIG_DIR}/

    if [ ! -z "${HOMEASSISTANT_SECRETS_DIR}" ]; then
        echo "Using Home Assistant secrets from ${HOMEASSISTANT_SECRETS_DIR}..."
        cp -rf ${HOMEASSISTANT_SECRETS_DIR}/. ${D}/${HOMEASSISTANT_CONFIG_DIR}/
    fi
    # remove git leftovers if any
    rm -rf ${D}/${HOMEASSISTANT_CONFIG_DIR}/.git* || true
}

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-ebusdpy \
"
