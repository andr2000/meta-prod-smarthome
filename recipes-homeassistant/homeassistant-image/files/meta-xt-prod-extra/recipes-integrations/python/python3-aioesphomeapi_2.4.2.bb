SUMMARY = "Python Client for ESPHome native API. Used by Home Assistant."
HOMEPAGE = "https://github.com/esphome/aioesphomeapi"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://setup.py;md5=599347b6f0416aad44748741735b41ef"

inherit pypi setuptools3

SRC_URI[md5sum] = "561c928ee4f8b7f8542d4017ffdcb672"
SRC_URI[sha256sum] = "e43e0fd628506f95752e90ab1579e5495183cc3c46915d0b0a062975cb5d181b"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-protobuf \
    ${PYTHON_PN}-attrs \
    ${PYTHON_PN}-zeroconf \
"
