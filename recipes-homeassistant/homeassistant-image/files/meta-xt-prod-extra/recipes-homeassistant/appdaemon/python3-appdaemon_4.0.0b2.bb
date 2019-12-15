SUMMARY = "Apps for the Home Assistant home automation package"
HOMEPAGE = "https://github.com/home-assistant/appdaemon.git"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=f4eda51018051de136d3b3742e9a7a40"

inherit pypi setuptools3

SRC_URI[md5sum] = "a67b5ff37cd910e91a04e00928709668"
SRC_URI[sha256sum] = "a0ff20e85fd748f3a1e285cbf54fe33ccbe7587e1245cad69c16ad5d5c02583d"

DEPENDS += "\
    ${PYTHON_PN}-dateutil-native \
    ${PYTHON_PN}-six-native \
"

# Need to tweak it so we can substitute some
# of the packages that require versions different
# to what host environment has
RDEPENDS_${PN} = "\
    ${PYTHON_PN}-aiohttp-appdaemon \
    ${PYTHON_PN}-aiohttp-jinja2-appdaemon \
    ${PYTHON_PN}-async \
    ${PYTHON_PN}-astral \
    ${PYTHON_PN}-bcrypt-appdaemon \
    ${PYTHON_PN}-daemonize \
    ${PYTHON_PN}-dateutil \
    ${PYTHON_PN}-deepdiff \
    ${PYTHON_PN}-feedparser \
    ${PYTHON_PN}-iso8601 \
    ${PYTHON_PN}-jinja2-appdaemon \
    ${PYTHON_PN}-paho-mqtt \
    ${PYTHON_PN}-pid \
    ${PYTHON_PN}-profile \
    ${PYTHON_PN}-pycparser \
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-pyyaml-appdaemon \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-socketio \
    ${PYTHON_PN}-sseclient \
    ${PYTHON_PN}-typing \
    ${PYTHON_PN}-voluptuous \
    ${PYTHON_PN}-websocket-client \
    ${PYTHON_PN}-yarl-appdaemon \
"
