package org.yacine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yacine.Entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
