package com.tintingpatch.FileConverter.util;

import java.util.ArrayList;

public class FileTypeUtils {
    //This function lists all filetypes the image can be converted to
    public static String[] listFiletypesByType(String ending){
        ArrayList<String> output = new ArrayList();
        String[] endings = {"gif", "jpg", "jpeg", "png", "webp"};

        for (String s : endings) {
            if(!s.equals(ending)){
                output.add(s);
            }
        }

        return output.toArray(new String[0]);
    }
}
