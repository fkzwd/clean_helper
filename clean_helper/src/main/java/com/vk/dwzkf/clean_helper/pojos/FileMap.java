package com.vk.dwzkf.clean_helper.pojos;

import static com.vk.dwzkf.clean_helper.util.CodeUtil.not;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.vk.dwzkf.clean_helper.exception.BadFileEntryException;
import com.vk.dwzkf.clean_helper.exception.FileNotExistsException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileMap {
    private Map<String, List<FileEntry>> entries = new ConcurrentHashMap<>();

    public FileMap() {

    }

    public FileMap(List<FileEntry> fileEntries) {
        fileEntries.forEach(e -> addEntry(e));
    }

    public Map<String, List<FileEntry>> getMap() {
        return new HashMap<>(entries);
    }

    public void addEntry(FileEntry fileEntry) {
        try {
        checkEntry(fileEntry);
        entries.putIfAbsent(fileEntry.getExtension(), 
            Collections.synchronizedList(new LinkedList<>())
        );
        entries.get(fileEntry.getExtension()).add(fileEntry);
        } catch (BadFileEntryException ex) {
            log.error("BadFileEntry: {}", fileEntry.getRealPath(), ex);
        } catch (FileNotExistsException ex2) {
            log.error("BadFileEntry: {}", fileEntry.getRealPath(), ex2);
        }
    }

    private void checkEntry(FileEntry fileEntry) {
        if (fileEntry == null || fileEntry.isEmpty()) {
            log.error("Bad file entry! FileEntry'{}'", fileEntry);
            throw new BadFileEntryException();
        }
        String realFilePath = fileEntry.getRealPath();
        Path actualFile = Paths.get(realFilePath);
        if (not(Files.exists(actualFile))) {
            throw new FileNotExistsException(realFilePath);
        }
    }

    public void removeEntry(FileEntry fileEntry) {
        List<FileEntry> fileEntryList = entries.get(fileEntry.getExtension());
        if (fileEntryList != null) {
            fileEntryList.remove(fileEntry);
        }
        else {
            log.error("Bad file entry:'{}'", fileEntry);
            throw new BadFileEntryException();
        }
    }

    public void removeEntryAndFile(FileEntry fileEntry) {
        removeEntry(fileEntry);
        try {
            Path filePath = Paths.get(fileEntry.getRealPath());
            Files.delete(filePath);
        } catch (IOException ex) {
            log.error("Error while delete file'{}'",
                fileEntry.getRealPath(),
                ex
            );
            throw new BadFileEntryException();
        }
    }
}
