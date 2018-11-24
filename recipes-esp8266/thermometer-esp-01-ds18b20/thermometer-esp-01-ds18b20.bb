SUMMARY = "ESP8266 ES-01S + ds18b20 based thermomenter firmware"

LICENSE = "GPLv2"

inherit esp8266_env

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/andr2000/esp8266-thermometer.git"

S = "${WORKDIR}/git"

# Unfortunately xt-distro doesn't handle DEPENDS at all...
do_build[depends] += "esp8266-toolchain-native:do_${BB_DEFAULT_TASK}"
do_build[depends] += "rboot:do_${BB_DEFAULT_TASK}"

do_compile_append() {
    cd ${S}
    make ${PARALLEL_MAKE}
}
