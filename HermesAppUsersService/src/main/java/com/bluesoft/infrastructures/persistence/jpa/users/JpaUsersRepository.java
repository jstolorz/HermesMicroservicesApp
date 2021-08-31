package com.bluesoft.infrastructures.persistence.jpa.users;

import com.bluesoft.domain.UsersRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaUsersRepository implements UsersRepository {
    private final SpringJpaUsersRepository springJpaUsersRepository;

    JpaUsersRepository(final SpringJpaUsersRepository springJpaUsersRepository) {
        this.springJpaUsersRepository = springJpaUsersRepository;
    }
}
