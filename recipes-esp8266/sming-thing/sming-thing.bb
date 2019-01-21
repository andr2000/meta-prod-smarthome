SUMMARY = "ESP8266 ES-01S + ds18b20 based thermomenter firmware"

LICENSE = "GPLv3"

inherit esp8266_env sming_env

SRCREV = "${AUTOREV}"

SRC_URI = "gitsm://github.com/andr2000/sming-test.git"

S = "${WORKDIR}/git"

# Unfortunately xt-distro doesn't handle DEPENDS at all...
do_build[depends] += "sming:do_${BB_DEFAULT_TASK}"

export ESP_HOME="${TOOLCHAIN_ROOT}"

do_compile_append() {
    cd ${S}
    make ${PARALLEL_MAKE}
}
