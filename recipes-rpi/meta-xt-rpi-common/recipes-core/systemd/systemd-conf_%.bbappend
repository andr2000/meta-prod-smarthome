do_install_append() {
	# Enable watchdog
	sed -i -e 's/.*RuntimeWatchdogSec.*/RuntimeWatchdogSec=10/' ${D}${sysconfdir}/systemd/system.conf
	sed -i -e 's/.*ShutdownWatchdogSec.*/ShutdownWatchdogSec=5min/' ${D}${sysconfdir}/systemd/system.conf
}
