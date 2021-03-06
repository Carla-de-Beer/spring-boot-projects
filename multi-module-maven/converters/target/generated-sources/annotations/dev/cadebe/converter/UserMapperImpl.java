package dev.cadebe.converter;

import dev.cadebe.domain.UserCommand;
import dev.cadebe.entities.User;
import dev.cadebe.entities.User.UserBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-26T00:01:59+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserCommand userToUserCommand(User source) {
        if ( source == null ) {
            return null;
        }

        UserCommand userCommand = new UserCommand();

        userCommand.setFirstName( source.getFirstName() );
        userCommand.setLastName( source.getLastName() );
        userCommand.setEmail( source.getEmail() );

        return userCommand;
    }

    @Override
    public User userCommandToUser(UserCommand source) {
        if ( source == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstName( source.getFirstName() );
        user.lastName( source.getLastName() );
        user.email( source.getEmail() );

        return user.build();
    }
}
