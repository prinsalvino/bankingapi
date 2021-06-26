package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(Long value);

    @Query(value = "SELECT * FROM user limit :limit", nativeQuery = true)
    List<User> getUsersWithLimit(int limit);

    User findByUsername(String value);

    User findByLastName(String value);

    @Query(value = "SELECT * FROM user u WHERE u.lastName = :lastName limit :limit", nativeQuery = true)
    User findByLastNameWithLimit(String lastName, int limit);


}
