#
# This class is used for optional components or those
# which are not mission critical.
#

addtask rootfs_pack_opt after do_rootfs before do_image
do_rootfs_pack_opt () {
    cd ${IMAGE_ROOTFS}

    if [ -d ".$VAILLANT_MNT_SECRET" ]; then
        # Pack ${VAILLANT_MNT_SECRET} as an additional image and remove from initramfs
        find .${VAILLANT_MNT_SECRET} | cpio --quiet -R 0:0 -H newc -o | gzip -9 -n > ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-secret.cpio.gz
        rm -rf ${IMAGE_ROOTFS}/../rootfs-secret
        mkdir ${IMAGE_ROOTFS}/../rootfs-secret
        mv .${VAILLANT_MNT_SECRET}/* ${IMAGE_ROOTFS}/../rootfs-secret
        ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-secret.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs-secret.cpio.gz
    fi
}

inherit image

addtask rootfs_create_overlay after do_populate_sysroot before do_build
fakeroot python do_rootfs_create_overlay () {
    pkgs_install = d.getVar("PACKAGE_OVERLAY_ROOTFS_INSTALL") or ""
    if pkgs_install:
        rootfs = d.getVar("IMAGE_ROOTFS")
        d.setVar("IMAGE_ROOTFS", rootfs + "-overlay")
        d.setVar("PACKAGE_INSTALL", pkgs_install)
        pkgs_exclude = d.getVar("PACKAGE_OVERLAY_ROOTFS_EXCLUDE") or ""
        d.setVar("PACKAGE_EXCLUDE", pkgs_exclude)
        d.setVar("ROOTFS_PREPROCESS_COMMAND", "")
        d.setVar("ROOTFS_POSTPROCESS_COMMAND", "")

        bb.build.exec_func("do_rootfs", d)
}

addtask rootfs_pack_overlay after do_rootfs_create_overlay before do_build
do_rootfs_pack_overlay () {
    cd "${IMAGE_ROOTFS}-overlay"

    find . | cpio --quiet -R 0:0 -H newc -o | gzip -9 -n > ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-overlay.cpio.gz
    ln -sfr  ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-overlay.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs-overlay.cpio.gz
}
