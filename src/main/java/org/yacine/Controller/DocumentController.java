package org.yacine.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yacine.Entity.Document;
import org.yacine.Entity.Categorie;
import org.yacine.Entity.User;
import org.yacine.Service.DocumentService;

@RestController
@RequestMapping("api/")
public class DocumentController {

	@Autowired
	DocumentService documentService;

	@GetMapping("index")
	public String index() {
		return "index";
	}

	@PostMapping("upload")
	public ResponseEntity<String> Upload(@RequestParam("file") MultipartFile[] file, @ModelAttribute Document document,
			@ModelAttribute User user, @ModelAttribute Categorie categorie) {
		return new ResponseEntity<String>(documentService.Upload(file, document, user, categorie), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<Void> Delete(@PathVariable Long id) throws IOException {
		documentService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
//	@PutMapping(path = "update/{id}")
//	public ResponseEntity<Document> Update(@RequestBody Document document, @PathVariable Long id){
//		
//		return new ResponseEntity<Document>(documentService.Update(document, id),HttpStatus.OK);
//	}
//	
//	@PutMapping(path = "updatee/{id}")
//	public ResponseEntity<Void> Update(@RequestParam("file") MultipartFile file, @ModelAttribute Document document,
//			@ModelAttribute(name = "u") User user, @ModelAttribute(name = "cat") Categorie categorie, @PathVariable(name = "id") Long id) {
//		documentService.Update(file, document, user, categorie, id);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}
