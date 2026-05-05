package com.lamaison.auth.infrastructure.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lamaison.auth.application.service.UserService;
import com.lamaison.auth.domain.model.Role;
import com.lamaison.auth.dto.response.UserResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Flux<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Mono<UserResponse> getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/users/{id}/role")
    public Mono<UserResponse> updateRole(@PathVariable String id,
                                          @RequestParam Role rol) {
        return userService.updateRole(id, rol);
    }

    @DeleteMapping("/users/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
