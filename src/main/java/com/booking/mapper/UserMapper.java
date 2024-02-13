package com.booking.mapper;

import com.booking.domain.dtos.users.UserCreationDto;
import com.booking.domain.dtos.users.UserResultDto;
import com.booking.domain.dtos.users.UserUpdateDto;
import com.booking.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userCreationToUser(UserCreationDto userCreationDto);
    UserResultDto userToUserResult(User user);
    UserResultDto userCreationToUserResult(UserCreationDto userCreationDto);
    List<UserResultDto> usersToUserResultDtos(List<User> users);
    User userUpdateDtoToUser(UserUpdateDto userUpdateDto);
}
