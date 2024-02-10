package com.tintingpatch.FileConverter.util;

import com.tintingpatch.FileConverter.filetypes.image.*;

public enum ImageFiletypes {
    GIF(new GIFImage()),
    JPG(new JPGImage()),
    PNG(new JPGImage()),
    SVG(new SVGImage()),
    WEBP(new WEBPImage());

    final ImageFiletype imageFiletypeClass;

    ImageFiletypes(ImageFiletype imageFiletypeClass){
        this.imageFiletypeClass = imageFiletypeClass;
    }
}
