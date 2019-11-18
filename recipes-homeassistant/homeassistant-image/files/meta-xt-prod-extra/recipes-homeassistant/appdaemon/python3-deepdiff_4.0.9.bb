SUMMARY = "Deep Difference and Search of any Python object/data."
HOMEPAGE = "https://github.com/seperman/deepdiff"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8d89e360d69f92be22e7f73cdfdf7ca6"

inherit pypi setuptools3

SRC_URI[md5sum] = "729d0e89e0258f32337716124043e0d0"
SRC_URI[sha256sum] = "5e2343398e90538edaa59c0c99207e996a3a834fdc878c666376f632a760c35a"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-jsonpickle \
    ${PYTHON_PN}-ordered-set-appdaemon \
"
