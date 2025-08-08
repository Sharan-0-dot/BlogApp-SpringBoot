package com.App.Blog.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPost {
    @Id
    private ObjectId id;
    @DBRef
    private User postedUser;
    private String authorName;
    private String content;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date updatedAt;
    private int likes;
}
