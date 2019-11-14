SUMMARY = "jinja2 template renderer for aiohttp.web (http server for asyncio)"
HOMEPAGE = "https://github.com/aio-libs/aiohttp_jinja2/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c82758543767c96574b6e29fc478fb73"

PYPI_PACKAGE = "aiohttp-jinja2"
inherit pypi setuptools3

SRC_URI[md5sum] = "a22d9fe953246d8cc2058b0628912155"
SRC_URI[sha256sum] = "0f390693f46173d8ffb95669acbb0e2a3ec54ecce676703510ad47f1a6d9dc83"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-jinja2 (>=2.7) \
    ${PYTHON_PN}-aiohttp-appdaemon (>=0.20) \
"
