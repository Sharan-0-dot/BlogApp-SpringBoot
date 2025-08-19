package com.App.Blog.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDTO {
    private String id;
    private String authorName;
    private String content;
    private int likes;
    private Date created;
    private Date updated;
}
