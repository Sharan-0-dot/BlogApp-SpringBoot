package com.App.Blog.Repository;

import com.App.Blog.Model.BlogPost;
import com.App.Blog.Model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BlogRepo extends MongoRepository<BlogPost, ObjectId> {
    List<BlogPost> findByPostedUser(User user);
    void deleteByPostedUser(User user);
}
