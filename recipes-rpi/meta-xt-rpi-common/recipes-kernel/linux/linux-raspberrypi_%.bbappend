FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://overlayfs.cfg \
"

CMDLINE_append = " rootwait root=/dev/mmcblk0p2 rootrw=/dev/mmcblk0p3 init=/init"
