package com.bluesoft.services;

import com.bluesoft.domain.UserEntity;
import com.bluesoft.domain.UsersRepository;
import com.bluesoft.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class UsersApplicationService implements UsersService {

    private final UsersRepository repository;

    UsersApplicationService(final UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto createUser(final UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword("test");
        repository.save(userEntity);
        return null;
    }
}
