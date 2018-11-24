python () {
    base = d.getVar('DEPLOY_DIR') or ""
    d.setVar("ESP8266_HOST_INSTALL_DIR", os.path.join(base, "esp8266/host"))
}
