# Based on: "Building Container Images with OpenEmbedded and
# the Yocto Project" by Scott Murray <scott.murray@konsulko.com>
# Copyright 2018 Konsulko Group CC BY-SA 3.0 US
SUMMARY = "Appdaemon container image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

require app-container-image.bb

IMAGE_INSTALL += " \
    bash \
    coreutils \
    python3-appdaemon \
"
