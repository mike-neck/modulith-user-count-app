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

import com.example.users.UserId;
import com.example.users.UserInfo;
import com.example.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    ResponseEntity<UserId> create(@RequestBody UserCreateRequest request) {
        UserId userId = userService.createUser(request.getName());
        logger.info("success: create-user, name: {}, new-user-id: {}", request.getName(), userId.userId);
        URI uri = UriComponentsBuilder.fromPath("/users/{userId}")
                .buildAndExpand(userId.userId)
                .toUri();
        return ResponseEntity.created(uri).body(userId);
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
