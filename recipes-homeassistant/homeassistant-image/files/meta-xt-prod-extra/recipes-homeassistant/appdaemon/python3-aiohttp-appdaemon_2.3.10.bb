SUMMARY = "http client/server for asyncio"
HOMEPAGE = "https://github.com/KeepSafe/aiohttp/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=60dd5a575c9bd4339411bdef4a702d46"

PYPI_PACKAGE = "aiohttp"
inherit pypi setuptools3

SRC_URI += "file://0001-Improve-make-clean-compatibility.patch"

SRC_URI[md5sum] = "0ad682f635a0392e26320b1ab7d6dd26"
SRC_URI[sha256sum] = "8adda6583ba438a4c70693374e10b60168663ffa6564c5c75d3c7a9055290964"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-attrs (>=17.3.0) \
    ${PYTHON_PN}-chardet (>=2.0) \
    ${PYTHON_PN}-multidict (>=4.0) \
    ${PYTHON_PN}-async-timeout (>=2.0) \
    ${PYTHON_PN}-yarl-appdaemon (>=1.0) \
    ${PYTHON_PN}-idna-ssl (>=1.0.0) \
"
