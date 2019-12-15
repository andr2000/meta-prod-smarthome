SUMMARY = "The Home Assistant frontend"
HOMEPAGE = "https://github.com/home-assistant/home-assistant-polymer"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=f4eda51018051de136d3b3742e9a7a40"

inherit pypi setuptools3

SRC_URI[md5sum] = "b5d5827de9262b2ccabe6004744e68ce"
SRC_URI[sha256sum] = "420136b9d4a6b5bd7d9b0c28ebdd56c724a8f8170a5a8c5c5a3b51ac2862eba9"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-user-agents \
"
