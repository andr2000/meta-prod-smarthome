#!/bin/bash -x

mkdir -p /mnt/data/overlay/workdir || true

mount -t overlay overlay -o lowerdir=/usr,upperdir=/mnt/overlay/usr,workdir=/mnt/data/overlay/workdir /usr
