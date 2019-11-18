SUMMARY = "http client/server for asyncio"
HOMEPAGE = "https://github.com/KeepSafe/aiohttp/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=c76b717025e9f23e50092cd39a213d56"

PYPI_PACKAGE = "aiohttp"
inherit pypi setuptools3

SRC_URI += "file://0001-Improve-make-clean-compatibility.patch"

SRC_URI[md5sum] = "80a6e0c6c452d511d1d37755d6f0995a"
SRC_URI[sha256sum] = "51afec6ffa50a9da4cdef188971a802beb1ca8e8edb40fa429e5e529db3475fa"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-attrs (>=17.3.0) \
    ${PYTHON_PN}-chardet (>=2.0) \
    ${PYTHON_PN}-multidict (>=4.0) \
    ${PYTHON_PN}-async-timeout (>=2.0) \
    ${PYTHON_PN}-yarl-appdaemon (>=1.0) \
    ${PYTHON_PN}-idna-ssl (>=1.0.0) \
    ${PYTHON_PN}-misc \
"
