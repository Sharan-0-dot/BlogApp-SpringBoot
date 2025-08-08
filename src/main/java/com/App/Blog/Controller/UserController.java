package com.App.Blog.Controller;

import com.App.Blog.Model.User;
import com.App.Blog.Model.UserDTO;
import com.App.Blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.getUser(name);
        UserDTO response = UserDTO.builder()
                .username(user.getUsername())
                .imgURL(user.getImgURL())
                .roles(new ArrayList<>(user.getRoles()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(userService.getUserId(name), HttpStatus.OK);
    }


    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User updatedUser = userService.putUser(user, name);
        if(updatedUser != null) {
            UserDTO response = UserDTO.builder()
                    .username(updatedUser.getUsername())
                    .imgURL(updatedUser.getImgURL())
                    .roles(new ArrayList<>(updatedUser.getRoles()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not found or Check the credentials", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User deleted = userService.deleteUser(name);
        if(deleted != null) {
            UserDTO response = UserDTO.builder()
                    .username(deleted.getUsername())
                    .imgURL(deleted.getImgURL())
                    .roles(new ArrayList<>(deleted.getRoles()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/blogs")
    public ResponseEntity<?> getAllBlogsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(userService.getAllBlogsOfUser(name), HttpStatus.OK);
    }

}
