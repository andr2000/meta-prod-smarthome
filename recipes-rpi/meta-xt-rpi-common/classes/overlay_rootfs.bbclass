#
# This class is used for optional components or those
# which are not mission critical.
#

addtask rootfs_deploy_secret after do_rootfs before do_image
do_rootfs_deploy_secret () {
    cd ${IMAGE_ROOTFS}

    if [ -d ".$SMARTHOME_RPI_MNT_SECRET" ]; then
        # Pack ${SMARTHOME_RPI_MNT_SECRET} as an additional image and remove from rootfs
        find .${SMARTHOME_RPI_MNT_SECRET} | cpio --quiet -R 0:0 -H newc -o | gzip -9 -n > ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-secret.cpio.gz
        rm -rf ${IMAGE_ROOTFS}/../rootfs-secret
        mkdir ${IMAGE_ROOTFS}/../rootfs-secret
        mv .${SMARTHOME_RPI_MNT_SECRET}/* ${IMAGE_ROOTFS}/../rootfs-secret
        ln -sfr ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}-secret.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs-secret.cpio.gz
    fi
}

inherit image

# Prepare overlay to be ready for mounting on the target,
# remove unneeded files, move all to /mnt/overlay/user and
# create working directory
do_rootfs_prepare_overlay () {
    cd "${IMAGE_ROOTFS}"

    # Remove all files that are already installed into the initramfs
    find ../rootfs -not -type d | cut -c 11- | xargs rm -f
    # We now may have empty directories - remove those as well
    find . -empty -type d -delete

    # Create working directory for overlyafs so it can be mounted from fstab
    mkdir workdir
}

addtask rootfs_create_overlay after do_rootfs before do_image
fakeroot python do_rootfs_create_overlay () {
    pkgs_install = d.getVar("PACKAGE_OVERLAY_ROOTFS_INSTALL") or ""
    if pkgs_install:
        rootfs = d.getVar("IMAGE_ROOTFS")
        d.setVar("IMAGE_ROOTFS", rootfs + "-overlay")
        imgdeploydir = d.getVar("IMGDEPLOYDIR")
        d.setVar("IMGDEPLOYDIR", imgdeploydir + "-overlay")
        image_name = d.getVar("IMAGE_NAME")
        d.setVar("IMAGE_NAME", image_name + "-overlay")

        d.setVar("PACKAGE_INSTALL", pkgs_install)
        pkgs_exclude = d.getVar("PACKAGE_OVERLAY_ROOTFS_EXCLUDE") or ""
        d.setVar("PACKAGE_EXCLUDE", pkgs_exclude)
        d.setVar("ROOTFS_PREPROCESS_COMMAND", "")
        d.setVar("ROOTFS_POSTPROCESS_COMMAND", "")

        bb.build.exec_func("do_rootfs", d)

        # Prepare mount points and remove unneeded
        bb.build.exec_func("do_rootfs_prepare_overlay", d)
}

addtask rootfs_pack_overlay after do_image_complete before do_build
do_rootfs_pack_overlay () {
    cd "${IMAGE_ROOTFS}-overlay"
    # IMAGE_NAME_SUFFIX gets evaluated while generating this script,
    # so we cannot use it as is below: use own
    export IMAGE_NAME_SUFFIX_OVL="${IMAGE_NAME_SUFFIX}-overlay"

    # Remove previous overlay cpio.gz archives if any
    find ${DEPLOY_DIR_IMAGE}/ -name "*overlay*" -type f -delete || true
    # Create working directory for overlyafs so it can be mounted from fstab
    # Now pack into the archive
    find . | cpio --quiet -R 0:0 -H newc -o | gzip -9 -n > ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX_OVL}.cpio.gz
    chmod 0644 ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX_OVL}.cpio.gz
    cp -f ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX_OVL}.cpio.gz ${DEPLOY_DIR_IMAGE}/
    ln -sfr ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX_OVL}.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs-overlay.cpio.gz
}
