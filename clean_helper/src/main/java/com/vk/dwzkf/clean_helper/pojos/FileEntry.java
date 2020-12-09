package com.vk.dwzkf.clean_helper.pojos;

import java.nio.file.Path;

import com.vk.dwzkf.clean_helper.exception.BadFilePathException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class FileEntry {
    private String extension;
    private String fileName;
    private String realPath;

    public FileEntry(String extension, String fileName, String realPath) {
        this.extension = extension.toLowerCase();
        this.fileName = fileName;
        this.realPath = realPath;
    }

    public static FileEntry create(Path filePath) {
        log.debug("Create entry from path:{}", filePath.toString());
        String realPath = filePath.toAbsolutePath().toString();
        String fileNameStr = filePath.getFileName().toString();
        int lastIndexDot = fileNameStr.lastIndexOf(".");
        if (lastIndexDot != -1) {
            String fileName = fileNameStr.substring(0, lastIndexDot);
            String extension = fileNameStr.substring(lastIndexDot, 
                fileNameStr.length()
            );
            return new FileEntry(extension, fileName, realPath);
        }
        else {
            throw new BadFilePathException(filePath.toString());
        }
    }

    public boolean isEmpty() {
        return (extension == null || extension.isBlank()
                || fileName == null || fileName.isBlank()
                || realPath == null || realPath.isBlank()
        );
    }
}
