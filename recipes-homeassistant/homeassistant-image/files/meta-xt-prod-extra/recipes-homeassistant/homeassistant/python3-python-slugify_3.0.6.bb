SUMMARY = "A Python Slugify application that handles Unicode"
HOMEPAGE = "https://github.com/un33k/python-slugify"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7946d011d653bfcfbb24480377867128"

PYPI_PACKAGE = "python-slugify"

inherit pypi setuptools3

SRC_URI[md5sum] = "1b989c0624a356505eb5a406171d64df"
SRC_URI[sha256sum] = "8653d589308c91c67fe5c97a2afda0cfac9492061e69c0db90d1aef68fcd2332"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-pycryptodome \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-ecdsa \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-text-unidecode \
"
