package com.appsdev.mobileapp.ws.io.entity.repository;

import com.appsdev.mobileapp.ws.io.entity.AuthorityEntity;
import com.appsdev.mobileapp.ws.io.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
    AuthorityEntity findByName(String name);
}
