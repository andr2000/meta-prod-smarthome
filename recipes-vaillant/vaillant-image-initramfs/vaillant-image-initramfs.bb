SUMMARY = "Minimalistic initrafs image for Raspberry Pi Zero W for Vaillant"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

require ../../recipes-rpi/inc/image-rpi-common.inc

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../recipes-rpi:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../recipes-rpi/patches:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../machine:"

################################################################################
# Raspberry Pi Zero W Vaillant
################################################################################
SRC_URI = "\
    repo://github.com/andr2000/meta-products;protocol=https;branch=master;manifest=vaillant.xml;scmdata=keep \
    file://0001-Workaround-python3-sqlite3-not-installing.patch;patchdir=poky \
"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += "\
    file://meta-xt-prod-extra;subdir=repo \
    file://meta-xt-rpi-common;subdir=repo \
    file://poky;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += "\
    meta-xt-rpi-common \
    meta-xt-prod-extra \
"

XT_BB_LAYERS_FILE = "meta-xt-rpi-common/inc/bblayers.conf.rpi-common"
XT_BB_LOCAL_CONF_FILE = "meta-xt-prod-extra/doc/local.conf.image-minimal-initramfs"

XT_BB_IMAGE_TARGET = "core-image-vaillant-initramfs"

XT_RPI_MACHINE ?= "raspberrypi0-wifi"