package com.tintingpatch.FileConverter.filetypes.image;

import com.tintingpatch.FileConverter.util.CouldNotConvertFileException;
import com.tintingpatch.FileConverter.util.ImageFiletype;
import com.tintingpatch.FileConverter.util.ImageFiletypes;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;
import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SVGImage implements ImageFiletype {
    @Override
    public BufferedImage read(File file) throws IOException, CouldNotConvertFileException {
        BufferedImage image = null;

        // Rendering hints can't be set programmatically, so will override with defaults
        // SVG documents can still override these defaults.
        String css = "svg {" +
                "shape-rendering: geometricPrecision;" +
                "text-rendering:  geometricPrecision;" +
                "color-rendering: optimizeQuality;" +
                "image-rendering: optimizeQuality;" +
                "}";
        File cssFile = File.createTempFile("batik-default-override-", ".css");
        FileUtils.writeStringToFile(cssFile, css);

        TranscodingHints transcoderHints = new TranscodingHints();
        transcoderHints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
        transcoderHints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION,
                SVGDOMImplementation.getDOMImplementation());
        transcoderHints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI,
                SVGConstants.SVG_NAMESPACE_URI);
        transcoderHints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, "svg");
        transcoderHints.put(ImageTranscoder.KEY_USER_STYLESHEET_URI, cssFile.toURI().toString());

        //Converting the image
        try {
            TranscoderInput input = new TranscoderInput(new FileInputStream(file));

            ImageTranscoder transcoder = new ImageTranscoder() {

                @Override
                public BufferedImage createImage(int w, int h) {
                    return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                }

                @Override
                public void writeImage(BufferedImage image, TranscoderOutput out) throws TranscoderException {
                    image = image;
                }
            };
            transcoder.setTranscodingHints(transcoderHints);
            transcoder.transcode(input, null);
        }
        catch (TranscoderException ex) {
            ex.printStackTrace();
            throw new IOException("Couldn't convert " + file);
        }
        finally {
            cssFile.delete();
        }

        return image;
    }

    @Override
    public void write(File file, BufferedImage image) throws CouldNotConvertFileException {
        throw new CouldNotConvertFileException("Incompatible filetype", new Throwable("Raster cannot be converted to Vector"));
    }

    @Override
    public ImageFiletypes getImageFileType() {
        return ImageFiletypes.SVG;
    }
}
