package org.yacine;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yacine.Entity.Departement;
import org.yacine.Entity.Poste;
import org.yacine.Entity.Categorie;
import org.yacine.Entity.User;
import org.yacine.Repository.CategorieRepository;
import org.yacine.Repository.DepartementRepository;
import org.yacine.Repository.PosteRepository;
import org.yacine.Repository.UserRepository;

@SpringBootApplication
public class SpringRestGedApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestGedApplication.class, args);

	}

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PosteRepository posteRepo;
	@Autowired
	private DepartementRepository departementRepo;
	@Autowired
	private CategorieRepository typeRepository;
	@Value("${directory.racine}")
	String racine;

	@Override
	public void run(String... args) throws Exception {

		// departement initialisation
		
		Departement departement1 = new Departement(null, "dep1", null);
		departementRepo.save(departement1);
		Departement departement2 = new Departement(null, "dep2", null);
		departementRepo.save(departement2);
		Departement departement3 = new Departement(null, "dep3", null);
		departementRepo.save(departement3);

		// poste initialisation
		
		Poste poste1 = new Poste(null, "post1", departement1, null);
		posteRepo.save(poste1);
		Poste poste2 = new Poste(null, "post2", departement2, null);
		posteRepo.save(poste2);
		Poste poste3 = new Poste(null, "post3", departement3, null);
		posteRepo.save(poste3);

		// user initialisation
		
		User user1 = new User(null, "user1", "123", "yacine", "bouregba", poste1,null);
		userRepo.save(user1);
		User user11 = new User(null, "user11", "123", "imed", "boucenda", poste2, null);
		userRepo.save(user11);
		User user2 = new User(null, "user2", "123", "zinou", "bouregba", poste2, null);
		userRepo.save(user2);
		User user3 = new User(null, "user3", "123", "abdou", "bouregba", poste3, null);
		userRepo.save(user3);

		// type document initialisation
		
		Categorie type1 =new Categorie(null,"facture", null);
		typeRepository.save(type1);
		Categorie type2 =new Categorie(null,"raport", null);
		typeRepository.save(type2);
		
		
		// get lists departements, postes, users
		
		List<Departement> departements = departementRepo.findAll();
		List<Poste> postes = posteRepo.findAll();
		List<User> users = userRepo.findAll();

		
		// creation directory
		departements.forEach(d -> {
			postes.forEach(p -> {
				users.forEach(u -> {
					String directory = racine + u.getPoste().getDepartement().getNom() + "/" + u.getPoste().getNom()
							+ "/" + u.getNom();
					File file = new File(directory);
					if (file.mkdirs()) {
						System.out.println("created");
					}
				});

			});

		});

	}

}
