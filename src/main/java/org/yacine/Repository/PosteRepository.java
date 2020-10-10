package org.yacine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.yacine.Entity.Poste;

@RepositoryRestResource
public interface PosteRepository extends JpaRepository<Poste, Long>{

}
