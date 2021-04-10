package com.appsdev.mobileapp.ws.io.entity.repository;

import com.appsdev.mobileapp.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findUserByEmailVerificationToken(String token);

    @Query( value = "SELECT * FROM USERS u WHERE u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "SELECT count(*) FROM USERS u WHERE u.EMAIL_VERIFICATION_STATUS = 'true'",
            nativeQuery = true )
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);

    @Query( value = "SELECT * FROM USERS u WHERE u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query( value = "SELECT * FROM USERS u WHERE u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    @Query( value = "SELECT * FROM USERS u WHERE first_name LIKE %:keyword% OR last_name LIKE  %:keyword%", nativeQuery = true)
    List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);

    @Query( value = "SELECT u.first_name, u.last_name FROM USERS u WHERE first_name LIKE %:keyword% OR last_name LIKE  %:keyword%", nativeQuery = true)
    List<Object[]> findUsersByFirstNameAndLastNameKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query( value = "UPDATE USERS u SET u.email_verification_status = :emailVerificationStatus WHERE u.user_id = :userId",
            nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
                                           @Param("userId") String userId);

    // USING JPQL
    @Query("SELECT user FROM UserEntity user WHERE user.userId = :userId")
    UserEntity findUserEntityByUserId(@Param("userId") String userId);

    @Query("SELECT user.firstName, user.lastName FROM UserEntity user WHERE user.userId = :userId")
    List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);

    
//    @Modifying(clearAutomatically=true, flushAutomatically=true
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.emailVerificationStatus = :emailVerificationStatus WHERE u.userId = :userId")
    void updateUserEntityEmailVerificationStatus(
            @Param("emailVerificationStatus") boolean emailVerificationStatus,
            @Param("userId") String userId);
}
