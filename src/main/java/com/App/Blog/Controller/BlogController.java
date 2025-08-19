package com.App.Blog.Controller;

import com.App.Blog.Model.BlogPost;
import com.App.Blog.Model.BlogPostDTO;
import com.App.Blog.Service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/")
    public ResponseEntity<?> postBlog(@RequestBody BlogPostDTO post) {
        try {
            return new ResponseEntity<>(blogService.postBlog(post), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateBlog(@RequestBody BlogPost post) {
        try {
            return new ResponseEntity<>(blogService.updateBlog(post), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable String id) {
        try {
            return new ResponseEntity<>(blogService.deleteBlog(new ObjectId(id)), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
