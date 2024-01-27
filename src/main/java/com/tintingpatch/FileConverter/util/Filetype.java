package com.tintingpatch.FileConverter.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface Filetype {
    public BufferedImage read(File file) throws IOException, CouldNotConvertFileException;
    public void write(File file, BufferedImage image) throws CouldNotConvertFileException;
}
