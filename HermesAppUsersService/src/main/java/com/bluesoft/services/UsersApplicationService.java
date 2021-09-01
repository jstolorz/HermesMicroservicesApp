package com.bluesoft.services;

import com.bluesoft.domain.UserEntity;
import com.bluesoft.domain.UsersRepository;
import com.bluesoft.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class UsersApplicationService implements UsersService {

    private final UsersRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UsersApplicationService(final UsersRepository repository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(final UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        repository.save(userEntity);

        final UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }
}
