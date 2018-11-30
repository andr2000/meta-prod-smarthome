SUMMARY = "ESP8266 ES-01S + ds18b20 based thermomenter firmware"

LICENSE = "GPLv2 | MIT"

inherit esp8266_env

SRCREV = "${AUTOREV}"

SRC_URI = "gitsm://github.com/andr2000/esp8266-thermometer.git"

S = "${WORKDIR}/git"

EXTRA_OE_MAKE += "SPI_SIZE=1M SPI_MODE=qio SPI_SPEED=40"

# Unfortunately xt-distro doesn't handle DEPENDS at all...
do_build[depends] += "esp8266-toolchain-native:do_${BB_DEFAULT_TASK}"
do_build[depends] += "esptool2-native:do_${BB_DEFAULT_TASK}"

do_compile_append() {
    cd ${S}
    export ESPTOOL2="${ESP8266_HOST_INSTALL_DIR}/esptool2"
    make ${PARALLEL_MAKE} ${EXTRA_OE_MAKE}
}
