package com.tintingpatch.FileConverter.util;

public enum ImageFiletypes {
    GIF(true),
    JPG(true),
    PNG(true),
    SVG(false),
    WEBP(true);

    final boolean canConvertToImage;

    ImageFiletypes(boolean canConvertToImage){
        this.canConvertToImage = canConvertToImage;
    }
}
