SUMMARY = "Python support for YAML"
HOMEPAGE = "http://www.pyyaml.org"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a76b4c69bfcf82313bbdc0393b04438a"

PYPI_PACKAGE = "PyYAML"
inherit pypi setuptools3

SRC_URI[md5sum] = "3b07eb596071bac75c886129de881d22"
SRC_URI[sha256sum] = "436bc774ecf7c103814098159fbb84c2715d25980175292c648f2da143909f95"

DEPENDS = "\
    libyaml \
    ${PYTHON_PN} \
    ${PYTHON_PN}-cython-native \
"

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-datetime \
"

BBCLASSEXTEND = "native nativesdk"
