package com.App.Blog.Controller;


import com.App.Blog.Model.User;
import com.App.Blog.Service.BlogService;
import com.App.Blog.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@CrossOrigin
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("Running successfully", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.verify(user), HttpStatus.OK);
        } catch (Exception e) {
            PublicController.log.error(e.getLocalizedMessage());
        }
        return new ResponseEntity<>("User not verified", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.postUser(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return new ResponseEntity<>("username already exists try using other username", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/blogs")
    public ResponseEntity<?> getAllBlog() {
        return new ResponseEntity<>(blogService.getAllBlog(), HttpStatus.OK);
    }
}
