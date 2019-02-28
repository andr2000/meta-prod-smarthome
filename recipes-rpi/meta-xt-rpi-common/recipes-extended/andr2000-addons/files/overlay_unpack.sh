#!/bin/bash -x
# Forced umount:
#umount -l $(grep 'overlay' /proc/mounts | awk '{print$2}' | sort -r)

umount /usr
cd /mnt/overlay/overlay
rm -rf *
gzip -cd ../rootfs-overlay.cpio.gz | cpio -imd --quiet
