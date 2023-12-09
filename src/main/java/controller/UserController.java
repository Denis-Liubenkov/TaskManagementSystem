package controller;

import domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Creating user", description = "Create user, need to pass the input parameter object User in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User is created"),
            @ApiResponse(responseCode = "409", description = "User is not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        userService.createUser(user);
        log.info("User with firstname " + user.getFirstName() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updating user", description = "Update user, need to pass the input parameter object User in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User is updated"),
            @ApiResponse(responseCode = "409", description = "User is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        log.info("User with id: " + user.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting orderedMenu", description = "Delete client,  need to pass the input parameter client`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client is deleted"),
            @ApiResponse(responseCode = "409", description = "Client is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        log.info("Client with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
