package com.sonny.spotifyclone.usercontext.repository;

import com.sonny.spotifyclone.usercontext.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
