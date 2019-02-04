do_deploy_append() {
    echo "initramfs initrd followkernel" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    # Enable DS3231 RTC on I2C1:
    # Pin#1 3V3
    # Pin#3 SDA
    # Pin#5 SCL
    # Pin#9 GND
    echo "dtoverlay=i2c-rtc,ds3231" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
