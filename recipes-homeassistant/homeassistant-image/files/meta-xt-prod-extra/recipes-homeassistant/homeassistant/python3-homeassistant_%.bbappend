# Use git version, not pypi
S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"
PYPI_SRC_URI = "\
    git://github.com/home-assistant/home-assistant.git;tag=0.103.3;name=ha \
"

FILES_${PN} += "\
    ${HOMEASSISTANT_CONFIG_DIR}/appconfig \
"

# Force updating external configuration files
do_install[nostamp] = "1"

do_install_append() {
    install -d ${D}${HOMEASSISTANT_CONFIG_DIR}
    if [ ! -z "${HOMEASSISTANT_APP_SECRETS_DIR}" ]; then
        echo "Using Home Assistant secrets from ${HOMEASSISTANT_APP_SECRETS_DIR}..."
        cp -rf ${HOMEASSISTANT_APP_SECRETS_DIR}/appconfig/. ${D}/${HOMEASSISTANT_CONFIG_DIR}/
    fi
    # remove git leftovers if any
    rm -rf ${D}/${HOMEASSISTANT_CONFIG_DIR}/.git* || true
    chown -R ${HOMEASSISTANT_USER}:${HOMEASSISTANT_USER} ${D}/${HOMEASSISTANT_CONFIG_DIR}/
}

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-av \
    ${PYTHON_PN}-ebusdpy \
    ${PYTHON_PN}-python-telegram-bot \
    ${PYTHON_PN}-restrictedpython \
    ${PYTHON_PN}-aioesphomeapi \
    ${PYTHON_PN}-psycopg2 \
    ${PYTHON_PN}-influxdb \
    ${PYTHON_PN}-pynacl \
"

# Because we resize data partition on the first boot we need
# to create database folder after that is done.
RDEPENDS_${PN} += "base-files"


