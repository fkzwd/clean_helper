package com.vk.dwzkf.clean_helper;

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
	private static final String TEST_PATH = "C:\\Users\\lives\\Desktop\\trash\\C++";
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CleanHelperApplication.class, 
			args);
		ctx.getBean(CleanHelperApplication.class).app();
	}

	public void app() {
		FileScaner fileScaner = new FileScaner();
		FileMap fileMap = new FileMap(fileScaner.scanDirectory(TEST_PATH));
		Map<String, List<FileEntry>> map = fileMap.getMap();
		for (Map.Entry<String, List<FileEntry>> e : map.entrySet()) {
			System.out.println("----------------FileType: "+e.getKey());
			for (FileEntry fe : e.getValue()) {
				System.out.println(fe);
			}
		}
	}
}
