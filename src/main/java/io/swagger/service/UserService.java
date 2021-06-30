package io.swagger.service;

import io.swagger.model.User;
import io.swagger.model.UserDTO;
import io.swagger.repository.UserRepository;
import io.swagger.util.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
@Setter

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService authenticationService;


    List<User> users;

    public UserService() {

    }

    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String getLogin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtUtil.generateToken(username, userRepository.findByUsername(username).getType());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid username/password");
        }
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getByLastName(String lastname) {
        return userRepository.findByLastName(lastname);
    }

    public void deleteUserByID(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findByFirstNameAndLastName(String firstname, String lastname) {
        return userRepository.findByFirstNameAndLastName(firstname, lastname);
    }

    public List<User> findByFirstName(String firstname) {
        return userRepository.findByFirstName(firstname);
    }

    public void updateUserById(Long id, User newUserData) {
        User target = userRepository.getUserById(id);
        if (newUserData.getType() != null) {
            target.setType(newUserData.getType());
        }
        if (newUserData.getActive() != null) {
            target.setActive(newUserData.getActive());
        }
        if (newUserData.getBirthdate() != null) {
            target.setBirthdate(newUserData.getBirthdate());
        }
        if (newUserData.getAddress() != null) {
            target.setAddress(newUserData.getAddress());
        }
        if (newUserData.getUsername() != null) {
            target.setUsername(newUserData.getUsername());
        }
        if (newUserData.getLastName() != null) {
            target.setLastName(newUserData.getLastName());
        }
        if (newUserData.getEmail() != null) {
            target.setEmail(newUserData.getEmail());
        }
        if (newUserData.getPostalcode() != null) {
            target.setPostalcode(newUserData.getPostalcode());
        }
        if (newUserData.getCity() != null) {
            target.setCity(newUserData.getCity());
        }
        if (newUserData.getPhoneNumber() != null) {
            target.setPhoneNumber(newUserData.getPhoneNumber());
        }
        if (newUserData.getFirstName() != null) {
            target.setFirstName(newUserData.getFirstName());
        }
        if (newUserData.getPassword() != null) {
            target.setPassword(passwordEncoder.encode(newUserData.getPassword()));
        }
        userRepository.save(target);
    }

    public User getByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username).password(user.getPassword()).authorities(user.getType())
                .accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build();
    }

    public void updateCurrentUserPassword(UserDTO newInfoUser) {
        User user = authenticationService.getCurrentUser();
        user.setPassword(passwordEncoder.encode(newInfoUser.getPassword()));
        userRepository.save(user);
    }
}
