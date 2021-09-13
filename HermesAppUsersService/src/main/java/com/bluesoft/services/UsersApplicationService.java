package com.bluesoft.services;

import com.bluesoft.domain.AlbumsServiceClient;
import com.bluesoft.domain.UserEntity;
import com.bluesoft.domain.UsersRepository;
import com.bluesoft.shared.UserDto;
import com.bluesoft.ui.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
class UsersApplicationService implements UsersService {

    private final UsersRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RestTemplate restTemplate;
    private final AlbumsServiceClient albumsServiceClient;

    UsersApplicationService(final UsersRepository repository, final BCryptPasswordEncoder bCryptPasswordEncoder, final RestTemplate restTemplate, final AlbumsServiceClient albumsServiceClient) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.restTemplate = restTemplate;
        this.albumsServiceClient = albumsServiceClient;
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
    public UserDto getUserByUserId(final String userId) {
        UserEntity userEntity = repository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

//        String albumsUrl = String.format("http://ALBUMS-WS/users/%s/albums",userId);
//
//        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(
//                albumsUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//                });
//        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
//

        final List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);

        userDto.setAlbums(albumsList);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity userEmail = repository.findByEmail(username);
        if(userEmail == null) throw new UsernameNotFoundException(username);
        return new User(userEmail.getEmail(),userEmail.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
    }
}
