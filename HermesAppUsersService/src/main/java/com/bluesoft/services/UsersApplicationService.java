package com.bluesoft.services;

import com.bluesoft.domain.UserEntity;
import com.bluesoft.domain.UsersRepository;
import com.bluesoft.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public UserDto getUserDetailsByEmail(final String email) {
        final UserEntity userEmail = repository.findByEmail(email);
        if(userEmail == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEmail,UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity userEmail = repository.findByEmail(username);
        if(userEmail == null) throw new UsernameNotFoundException(username);
        return new User(userEmail.getEmail(),userEmail.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
    }
}
