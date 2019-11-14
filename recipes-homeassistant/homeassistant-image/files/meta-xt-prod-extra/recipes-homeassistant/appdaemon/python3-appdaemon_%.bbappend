# Need to tweak it so we can substitute some
# of the packages that require versions different
# to what host environment has
RDEPENDS_${PN} = "\
    ${PYTHON_PN}-aiohttp-appdaemon \
    ${PYTHON_PN}-aiohttp-jinja2-appdaemon \
    ${PYTHON_PN}-async \
    ${PYTHON_PN}-astral \
    ${PYTHON_PN}-bcrypt \
    ${PYTHON_PN}-configparser \
    ${PYTHON_PN}-daemonize \
    ${PYTHON_PN}-feedparser \
    ${PYTHON_PN}-iso8601 \
    ${PYTHON_PN}-jinja2 \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-sseclient \
    ${PYTHON_PN}-paho-mqtt \
    ${PYTHON_PN}-profile \
    ${PYTHON_PN}-pycparser \
    ${PYTHON_PN}-pyyaml-appdaemon \
    ${PYTHON_PN}-typing \
    ${PYTHON_PN}-voluptuous \
    ${PYTHON_PN}-websockets \
    ${PYTHON_PN}-websocket-client \
    ${PYTHON_PN}-yarl-appdaemon \
"
