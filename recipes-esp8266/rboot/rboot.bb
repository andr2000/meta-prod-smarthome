SUMMARY = "An open source boot loader for the ESP8266"

LICENSE = "MIT"

inherit esp8266_env

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/raburton/rboot.git"

S = "${WORKDIR}/git"

# Unfortunately xt-distro doesn't handle DEPENDS at all...
do_build[depends] += "esp8266-toolchain-native:do_${BB_DEFAULT_TASK}"
do_build[depends] += "esptool2-native:do_${BB_DEFAULT_TASK}"

do_compile_append() {
    cd ${S}
    export ESPTOOL2="${ESP8266_HOST_INSTALL_DIR}/esptool2"
    make ${PARALLEL_MAKE}
}
