package com.fin.repository;

import com.fin.dto.UserLoginDto;
import com.fin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAuthDataRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);
    void deleteByUserName(String userName);
}