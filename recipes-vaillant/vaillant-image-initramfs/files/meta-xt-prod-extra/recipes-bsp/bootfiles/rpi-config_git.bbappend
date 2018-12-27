do_deploy_append() {
    echo "initramfs initrd followkernel" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
