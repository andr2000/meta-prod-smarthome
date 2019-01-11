#!/bin/bash -x

mount -t overlay overlay -o lowerdir=/usr,upperdir=/mnt/data/dev/overlay/usr,workdir=/mnt/data/dev/workdir /usr
