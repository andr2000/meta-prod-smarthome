SUMMARY = "Sming - Open Source framework for high efficiency WiFi SoC ESP8266 native development with C++ language."

LICENSE = "GPLv3"

inherit esp8266_env

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/SmingHub/Sming.git;branch=master"

S = "${WORKDIR}/git"

# Unfortunately xt-distro doesn't handle DEPENDS at all...
do_build[depends] += "esp8266-toolchain-native:do_${BB_DEFAULT_TASK}"

export ESP_HOME="${TOOLCHAIN_ROOT}"
export SMING_HOME="${S}/Sming"

do_compile_append() {
    cd ${SMING_HOME}
    make ${PARALLEL_MAKE}
}

do_install() {
    # Write environment setup file
    echo "export SMING_HOME=${SMING_HOME}" >> "${XT_SHARED_ROOTFS_DIR}/environment-${PN}"
}
