SUMMARY = "InfluxDB-Python is a client for interacting with InfluxDB. Maintained by @aviau (https://github.com/aviau)."
HOMEPAGE = "https://github.com/influxdb/influxdb-python"
LICENSE = "MIT"
SRCNAME = "influxdb"

LIC_FILES_CHKSUM = "file://LICENSE;md5=046523829184aac3703a4c60c0ae2104"

inherit pypi

RDEPENDS_${PN} = "python3-dateutil"
