package com.vk.dwzkf.clean_helper.exception;

public class FileVisitException extends CleanHelperException {
    public FileVisitException(String filePath) {
        super("Error while visiting path:"+filePath);
    }
}
