FILESEXTRAPATHS_prepend := "${THISDIR}/files/${MACHINE}:"

CMDLINE_append = " ro"

SRC_URI += " \
    file://ikconfig.cfg \
    file://rtc.cfg \
"