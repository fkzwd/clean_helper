package com.vk.dwzkf.clean_helper.exception;

public class FileNotExistsException extends CleanHelperException {
    public FileNotExistsException(String filePath) {
        super("File not exists: path="+filePath);
    }
}
