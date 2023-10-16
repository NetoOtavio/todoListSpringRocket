package com.todolist.todolistAPI.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username);
}
