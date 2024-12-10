package com.challange.orderjavachallange.unit.user.mapper;

import com.challange.orderjavachallange.user.dto.UserDto;
import com.challange.orderjavachallange.user.entity.User;
import com.challange.orderjavachallange.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testToDto_WithValidUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        UserDto userDto = UserMapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void testToDto_WithNullUser() {
        UserDto userDto = UserMapper.toDto(null);

        assertNull(userDto);
    }

    @Test
    void testToEntity_WithValidUserDto() {
        UserDto userDto = new UserDto(1L, "Jane Doe", "jane.doe@example.com");

        User user = UserMapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    void testToEntity_WithNullUserDto() {
        User user = UserMapper.toEntity(null);

        assertNull(user);
    }
}
