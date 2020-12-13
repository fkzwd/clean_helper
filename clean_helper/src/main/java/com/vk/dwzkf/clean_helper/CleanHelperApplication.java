package com.vk.dwzkf.clean_helper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.vk.dwzkf.clean_helper.pojos.FileEntry;
import com.vk.dwzkf.clean_helper.pojos.FileMap;
import com.vk.dwzkf.clean_helper.scaner.FileScaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@SpringBootApplication
public class CleanHelperApplication {
	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("No args provided.");
		}
		String testPath = args[0];
		if (!Files.exists(Paths.get(testPath))) {
			throw new IllegalArgumentException("Path not exists. Path:"+testPath);
		}
		ApplicationContext ctx = SpringApplication.run(CleanHelperApplication.class, 
			args);
		ctx.getBean(CleanHelperApplication.class).app(testPath);
	}

	public void app(String testPath) {
		FileScaner fileScaner = new FileScaner();
		FileMap fileMap = new FileMap(fileScaner.scanDirectory(testPath));
		Map<String, List<FileEntry>> map = fileMap.getMap();
		for (Map.Entry<String, List<FileEntry>> e : map.entrySet()) {
			System.out.println("----------------FileType: "+e.getKey());
			for (FileEntry fe : e.getValue()) {
				System.out.println(fe);
			}
		}
	}
}
