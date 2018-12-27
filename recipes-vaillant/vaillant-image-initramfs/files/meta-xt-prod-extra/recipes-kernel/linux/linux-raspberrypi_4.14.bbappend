FILESEXTRAPATHS_prepend := "${THISDIR}/files/${MACHINE}:"

SRC_URI += " \
    file://defconfig \
"

KBUILD_DEFCONFIG = "defconfig"

CMDLINE = "dwc_otg.lpm_enable=0 ${SERIAL}"