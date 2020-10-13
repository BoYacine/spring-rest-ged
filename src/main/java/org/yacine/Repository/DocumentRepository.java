package org.yacine.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import org.yacine.Entity.Document;
import org.yacine.Entity.User;

@RepositoryRestResource
public interface DocumentRepository extends JpaRepository<Document, Long> {
	public long count();
	
	//@Query("select d from Document d where d.nom =?1 and d.user = ?2")
	public long countByNomAndUserId(@RequestParam String nom, Long user );
	
	public List<Document> findByPathContainsAndFormat(String Path, String format);

	public Document findBynom(String nom);
	

}
