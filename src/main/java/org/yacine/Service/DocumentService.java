package org.yacine.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yacine.Entity.Document;
import org.yacine.Entity.User;
import org.yacine.Repository.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	UserService userService;

	// upload documents
	public String Upload(MultipartFile[] file, Document document, User user) {
		String fileName = "";
		String[] format = null;
		float size = 0;
		Path path = null;

		Long test = documentRepository.count();

		for (MultipartFile multipartFile : file) {

			// get information from file
			fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			format = fileName.split("\\.");
			size = multipartFile.getSize() / 1024;
			
			path = Paths.get(userService.GetUserPath(user.getId())+ fileName);

			// test if file is already exist
			if (documentRepository.countByNomAndUserId(fileName, user.getId()) > 0) {
				continue;
			}

			try {
				// copy file to destination
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// add files information to data base
			document.setId(documentRepository.count() + 1);
			document.setNom(fileName);
			document.setPath(path + "");
			document.setSize(size);
			document.setFormat(format[1]);
			document.setDate(new Date());
			document.setUser(user);
			documentRepository.save(document);

		}
		return path + "    " + format[1] + "    " + size + "    " + test;
	}
}
