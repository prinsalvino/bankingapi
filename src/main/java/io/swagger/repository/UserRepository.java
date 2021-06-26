package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(Long value);

    List<User> findByFirstNameAndLastName(String firstname, String lastname);

    List<User> findByFirstName(String firstname);

    User findByUsername(String value);

    List<User> findByLastName(String value);
}
