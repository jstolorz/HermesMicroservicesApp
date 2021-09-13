package com.bluesoft.domain;

public interface UsersRepository {

    void save(UserEntity userEntity);
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);
}
