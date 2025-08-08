package com.App.Blog.Model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class User {
    @Id
    private ObjectId id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String imgURL;
    private List<String> roles;
}
