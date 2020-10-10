package org.yacine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.yacine.Entity.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long>{
	
}
