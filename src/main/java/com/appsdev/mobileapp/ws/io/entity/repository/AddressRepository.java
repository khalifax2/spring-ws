package com.appsdev.mobileapp.ws.io.entity.repository;

import com.appsdev.mobileapp.ws.io.entity.AddressEntity;
import com.appsdev.mobileapp.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
    AddressEntity findByAddressId(String addressId);
}
