FILESEXTRAPATHS_prepend := "${THISDIR}/files/${MACHINE}:"

SRC_URI += " \
    file://defconfig \
    file://0001-Add-ttyebus-driver-for-Raspberry-Pi-Zero-W.patch \
"

KBUILD_DEFCONFIG = "defconfig"

CMDLINE = "dwc_otg.lpm_enable=0"

# FIXME: for some reason this module doesn't work when built in
KERNEL_MODULE_AUTOLOAD += "ttyebusm"