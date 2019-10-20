SUMMARY = "PyAV is a Pythonic binding for the [FFmpeg][ffmpeg] libraries. "
HOMEPAGE = "https://github.com/mikeboers/PyAV"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=a9c4cea4308c4521674ecd7c3255d8af"

inherit pypi setuptools3

SRC_URI[md5sum] = "ad9ce4b2c5e676ac252356c677ba501e"
SRC_URI[sha256sum] = "4fb03da095773d283cff051e16cf6af1208b39bf62b97f6c4ef4b22e151ea3f2"

DEPENDS += "\
    ffmpeg \
"

RDEPENDS_${PN} += "\
    ffmpeg \
"
