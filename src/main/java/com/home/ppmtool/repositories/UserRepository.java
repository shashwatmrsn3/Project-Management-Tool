package com.home.ppmtool.repositories;

import com.home.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);
    User getById(Long id);

}
