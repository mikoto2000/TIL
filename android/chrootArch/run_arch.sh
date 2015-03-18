#!/system/bin/sh

mount -t ext2 /dev/block/mmcblk1p2 /sdcard/arch
mount -t proc /proc /sdcard/arch/proc
mount -o bind /sys /sdcard/arch/sys
mount -o bind /dev /sdcard/arch/dev
mount -o bind /sdcard/Download /sdcard/arch/home/mikoto/download

chroot /sdcard/arch /bin/bash

umount /sdcard/arch/home/mikoto/download
umount /sdcard/arch/dev
umount /sdcard/arch/sys
umount /sdcard/arch/proc
umount /sdcard/arch
