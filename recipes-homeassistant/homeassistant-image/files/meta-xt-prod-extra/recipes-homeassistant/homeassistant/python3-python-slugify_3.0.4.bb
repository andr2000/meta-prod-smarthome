SUMMARY = "A Python Slugify application that handles Unicode"
HOMEPAGE = "https://github.com/un33k/python-slugify"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7946d011d653bfcfbb24480377867128"

PYPI_PACKAGE = "python-slugify"

inherit pypi setuptools3

SRC_URI[md5sum] = "2c50f359626f96034b83c75f4863e2f1"
SRC_URI[sha256sum] = "575d03256a132fc1efb4c52966c6eb11c57a13b071618f0b26076057a23f6937"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-pycryptodome \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-ecdsa \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-text-unidecode \
"
