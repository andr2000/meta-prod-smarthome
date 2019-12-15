FILESEXTRAPATHS_prepend := "${THISDIR}/files/${MACHINE}:"

SRC_URI += " \
    file://ikconfig.cfg \
    file://rtc.cfg \
"

CMDLINE = "dwc_otg.lpm_enable=0 console=serial0,115200"
