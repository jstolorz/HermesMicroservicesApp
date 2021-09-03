package com.bluesoft.infrastructures.persistence.jpa.users;

import com.bluesoft.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpringJpaUsersRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
