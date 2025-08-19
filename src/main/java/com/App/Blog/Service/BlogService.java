package com.App.Blog.Service;

import com.App.Blog.Model.BlogPost;
import com.App.Blog.Model.BlogPostDTO;
import com.App.Blog.Model.BlogResponseDTO;
import com.App.Blog.Model.User;
import com.App.Blog.Repository.BlogRepo;
import com.App.Blog.Repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepo repo;

    @Autowired
    private UserRepo userRepo;


    public List<BlogResponseDTO> getAllBlog() {
        List<BlogResponseDTO> list = new ArrayList<>();
        for(BlogPost posted : repo.findAll()) {
            list.add(BlogResponseDTO.builder()
                    .id(posted.getId().toString())
                    .authorName(posted.getAuthorName())
                    .content(posted.getContent())
                    .created(posted.getCreated())
                    .updated(posted.getUpdatedAt())
                    .likes(posted.getLikes())
                    .build());
        }
        return list;
    }

    public BlogResponseDTO postBlog(BlogPostDTO post) throws Exception {
        User user = userRepo.findById(post.getUserId()).orElse(null);
        if(user == null) {
            throw new Exception("User Not found");
        }
        BlogPost newPost = BlogPost.builder()
                .postedUser(user)
                .authorName(user.getUsername())
                .content(post.getContent())
                .likes(0)
                .build();
        BlogPost posted = repo.save(newPost);
        return BlogResponseDTO.builder()
                .id(posted.getId().toString())
                .authorName(posted.getAuthorName())
                .content(posted.getContent())
                .created(posted.getCreated())
                .updated(posted.getUpdatedAt())
                .likes(posted.getLikes())
                .build();
    }

    public BlogResponseDTO updateBlog(BlogPost post) throws Exception {
        BlogPost curPost = repo.findById(post.getId()).orElse(null);
        if(curPost == null) {
            throw new Exception("Post Not found");
        }
        curPost.setContent(post.getContent());
        BlogPost posted = repo.save(curPost);
        return BlogResponseDTO.builder()
                .id(posted.getId().toString())
                .authorName(posted.getAuthorName())
                .content(posted.getContent())
                .created(posted.getCreated())
                .updated(posted.getUpdatedAt())
                .likes(posted.getLikes())
                .build();
    }

    public BlogResponseDTO deleteBlog(ObjectId id) throws Exception {
        BlogPost posted = repo.findById(id).orElse(null);
        if(posted == null) {
            throw new Exception("Post Not found");
        }
        repo.deleteById(posted.getId());
        return BlogResponseDTO.builder()
                .id(posted.getId().toString())
                .authorName(posted.getAuthorName())
                .content(posted.getContent())
                .created(posted.getCreated())
                .updated(posted.getUpdatedAt())
                .likes(posted.getLikes())
                .build();
    }

    public List<BlogResponseDTO> getAllBlogsOfUser(User user) {
        List<BlogResponseDTO> list = new ArrayList<>();
        for(BlogPost posted : repo.findByPostedUser(user)) {
            list.add(BlogResponseDTO.builder()
                            .id(posted.getId().toString())
                            .authorName(posted.getAuthorName())
                            .content(posted.getContent())
                            .created(posted.getCreated())
                            .updated(posted.getUpdatedAt())
                            .likes(posted.getLikes())
                            .build());
        }
        return list;
    }
}
