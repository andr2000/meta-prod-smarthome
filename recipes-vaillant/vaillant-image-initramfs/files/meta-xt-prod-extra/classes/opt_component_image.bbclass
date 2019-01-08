#
# This class is used for optional components or those
# which are not mission critical.
#

addtask rootfs_pack_opt after do_rootfs before do_image
do_rootfs_pack_opt () {
    cd ${IMAGE_ROOTFS}

    if [ -d "opt" ]; then
        # Search for all shared libraries at /opt and add those to ld.so.cache
        find opt -type f -executable -exec file -i '{}' \; | grep sharedlib | cut -f1 -d ':' | xargs dirname | sort -u | sed 's#^#/#' > ${IMAGE_ROOTFS}/etc/ld.so.conf
        ${STAGING_DIR_NATIVE}${bindir_native}/ldconfig -r . -c new -f etc/ld.so.conf

        # Pack /opt as an additional image and remove from initramfs
        find ./opt | cpio --quiet -R 0:0 -H newc -o | gzip -9 -n > ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-opt.cpio.gz
        rm -rf ${IMAGE_ROOTFS}/../rootfs-opt
        mkdir ${IMAGE_ROOTFS}/../rootfs-opt
        mv opt/* ${IMAGE_ROOTFS}/../rootfs-opt
        ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-opt.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs-opt.cpio.gz
    fi

    if [ -d ".$VAILLANT_MNT_SECRET" ]; then
        # Pack ${VAILLANT_MNT_SECRET} as an additional image and remove from initramfs
        find .${VAILLANT_MNT_SECRET} | cpio --quiet -R 0:0 -H newc -o | gzip -9 -n > ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-secret.cpio.gz
        rm -rf ${IMAGE_ROOTFS}/../rootfs-secret
        mkdir ${IMAGE_ROOTFS}/../rootfs-secret
        mv .${VAILLANT_MNT_SECRET}/* ${IMAGE_ROOTFS}/../rootfs-secret
        ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-secret.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs-secret.cpio.gz
    fi
}