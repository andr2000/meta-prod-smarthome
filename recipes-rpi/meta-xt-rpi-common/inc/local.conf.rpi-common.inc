# Local configuration for meta-rpi images
# Yocto Project 2.5 Poky distribution [sumo] branch
# This is a sysvinit system

LICENSE_FLAGS_WHITELIST = "commercial"

DISTRO_FEATURES = "ext2 opengl usbhost ${DISTRO_FEATURES_LIBC}"

DISTRO_FEATURES_BACKFILL_CONSIDERED += "pulseaudio"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

PREFERRED_PROVIDER_udev = "eudev"

MACHINE_FEATURES_remove = "apm"
# Enable hardware clock
MACHINE_FEATURES_BACKFILL_CONSIDERED_remove = "rtc"

IMAGE_FSTYPES = "tar.xz"

# Choose the board you are building for
#MACHINE="raspberrypi"
#MACHINE="raspberrypi0"
#MACHINE="raspberrypi0-wifi"
#MACHINE="raspberrypi2"
#MACHINE = "raspberrypi3"
#MACHINE="raspberrypi-cm"
#MACHINE="raspberrypi-cm3"
MACHINE = "raspberrypi3"

DISABLE_OVERSCAN = "1"
ENABLE_UART = "1"
#ENABLE_RPI3_SERIAL_CONSOLE = "1"
#SERIAL_CONSOLE = "115200 ttyAMA0"
# SERIAL_CONSOLE_forcevariable = ""

DISABLE_RPI_BOOT_LOGO = "1"

#DL_DIR = "${HOME}/oe-sources"
#SSTATE_DIR = "/oe4/rpi/sstate-cache"
#TMPDIR = "/oe4/rpi/tmp-sumo"

DISTRO = "poky"
PACKAGE_CLASSES = "package_ipk"

# i686 or x86_64
SDKMACHINE = "x86_64"

# for no root passwd uncomment the following and comment the two extra user lines
#EXTRA_IMAGE_FEATURES = "debug-tweaks"

# set root password to whatever is requested via SMARTHOME_ROOT_PWD or root
SMARTHOME_ROOT_PWD ?= "root"
INHERIT += "extrausers"
EXTRA_USERS_PARAMS = "usermod -P ${SMARTHOME_ROOT_PWD} root; "

# Mount points
SMARTHOME_RPI_MNT_PERSIST ?= "/persist"

SMARTHOME_TELEMETRY_SSID ?= "tele_wrong_ssid"
SMARTHOME_TELEMETRY_PWD ?= "tele_wrong_pwd"

USER_CLASSES = "image-mklibs image-prelink"
PATCHRESOLVE = "noop"
RM_OLD_IMAGE = "1"
CONF_VERSION = "1"

DEFAULT_TIMEZONE = "EET"

BB_DANGLINGAPPENDS_WARNONLY = "1"

# Disable sshd configuration from meta-rpi
BBMASK = "meta-rpi/recipes-connectivity/openssh"
# Disable old mosquitto from meta-oe sumo branch
BBMASK += "meta-openembedded/meta-oe/recipes-connectivity/mosquitto"
BBMASK += "meta-rpi/recipes-connectivity/mosquitto"
# We provide our own network configuration
BBMASK += "meta-rpi/recipes-core/init-ifupdown"
# Disable Qt from meta-rpi
BBMASK += "meta-rpi/recipes-qt/"
