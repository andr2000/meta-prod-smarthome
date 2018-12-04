DESCRIPTION = "Linux Kernel for Orange Pi 4G-IOT"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo
require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "^opi$"

SRC_URI += " \
    git://github.com/andr2000/linux-orangepi-4g-iot.git;protocol=git;branch=master \
"

SRCREV = "${AUTOREV}"
KERNEL_VERSION_SANITY_SKIP="1"

KBUILD_DEFCONFIG_pn-linux-orangepi = "orangepi_4g_iot_defconfig"

PV = "${LINUX_VERSION}+git${SRCPV}"

LINUX_VERSION ?= "3.18"

CMDLINE ?= "${SERIAL} root=/dev/mmcblk0p18 rw rootwait"

do_install_append() {
    if test "${KERNEL_IMAGETYPE}" = "zImage-dtb"; then
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0644 ${KERNEL_OUTPUT_DIR}/${KERNEL_IMAGETYPE} ${D}/${KERNEL_IMAGEDEST}
    fi
}

