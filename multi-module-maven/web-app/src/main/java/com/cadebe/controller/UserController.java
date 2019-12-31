package com.cadebe.controller;

import com.cadebe.converter.UserMapper;
import com.cadebe.domain.UserCommand;
import com.cadebe.entities.User;

public class UserController {

    User saveUser(UserCommand command) {
        return UserMapper.INSTANCE.userCommandToUser(command);
    }
}
