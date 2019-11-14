SUMMARY = "Yet another URL library"
HOMEPAGE = "https://github.com/aio-libs/yarl/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b334fc90d45983db318f54fd5bf6c90b"

PYPI_PACKAGE = "yarl"
inherit pypi setuptools3

SRC_URI[md5sum] = "d0337b9c56af38ca66281c31ab58c125"
SRC_URI[sha256sum] = "6af895b45bd49254cc309ac0fe6e1595636a024953d710e01114257736184698"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-multidict (>=4.0) \
    ${PYTHON_PN}-idna (>=2.0) \
"
