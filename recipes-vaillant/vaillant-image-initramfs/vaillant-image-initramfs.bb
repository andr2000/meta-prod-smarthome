SUMMARY = "Minimalistic initrafs image for Raspberry Pi Zero W for Vaillant"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"

SRCREV = "${AUTOREV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../machine:"

################################################################################
# Raspberry Pi Zero W Vaillant
################################################################################
SRC_URI = "repo://github.com/andr2000/meta-products;protocol=https;branch=master;manifest=vaillant.xml;scmdata=keep"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += "\
    file://meta-xt-prod-extra;subdir=repo \
    file://poky;subdir=repo \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += "\
    meta-xt-prod-extra \
"

XT_BB_LAYERS_FILE = "meta-xt-prod-extra/doc/bblayers.conf.image-minimal-initramfs"
XT_BB_LOCAL_CONF_FILE = "meta-xt-prod-extra/doc/local.conf.image-minimal-initramfs"

XT_BB_IMAGE_TARGET = "core-image-vaillant-initramfs"

XT_VALLIANT_MACHINE ?= "raspberrypi0-wifi"

add_to_local_conf() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    # This image is for Raspberry Pi Zero W
    base_update_conf_value ${local_conf} MACHINE "${XT_VALLIANT_MACHINE}"
}

python do_configure_append() {
    bb.build.exec_func("add_to_local_conf", d)
}
