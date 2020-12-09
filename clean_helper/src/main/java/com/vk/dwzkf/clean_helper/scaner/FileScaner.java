package com.vk.dwzkf.clean_helper.scaner;

import static com.vk.dwzkf.clean_helper.util.CodeUtil.not;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

import com.vk.dwzkf.clean_helper.exception.BadFilePathException;
import com.vk.dwzkf.clean_helper.exception.FileVisitException;
import com.vk.dwzkf.clean_helper.pojos.FileEntry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileScaner {
    public List<FileEntry> scanDirectory(String directory) {
        Path directoryPath = Paths.get(directory);
        return scanDirectory(directoryPath);
    }

    public List<FileEntry> scanDirectory(Path directoryPath) {
        List<FileEntry> fileEntries = new LinkedList<>();
        try {
            Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path filePath, 
                        BasicFileAttributes attrs) throws IOException 
                {
                    if (not(Files.isDirectory(filePath))) {
                        try {
                            FileEntry fileEntry = FileEntry.create(filePath);
                            fileEntries.add(fileEntry);
                        } catch (BadFilePathException ex) {
                            log.error("Error craeting fileEntry.", ex);
                        }
                    };
                    return FileVisitResult.CONTINUE;
                }
                
            });
        } catch (IOException ex) {
            log.error("IOException error occured.", ex);
            throw new FileVisitException(directoryPath.toString());
        }
        return fileEntries;
    }
}
