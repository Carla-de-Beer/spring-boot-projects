package dev.cadebe.controller;

import dev.cadebe.converter.UserMapper;
import dev.cadebe.domain.UserCommand;
import dev.cadebe.entities.User;

public class UserController {

    User saveUser(UserCommand command) {
        return UserMapper.INSTANCE.userCommandToUser(command);
    }
}
