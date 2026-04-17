package com.lamaison.auth.controller;

import com.lamaison.auth.dto.response.UserResponse;
import com.lamaison.auth.model.Role;
import com.lamaison.auth.service.UserService;
import org.springframework.web.bind.annotation.*;
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