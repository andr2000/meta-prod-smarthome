# Use git version, not pypi
S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=40042432428a243619cb06d74913f5fa"
PYPI_SRC_URI = "\
    git://github.com/andr2000/ebusdpy.git;branch=master \
"
SRCREV = "${AUTOREV}"
