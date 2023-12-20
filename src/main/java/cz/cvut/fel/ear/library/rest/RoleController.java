package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.exceptions.NotFoundException;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.service.RoleService;
import cz.cvut.fel.ear.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/roles")
public class RoleController {

    private final RoleService service;

    private final UserService userService;

    @Autowired
    public RoleController(RoleService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    // get all users with the role
    @GetMapping(value = "/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersWithRole(@PathVariable String roleName) {
        final List<User> users = service.findAllUsersByRoleName(roleName);
//        if (users == null) {
//            throw new NotFoundException("No users with this role found");
//        }
        return users;
    }

    // add role to user
    @PostMapping(value = "/{userId}/{roleName}")
    public ResponseEntity<Void> addRoleToUser(@PathVariable Integer userId, @PathVariable String roleName) {
        final User user = userService.find(userId);
        if (user == null) {
            throw new NotFoundException("User with this id not found ");
        }
        service.addRoleToUser(user,roleName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // remove role from user
    @DeleteMapping(value = "/{userId}/{roleName}")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Integer userId, @PathVariable String roleName) {
        final User user = userService.find(userId);
        if (user == null) {
            throw new NotFoundException("User with this id not found ");
        }
        boolean success = service.removeRoleFromUser(user,roleName);
        if(!success){
            throw new NotFoundException("User doesn't have this role, this role cannot be removed");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // remove role from all users
    @DeleteMapping(value = "/{roleName}")
    public ResponseEntity<Void> removeRoleFromAllUsers(@PathVariable String roleName) {
        service.removeRole(roleName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
