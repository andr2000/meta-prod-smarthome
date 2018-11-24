# For host tools environment variables
inherit esp8266_host

base_do_compile_prepend() {
    unset CC CFLAGS CXXFLAGS LDFLAGS

    ENV_FILE=`find ${XT_SHARED_ROOTFS_DIR}/esp8266-toolchain-native -name "environment-setup*"`
    if [ ! -f ${ENV_FILE} ]; then
        echo "Cannot use unvironment file: \"${ENV_FILE}\""
        exit 1
    fi
    source ${ENV_FILE}

    export SDK_BASE=$TOOLCHAIN_ROOT
    export PATH="${ESP8266_HOST_INSTALL_DIR}:$PATH"
}
