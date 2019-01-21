base_do_compile_prepend() {
    ENV_FILE=`find ${XT_SHARED_ROOTFS_DIR} -name "environment-sming"`
    if [ ! -f ${ENV_FILE} ]; then
        echo "Cannot use unvironment file: \"${ENV_FILE}\""
        exit 1
    fi
    source ${ENV_FILE}
}
