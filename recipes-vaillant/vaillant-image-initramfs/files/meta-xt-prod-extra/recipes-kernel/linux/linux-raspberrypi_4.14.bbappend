FILESEXTRAPATHS_prepend := "${THISDIR}/files/${MACHINE}:"

SRC_URI += " \
    file://defconfig \
"

KBUILD_DEFCONFIG = "defconfig"
