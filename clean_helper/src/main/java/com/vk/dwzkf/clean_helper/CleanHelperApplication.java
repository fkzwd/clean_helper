package com.vk.dwzkf.clean_helper;

import java.util.List;
import java.util.Map;

import com.vk.dwzkf.clean_helper.pojos.FileEntry;
import com.vk.dwzkf.clean_helper.pojos.FileMap;
import com.vk.dwzkf.clean_helper.scaner.FileScaner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CleanHelperApplication {
	private static final String TEST_PATH = "C:\\Users\\lives\\Desktop\\trash\\C++";
	public static void main(String[] args) {
		FileScaner fileScaner = new FileScaner();
		FileMap fileMap = new FileMap(fileScaner.scanDirectory(TEST_PATH));
		Map<String, List<FileEntry>> map = fileMap.getMap();
		for (Map.Entry<String, List<FileEntry>> e : map.entrySet()) {
			log.info("Directory: {}", e.getKey());
			for (FileEntry fe : e.getValue()) {
				System.out.println("\t\t File:"+fe.getFileName()+fe.getExtension());
			}
		}
	}

}
