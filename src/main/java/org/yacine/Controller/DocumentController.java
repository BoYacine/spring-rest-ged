package org.yacine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yacine.Entity.Document;
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
	public String Upload(@RequestParam("file") MultipartFile[] file, @ModelAttribute Document document,
			@ModelAttribute User user) {
		return documentService.Upload(file, document, user);
	}
}
