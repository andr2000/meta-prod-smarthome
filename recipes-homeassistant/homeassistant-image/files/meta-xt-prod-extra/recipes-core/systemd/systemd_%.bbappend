FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://0001-Ignore-wpan0-interface.patch \
"

PACKAGECONFIG_append = " coredump"
