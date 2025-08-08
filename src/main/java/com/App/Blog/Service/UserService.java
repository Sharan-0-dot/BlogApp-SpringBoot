package com.App.Blog.Service;

import com.App.Blog.Model.BlogPost;
import com.App.Blog.Model.BlogResponseDTO;
import com.App.Blog.Model.User;
import com.App.Blog.Repository.BlogRepo;
import com.App.Blog.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWT_Service jwtService;

    public List<User> findAll() {
        return repo.findAll();
    }

    public User getUser(String name) {
        return repo.findByusername(name);
    }

    public String getUserId(String name) {
        return repo.findByusername(name).getId().toString();
    }

    public User postUser(User user) throws Exception {
        User present = repo.findByusername(user.getUsername());
        if(present != null) throw new Exception("username is taken");
        user.setRoles(new ArrayList<>(List.of("USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User putUser(User user, String name) {
        User u = repo.findByusername(name);
        if(u != null) {
            if(user.getUsername() != null && !user.getUsername().isEmpty()) {
                u.setUsername(user.getUsername());
            }
            if(user.getPassword() != null && !user.getPassword().isEmpty()) {
                u.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if(user.getImgURL() != null && !user.getImgURL().isEmpty()) {
                u.setImgURL(user.getImgURL());
            }
            return repo.save(u);
        }
        return null;
    }

    @Transactional
    public User deleteUser(String name) {
        User u = repo.findByusername(name);
        if(u != null) {
            blogRepo.deleteByPostedUser(u);
            repo.delete(u);
            return u;
        }
        return null;
    }

    public String verify(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if(authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "user not verified";
    }

    public List<BlogResponseDTO> getAllBlogsOfUser(String name) {
        User user = repo.findByusername(name);
        return blogService.getAllBlogsOfUser(user);
    }
}
