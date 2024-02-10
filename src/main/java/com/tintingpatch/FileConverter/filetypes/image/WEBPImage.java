package com.tintingpatch.FileConverter.filetypes.image;

import com.tintingpatch.FileConverter.util.CouldNotConvertFileException;
import com.tintingpatch.FileConverter.util.ImageFiletype;
import com.tintingpatch.FileConverter.util.ImageFiletypes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WEBPImage implements ImageFiletype {
    @Override
    public BufferedImage read(File file) throws IOException, CouldNotConvertFileException {
        return ImageIO.read(file);
    }

    @Override
    public void write(File file, BufferedImage image) throws CouldNotConvertFileException {
        try {
            ImageIO.write(image, "webp", file);
        } catch (IOException e) {
            throw new CouldNotConvertFileException("Could not convert buffered image to webp: " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public ImageFiletypes getImageFileType() {
        return ImageFiletypes.WEBP;
    }
}
