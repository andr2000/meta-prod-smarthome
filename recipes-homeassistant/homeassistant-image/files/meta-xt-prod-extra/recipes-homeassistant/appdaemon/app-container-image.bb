# Based on: "Building Container Images with OpenEmbedded and
# the Yocto Project" by Scott Murray <scott.murray@konsulko.com>
# Copyright 2018 Konsulko Group CC BY-SA 3.0 US
SUMMARY = "A minimal container image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
IMAGE_FSTYPES = "container"

inherit image

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = ""

IMAGE_TYPEDEP_container += "ext4"
IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""
NO_RECOMMENDATIONS = "1"

PREFERRED_PROVIDER_virtual/kernel = "linux-dummy"

TCLIBC = "musl"

DISTRO_FEATURES = "acl ipv4 ipv6 largefile xattr ${DISTRO_FEATURES_LIBC}"
DISTRO_FEATURES = ""

VIRTUAL-RUNTIME_dev_manager ?= ""
VIRTUAL-RUNTIME_login_manager ?= ""
VIRTUAL-RUNTIME_init_manager ?= ""
VIRTUAL-RUNTIME_initscripts ?= ""
VIRTUAL-RUNTIME_keymaps ?= ""

FORCE_RO_REMOVE = "1"

IMAGE_INSTALL = "\
    base-files \
    base-passwd \
    netbase \
"

# Workaround /var/volatile for now
ROOTFS_POSTPROCESS_COMMAND += "rootfs_fixup_var_volatile; "

rootfs_fixup_var_volatile () {
    install -m 1777 -d ${IMAGE_ROOTFS}/${localstatedir}/volatile/tmp
    install -m 755 -d ${IMAGE_ROOTFS}/${localstatedir}/volatile/log
}
