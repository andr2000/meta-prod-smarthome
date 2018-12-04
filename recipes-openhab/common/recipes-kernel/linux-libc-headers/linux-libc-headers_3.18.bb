#require recipes-kernel/linux-libc-headers/linux-libc-headers.inc
require linux-libc-headers.inc

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/andr2000/linux-orangepi-4g-iot.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

LINUX_VERSION ?= "3.18"

