package com.easywallet.controller;

import com.easywallet.Dto.LoginRequestDto;
import com.easywallet.Dto.UserDto;
import com.easywallet.constants.ApiResponse;
import com.easywallet.constants.CustomMessages;
import com.easywallet.exceptions.RecordAlreadyPresentException;
import com.easywallet.model.*;
import com.easywallet.service.OauthService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
//import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    OauthService oauthService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ServletContext context;


    @Autowired
    private AuthenticationManager authenticationManager;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    ModelMapper modelMapper = new ModelMapper();

    @ApiOperation(value = "Authenticate user role")
    @PostMapping("/authenticateAndGetUserRoles")
    public ResponseEntity authenticateUser(@RequestBody LoginRequestDto loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        User principal = (User) authentication.getPrincipal();

        UserDto userDTO = modelMapper.map(principal, UserDto.class);

        //get userID from database

        Optional<User> userIdToGet = oauthService.getUserByUsername(principal.getUsername());


        userDTO.setId(userIdToGet.get().getId());
        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, userDTO));
    }


    @ApiOperation(value = "Get All Active Users")
    @GetMapping("/user")
    public Object getAllUser() {
        List<User> userInfos = oauthService.findAll();
        if (userInfos == null || userInfos.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return userInfos;
    }

    @ApiOperation(value = "Create new User")
    @PostMapping("/createAccount")
    public ResponseEntity addUser(@RequestBody User userRecord) {

        Optional<User> findUserByUsername = oauthService.getUserByUsername(userRecord.getUsername());
        try {
            if (!findUserByUsername.isPresent()) {
                log.info("Sending Person {} from Client with Values " + "First Name: " + userRecord.getFirstName() + " LastName: " + userRecord.getLastName() + " Password: " + userRecord.getPassword() + "Email: " + userRecord.getEmail());

                if (userRecord == null) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Null Object was submitted"));
                }


                String encodedPassword = passwordEncoder.encode(userRecord.getPassword());

                userRecord.setPassword(encodedPassword);
                userRecord.setAccountNonExpired(true);
                userRecord.setEnabled(true);
                userRecord.setDateCreated(new Date());
                userRecord.setCredentialsNonExpired(true);
                userRecord.setAccountNonLocked(true);

                String userPhone = userRecord.getPhone();
                Random r = new Random();
                String randomNumber = String.format("%03d", (Object) Integer.valueOf(r.nextInt(222)));
                String generatedPin = randomNumber;

                String additionalNo = userPhone;
                int phraseLength = additionalNo.length();
                String twoNumbers = additionalNo.substring(phraseLength / 2 - 1, phraseLength / 2 + 1);
                String pin = generatedPin + twoNumbers;

                WalletAccount walletAccount = new WalletAccount();
                walletAccount.setAccountBalance(0.0);
                walletAccount.setLastDeposit(0.0);

                String userPhoneNumber = userRecord.getPhone();
                String lastFourDIGITS = "";

                if (userPhoneNumber.length() > 4) {
                    lastFourDIGITS = userPhoneNumber.substring(userPhoneNumber.length() - 4);
                } else {
                    lastFourDIGITS = userPhoneNumber;
                }
                if (lastFourDIGITS != null) {
                    ThreadLocalRandom random =
                            ThreadLocalRandom.current();
                    Long number = random.nextLong(10_000_0L, 100_000_0L);
                    String generatedNumber = number.toString();
                    String newAccountNumber = generatedNumber + lastFourDIGITS;
                    walletAccount.setAccountNumber(newAccountNumber);
                }
                String alphabet = "1A2B3C4D5E6F7G9H9I0J1K2L3M4N5O6P7Q8R9S0T1U2V3W4X5Y6Z7";
                // create random string builder
                StringBuilder sb = new StringBuilder();
                // create an object of Random class
                Random random = new Random();
                // specify length of random string
                int length = 5;
                for (int i = 0; i < length; i++) {
                    // generate random index number
                    int index = random.nextInt(alphabet.length());
                    // get character specified by index
                    // from the string
                    char randomChar = alphabet.charAt(index);
                    // append the character to string builder
                    sb.append(randomChar);
                }

                String randomString = sb.toString();

                userRecord.setReferralCode(randomString);
                userRecord.setTransactionPin(pin);
                userRecord.setWalletAccountId(walletAccount);
                OauthClientDetails clientDetails = new OauthClientDetails();
                clientDetails.setAccessTokenValidity(3600);
                clientDetails.setAutoapprove("");
                clientDetails.setAdditionalInformation("{}");
                clientDetails.setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit");
                clientDetails.setClientId(userRecord.getUsername());
                clientDetails.setClientSecret(encodedPassword);
                clientDetails.setRefreshTokenValidity(10000);
                clientDetails.setResourceIds("easyWallet-rest-api");
                clientDetails.setScope("READ,WRITE");
                clientDetails.setWebServerRedirectUri("http://localhost:9595/");


                System.out.println(userRecord);

                oauthService.saveUser(userRecord);

                oauthService.saveClientDetails(clientDetails);

                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, userRecord));
            } else
                throw new RecordAlreadyPresentException("User with username: " + userRecord.getUsername() + " already exists!!");
        } catch (RecordAlreadyPresentException e) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Failed, "User with username: " + userRecord.getUsername() + " already exists!!"));
        }

    }

    @ApiOperation(value = "Edit User Details")
    @PutMapping("/editUser")
    public ResponseEntity editUser(@RequestBody User userRecord) {
        User user = oauthService.saveUser(userRecord);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, user));
    }

    @ApiOperation("To add role to user")
    @PostMapping(value = "/add/userRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addSupplierAccount(@RequestBody User user) {

        Set<Role> roleList = user.getRoles().stream().distinct().collect(Collectors.toSet());
        user.setRoles(roleList);
        User savedSupplierAccount = oauthService.saveUser(user);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, savedSupplierAccount));
    }

    @ApiOperation("Save Roles")
    @PostMapping(value = "/add/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRoles(@RequestBody Role role) {

        //Check if Rolename Exists before saving a new role
        Optional<Role> checkRole = oauthService.findRoleByName(role.getName());

        if (checkRole.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Failed, CustomMessages.Exists));
        }

        Role roles = oauthService.saveRole(role);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, roles));
    }


    @ApiOperation("Save Roles")
    @PutMapping("/edit/role")
    public ResponseEntity editRoles(@RequestBody Role role) {
        Optional<Role> r = oauthService.findRoleById(role.getId());
        if (r.isPresent()) {
            r.get().setName(role.getName());
            Role roles = oauthService.saveRole(r.get());
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, roles));
        }
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.NotFound, "Role to edit not found"));
    }


    @ApiOperation(value = "Delete Role by ID")
    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<?> deleteRoleBy(@PathVariable("id") long id) {
        return oauthService.findRoleById(id)
                .map(record -> {
                    oauthService.deleteUserRole(id);
                    return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, CustomMessages.Deleted));
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(CustomMessages.NotFoundMessage, CustomMessages.NotFound)));
    }

    @ApiOperation("Get All Roles")
    @GetMapping(value = "/list/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getListOfRoles() {
        List<Role> roleList = oauthService.findAllRoles();
        return roleList;
    }


    @ApiOperation(value = "Get User by ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> User = oauthService.getUserById(id);
        if (User == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(User.get(), HttpStatus.OK);
    }


    @ApiOperation("To delete a User details by ID")
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") Long id) {
        oauthService.deleteUserById(id);
//        userService.deleteUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.Deleted));
    }

    @ApiOperation("To return all Users")
    @GetMapping("/list/users")
    public List<User> getAllRoles() {
        return oauthService.findAll();
    }

    @ApiOperation("To delete a Role assigned to a user")
    @DeleteMapping("/deleteAssignedRole/{userId}/{roleId}")
    public ResponseEntity deleteAssignedRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId) {

        Optional<RoleUser> ru = oauthService.findRoleUserById(userId, roleId);

        if (ru.isPresent()) {
            oauthService.deleteRoleUser(ru.get());
            return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, CustomMessages.Deleted));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.NotFoundMessage, CustomMessages.NotFound));
    }

    @ApiOperation("To add a role to a user")
    @PostMapping("/assignRoleToUser")
    public ResponseEntity addRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        Optional<User> searchedUser = oauthService.findUserById(userId);
        Optional<Role> searchedRole = oauthService.findRoleById(roleId);

        RoleUser ru = new RoleUser();
        ru.setUserId(searchedUser.get());
        ru.setRoleId(searchedRole.get());

        RoleUser savedRu = oauthService.saveRoleUser(ru);

        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, savedRu));

    }

}
