XT_VALLIANT_MACHINE ??= "raspberrypi0-wifi"

add_to_local_conf() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    base_update_conf_value ${local_conf} MACHINE "${XT_VALLIANT_MACHINE}"

    # Get the root password from the local conf
    base_update_conf_value ${local_conf} VAILLANT_ROOT_PWD "${VAILLANT_ROOT_PWD}"

    # Get telemetry WiFi settings
    base_update_conf_value ${local_conf} SMARTHOME_TELEMETRY_SSID "${SMARTHOME_TELEMETRY_SSID}"
    base_update_conf_value ${local_conf} SMARTHOME_TELEMETRY_PWD "${SMARTHOME_TELEMETRY_PWD}"

    # Get telegram settings
    base_update_conf_value ${local_conf} TELEGRAM_BOT_TOKEN "${TELEGRAM_BOT_TOKEN}"
    base_update_conf_value ${local_conf} TELEGRAM_CHAT_ID "${TELEGRAM_CHAT_ID}"
}

python do_configure_append() {
    bb.build.exec_func("add_to_local_conf", d)
}
