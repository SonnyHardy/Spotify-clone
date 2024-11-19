package com.sonny.spotifyclone.usercontext.mapper;

import com.sonny.spotifyclone.usercontext.ReadUserDTO;
import com.sonny.spotifyclone.usercontext.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);
}
