package com.tintingpatch.FileConverter.util;

public class CouldNotConvertFileException extends RuntimeException {
    public CouldNotConvertFileException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
