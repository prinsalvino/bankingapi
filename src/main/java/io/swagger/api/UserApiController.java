package io.swagger.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.User;
import io.swagger.model.UserDTO;
import io.swagger.service.AuthenticationService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.h2.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-02T11:15:57.209Z[GMT]")
@RestController
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> deleteuserbyid(@Parameter(in = ParameterIn.PATH, description = "ID of user to return", required = true, schema = @Schema()) @PathVariable("id") Long id) {
        if (authenticationService.isEmployee() == true) {
            if (userService.getById(id) == null){
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            userService.deleteUserByID(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<User> getuserbyid(@Parameter(in = ParameterIn.PATH, description = "ID of article to retun", required = true, schema = @Schema()) @PathVariable("id") Long id) {
        if (authenticationService.isEmployee() == true) {
            if (userService.getById(id) == null){
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<User>(userService.getById(id),HttpStatus.OK);
        }
        else if (authenticationService.isEmployee() == false){
            User user = authenticationService.getCurrentUser();
            if (user.getId() != id)
                return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
            return new ResponseEntity<User>(userService.getById(id),HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.FORBIDDEN);

    }

    public ResponseEntity<Void> updateUser(@Parameter(in = ParameterIn.PATH, description = "ID of article to retun", required = true, schema = @Schema()) @PathVariable("id") Long id, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody User body) {
        if (authenticationService.isEmployee() == true) {
            if (userService.getById(id) == null){
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            userService.updateUserById(id,body);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Void> updateCurrentUserPassword(@Valid @RequestBody UserDTO userDTO) {
        try {
            userService.updateCurrentUserPassword(userDTO);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
