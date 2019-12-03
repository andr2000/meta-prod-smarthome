FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://0001-Add-ttyebus-driver-for-Raspberry-Pi-Zero-W.patch \
    file://0001-Changed-IRQ-for-V4.19.42.patch \
    file://defconfig \
"

CMDLINE = "dwc_otg.lpm_enable=0"

# RPi3 has no connection to e-bus, so enable console for it
CMDLINE_append_raspberrypi3 = " console=serial0,115200"

# FIXME: for some reason this module doesn't work when built in
KERNEL_MODULE_AUTOLOAD += "ttyebusm"
