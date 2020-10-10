package org.yacine.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Poste {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	
	@ManyToOne
	@JoinColumn
	private Departement departement;
	
	@OneToMany(mappedBy = "poste")
	private List<User> users;
	
}
