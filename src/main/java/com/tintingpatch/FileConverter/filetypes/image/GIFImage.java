package com.tintingpatch.FileConverter.filetypes.image;

import com.tintingpatch.FileConverter.util.CouldNotConvertFileException;
import com.tintingpatch.FileConverter.util.Filetype;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GIFImage implements Filetype {
    public int currentImage = 0;
    @Override
    public BufferedImage read(File file) throws IOException, CouldNotConvertFileException {
        return ImageIO.read(file);
    }

    public List<BufferedImage> readEveryImage(File file){
        ArrayList<BufferedImage> images = new ArrayList<>();
        try {
            String[] imageatt = new String[]{
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader reader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(file);
            reader.setInput(ciis, false);

            int noi = reader.getNumImages(true);
            BufferedImage master = null;

            for (int i = 0; i < noi; i++) {
                currentImage = i;

                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);

                    if(nodeItem.getNodeName().equals("ImageDescriptor")){
                        Map<String, Integer> imageAttr = new HashMap<String, Integer>();

                        for (int k = 0; k < imageatt.length; k++) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(imageatt[k]);
                            imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
                        }
                        if(i==0){
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        }
                        master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                    }
                }
                images.add(master);
            }
        }catch (Exception exception){
            throw new CouldNotConvertFileException("Could not convert gif to buffered image: " + exception.getMessage(), exception.getCause());
        }finally {
            currentImage = 0;
        }
        return images;
    }

    @Override
    public void write(File file, BufferedImage image) throws CouldNotConvertFileException {
        try {
            ImageIO.write(image, "gif", file);
        } catch (IOException e) {
            throw new CouldNotConvertFileException("Could not convert buffered image to gif: " + e.getMessage(), e.getCause());
        }
    }
}
