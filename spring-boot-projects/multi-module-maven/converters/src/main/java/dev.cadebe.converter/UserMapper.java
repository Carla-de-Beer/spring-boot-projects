package dev.cadebe.converter;

import dev.cadebe.domain.UserCommand;
import dev.cadebe.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserCommand userToUserCommand(User source);

    User userCommandToUser(UserCommand source);
}
