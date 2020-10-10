package org.yacine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yacine.Entity.User;
import org.yacine.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	@Value("${directory.racine}")
	String racine;

	public String GetUserPath(Long id) {

		User user = repository.findById(id).get();
		String path = racine + user.getPoste().getDepartement().getNom() + "/" + user.getPoste().getNom() + "/"
				+ user.getNom()+"/";
		return path;
	}

}
