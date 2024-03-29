package com.akul.taskslist.web.mappers;

import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
