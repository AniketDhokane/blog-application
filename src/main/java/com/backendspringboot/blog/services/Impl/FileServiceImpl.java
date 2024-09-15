package com.backendspringboot.blog.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backendspringboot.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		//File Name
		String nameString= file.getOriginalFilename();
		//abc.png
		
		//random name generate file
		String randomID=UUID.randomUUID().toString();
		String fileName1= randomID.concat(nameString.substring(nameString.lastIndexOf(".")));
		
		//FullPath
		String filePath= path + File.separator +fileName1; 
		
		//create folder if not created
		File file2= new File(path);
		if (!file2.exists()) {
			file2.mkdir();
			}
		
		//file copy 
		 Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return nameString;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String fullPath= path + File.separator+ fileName;
		InputStream is =new FileInputStream(fullPath);
		//db logic to return input stream 
		return is;
	}

}
