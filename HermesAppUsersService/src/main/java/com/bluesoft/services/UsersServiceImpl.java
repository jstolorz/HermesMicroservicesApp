package com.bluesoft.services;

import com.bluesoft.shared.UserDto;

import java.util.UUID;

class UsersServiceImpl implements UsersService {
    @Override
    public UserDto createUser(final UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        return null;
    }
}
