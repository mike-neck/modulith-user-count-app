/*
 * Copyright 2019 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.users.api;

import com.example.Validation;
import com.example.users.UserInfo;
import com.example.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Map<String, Object>> create(@RequestBody UserCreateRequest request) {
        Validation<String> validator = request.validator();
        return validator
                .validate()
                .map(n -> userService.createUser(request))
                .doOnRight(userId -> logger.info("success: create-user, name: {}, email: {}, new-user-id: {}", request.getName(), request.getEmail(), userId.userId))
                .doOnLeft(message -> logger.info("failure: create-user, name: {}, email: {}, message: {}", request.getName(), request.getEmail(), message))
                .map(userId -> UriComponentsBuilder.fromPath("/users/{userId}")
                        .buildAndExpand(userId.userId)
                        .toUri())
                .map(uri -> ResponseEntity.created(uri).<Map<String, Object>>build())
                .rescue(message -> ResponseEntity.badRequest().body(Map.of("success", false, "message", message)));
    }

    @GetMapping(value = "{userId}", produces = "application/json")
    ResponseEntity<UserInfo> getUser(@PathVariable("userId") Long userId) {
        Optional<UserInfo> user = userService.getUser(userId);
        logger.info("get-user, user-id: {}, found: {}", userId, user.isPresent());
        return user
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
