#!/bin/bash -x

mkdir -p /mnt/overlay/workdir || true

mount -t overlay overlay -o lowerdir=/usr,upperdir=/mnt/overlay/usr,workdir=/mnt/overlay/workdir /usr
