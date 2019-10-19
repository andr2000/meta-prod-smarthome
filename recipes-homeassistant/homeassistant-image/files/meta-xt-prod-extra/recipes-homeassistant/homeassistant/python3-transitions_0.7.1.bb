SUMMARY = "MQTT client/broker using Python asynchronous I/O"
HOMEPAGE = "https://github.com/beerfactory/hbmqtt"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8734a2c2e13bbf9c2fd1b6cb64af8219"

PYPI_PACKAGE = "transitions"

inherit pypi setuptools3

SRC_URI[md5sum] = "61d84b188f241d0e74e8d283f086709c"
SRC_URI[sha256sum] = "b73015080833b753cbb4a10f51f8234924ddfbdbaf33539fee4e4f3abfff454d"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-six \
"
