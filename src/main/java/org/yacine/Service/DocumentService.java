package org.yacine.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.yacine.Entity.Document;
import org.yacine.Entity.Categorie;
import org.yacine.Entity.User;
import org.yacine.Repository.DocumentRepository;
import org.yacine.Repository.UserRepository;

@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	@Autowired
	UserService userService;
 
	@Autowired UserRepository userRepository;

	// upload file in folder and save document in data base
	public String Upload(MultipartFile[] file, Document document, User user , Categorie categorie) {

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

			path = Paths.get(userService.GetUserPath(user.getId()) + fileName);

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
			document.setCategorie(categorie);
			documentRepository.save(document);

		}
		return path + "    " + format[1] + "    " + size + "    " + test;
	}

	// delete file data base and folder by id
	public void delete(Long id) throws IOException {

		Document document = documentRepository.findById(id).get();
		Path path = Paths.get(document.getPath());

		Files.delete(path);

		documentRepository.deleteById(id);
	}
	
	// search file by document object
	public List<Document> SearchByPath(Document document){
		
		String path= userService.GetUserPath(document.getUser().getId());
		
		return documentRepository.findByPathContainsAndFormat(path, document.getFormat());
	}
	
	// download file by id
	public ResponseEntity<InputStreamResource> Download(Long id) {

		Document document = documentRepository.findById(id).get();
		//Document document = documentRepository.findBynom(nom);
		try {
			InputStreamResource filestream = new InputStreamResource(new FileInputStream(new File(document.getPath())));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Contenet-Disposition", "attachement;filename=" + document.getNom());

			return ResponseEntity.ok().headers(headers)
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(filestream);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// get PDF content to browser
 	public byte[] getpdf(Long id) throws IOException {
		// get document by id
		Document document = documentRepository.findById(id).get();
		// pointer new file to path of document
		File file = new File(document.getPath());
		Path path = Paths.get(file.toURI());
		// return byte File
		return Files.readAllBytes(path);
	}

	
//	public Document Update(Document document, Long id) {
//		Document doc = documentRepository.findById(id).get();
//		document.setId(id);
//		document.setNom(doc.getNom());
//		document.setPath(doc.getPath());
//		document.setSize(doc.getSize());
//		document.setFormat(doc.getFormat());
//		document.setDate(doc.getDate());
//		documentRepository.save(document);
//		return document;
//	}
//	
//
//	public void Update(MultipartFile file, Document document, User user, Categorie categorie, Long id) {
//
//		// get information from file
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		String []  format = fileName.split("\\.");
//		float size = file.getSize() / 1024;
//
//		Path path = Paths.get(userService.GetUserPath(user.getId()) + fileName);
//		
//		try {
//			// copy file to destination
//			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//
//		
//		// add files information to data base
//		document.setId(id);
//		document.setNom(fileName);
//		document.setPath(path + "");
//		document.setSize(size);
//		document.setFormat(format[1]);
//		document.setDate(new Date());
//		document.setUser(user);
//		document.setCategorie(categorie);
//		documentRepository.save(document);
//
//	}
	

}
