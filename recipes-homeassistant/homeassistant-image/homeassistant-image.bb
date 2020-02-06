SUMMARY = "Home assistant image for Raspberry Pi 3 B+ with ebusd"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

require ../../recipes-rpi/inc/image-rpi-common.inc

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../recipes-rpi:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../machine:"

################################################################################
# Raspberry Pi 3 B+ for Home Assistant
################################################################################
SRC_URI = "\
    repo://github.com/andr2000/meta-products;protocol=https;branch=master;manifest=homeassistant.xml;scmdata=keep \
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

XT_QUIRK_PATCH_SRC_URI += "\
    file://0001-Force-installing-ntpath.py.patch;patchdir=poky \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += "\
    meta-xt-rpi-common \
    meta-xt-prod-extra \
    meta-homeassistant \
    meta-openembedded/meta-filesystems \
    meta-virtualization \
"

XT_BB_LAYERS_FILE = "meta-xt-rpi-common/inc/bblayers.conf.rpi-common"
XT_BB_LOCAL_CONF_FILE = "meta-xt-prod-extra/doc/local.conf.image-homeassistant"

XT_BB_IMAGE_TARGET = "core-image-homeassist"

XT_RPI_MACHINE ?= "raspberrypi3"

add_to_local_conf() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    base_update_conf_value ${local_conf} HOMEASSISTANT_APP_SECRETS_DIR "${HOMEASSISTANT_APP_SECRETS_DIR}"

    # Get location of the secrets if any: everything from this dir will
    # be installed into ${SMARTHOME_RPI_MNT_SECRET}
    base_update_conf_value ${local_conf} SMARTHOME_SECRETS_DIR "${HOMEASSISTANT_SECRETS_DIR}"
}

python do_configure_append() {
    bb.build.exec_func("add_to_local_conf", d)
}
