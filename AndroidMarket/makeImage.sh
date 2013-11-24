#!/bin/bash
export SIZE=512;convert avatar.png -resize ${SIZE}x${SIZE} avatar${SIZE}.png
export SIZE=144;convert avatar.png -resize ${SIZE}x${SIZE} avatar${SIZE}.png
export SIZE=96;convert avatar.png -resize ${SIZE}x${SIZE} avatar${SIZE}.png
export SIZE=72;convert avatar.png -resize ${SIZE}x${SIZE} avatar${SIZE}.png
export SIZE=48;convert avatar.png -resize ${SIZE}x${SIZE} avatar${SIZE}.png
