FILESEXTRAPATHS_prepend := "${THISDIR}/../files:"

XT_RPI_MACHINE ??= "raspberrypi0-wifi"

XT_QUIRK_PATCH_SRC_URI += "\
    file://0001-wic-Add-partition-type-for-msdos-partition-tables.patch;patchdir=poky \
"

add_to_local_conf_common() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    base_update_conf_value ${local_conf} MACHINE "${XT_RPI_MACHINE}"

    # Get the root password from the local conf
    base_update_conf_value ${local_conf} SMARTHOME_ROOT_PWD "${SMARTHOME_ROOT_PWD}"

    # Get telemetry WiFi settings
    base_update_conf_value ${local_conf} SMARTHOME_TELEMETRY_SSID "${SMARTHOME_TELEMETRY_SSID}"
    base_update_conf_value ${local_conf} SMARTHOME_TELEMETRY_PWD "${SMARTHOME_TELEMETRY_PWD}"

    # Get telegram settings
    base_update_conf_value ${local_conf} TELEGRAM_BOT_TOKEN "${TELEGRAM_BOT_TOKEN}"
    base_update_conf_value ${local_conf} TELEGRAM_CHAT_ID "${TELEGRAM_CHAT_ID}"

    # Get location of the secrets if any: everything from this dir will
    # be installed into ${SMARTHOME_RPI_MNT_SECRET}
    base_update_conf_value ${local_conf} SMARTHOME_SECRETS_DIR "${SMARTHOME_SECRETS_DIR}"
}

python do_configure_append() {
    bb.build.exec_func("add_to_local_conf_common", d)
}
