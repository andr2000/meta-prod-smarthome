SUMMARY = "http client/server for asyncio"
HOMEPAGE = "https://github.com/KeepSafe/aiohttp/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cf056e8e7a0a5477451af18b7b5aa98c"

inherit pypi setuptools3

SRC_URI += "file://0001-Improve-make-clean-compatibility.patch"

SRC_URI[md5sum] = "ca40144c199a09fc1a141960cf6295f0"
SRC_URI[sha256sum] = "259ab809ff0727d0e834ac5e8a283dc5e3e0ecc30c4d80b3cd17a4139ce1f326"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-attrs (>=17.3.0) \
    ${PYTHON_PN}-chardet (>=2.0) \
    ${PYTHON_PN}-multidict (>=4.0) \
    ${PYTHON_PN}-async-timeout (>=3.0) \
    ${PYTHON_PN}-yarl (>=1.0) \
"
