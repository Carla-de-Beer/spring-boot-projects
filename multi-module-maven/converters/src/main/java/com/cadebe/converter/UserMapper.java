package com.cadebe.converter;

import com.cadebe.domain.UserCommand;
import com.cadebe.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserCommand userToUserCommand(User source);

    User userCommandToUser(UserCommand source);
}
