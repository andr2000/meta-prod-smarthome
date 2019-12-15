SUMMARY = "A Python Slugify application that handles Unicode"
HOMEPAGE = "https://github.com/un33k/python-slugify"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7946d011d653bfcfbb24480377867128"

PYPI_PACKAGE = "python-slugify"

inherit pypi setuptools3

SRC_URI[md5sum] = "d2f490f854f66cc723a004f5328a25b4"
SRC_URI[sha256sum] = "a8fc3433821140e8f409a9831d13ae5deccd0b033d4744d94b31fea141bdd84c"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-pycryptodome \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-ecdsa \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-text-unidecode \
"
