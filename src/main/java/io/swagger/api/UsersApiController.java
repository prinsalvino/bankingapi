package io.swagger.api;

import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AuthenticationService;
import io.swagger.service.UserService;
import io.swagger.util.UserValidation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-02T11:15:57.209Z[GMT]")
@RestController
@Controller
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    UserValidation userValidation = new UserValidation();

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody User body) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (authenticationService.isEmployee() == true) {
            if(userService.getByUserName(body.getUsername()) != null){
                jsonObject.put("message", "User already exists");
                return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.BAD_REQUEST);
            }
            if(body.getType() == null){
                body.setType(User.TypeEnum.CUSTOMER);
            }
            if(userValidation.checkValidEmail(body.getEmail()) == false){
                jsonObject.put("message", "Bad email input");
                return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.BAD_REQUEST);
            }
            if(userValidation.checkValidNumber(body.getPhoneNumber()) == false){
                jsonObject.put("message", "Bad phone number input and must be 10 digit");
                return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.BAD_REQUEST);
            }
            if(userValidation.checkValidBirthDate(body.getBirthdate()) == true){
                jsonObject.put("message", "Bad birth date input, must be (yyyy-MM-dd) and before today");

                return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.BAD_REQUEST);
            }
            userService.createUser(body);
            jsonObject.put("message", "User is created");

            return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }
//    @PreAuthorize("hasAuthority('')")
    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "Get users based on Last Name" ,
                                                       schema=@Schema()) @Valid @RequestParam(value = "lastname", required = false) String lastname,
                                               @Parameter(in = ParameterIn.QUERY, description = "Maximum numbers of items to return" ,
                                                       schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit)
    {
        if (authenticationService.isEmployee() == true){
            if(limit != null && lastname == null){
                return new ResponseEntity<List<User>>(userService.getAllUserWithLimit(limit), HttpStatus.OK);
            }
            else if(limit == null && lastname != null){
                return new ResponseEntity<List<User>>((List<User>) userService.getByLastName(lastname), HttpStatus.OK);
            }
            else if(limit != null && lastname != null){
                List<User> users = userService.getByLastName(lastname);
                if (users.size() < limit){
                    limit = users.size();
                }
                return new ResponseEntity<List<User>>(users.subList(0,limit), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<List<User>>(userService.getAllUser(), HttpStatus.OK);
            }

        }
        return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
    }

}