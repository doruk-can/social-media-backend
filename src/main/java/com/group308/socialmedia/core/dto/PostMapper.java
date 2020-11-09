package com.group308.socialmedia.core.dto;

import com.group308.socialmedia.core.model.domain.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    Post asEntity(PostDto source);

    PostDto asDto(Post source);

    List<PostDto> asDtos(List<Post> sources);
}