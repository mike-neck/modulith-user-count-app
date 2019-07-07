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
package com.example.users.service;

import com.example.IdGenerator;
import com.example.users.UserCreationEvent;
import com.example.users.UserId;
import com.example.users.UserInfo;
import com.example.users.api.UserCreateRequest;
import com.example.users.entities.UserEntity;
import com.example.users.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IdGenerator idGenerator;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Clock clock;

    public UserServiceImpl(
            IdGenerator idGenerator,
            UserRepository userRepository,
            ApplicationEventPublisher applicationEventPublisher,
            Clock clock) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.clock = clock;
    }

    @Override
    public UserId createUser(UserCreateRequest request) {
        UserId userId = createUser(request.getName());
        applicationEventPublisher.publishEvent(new UserCreationEvent(userId, request));
        return userId;
    }

    @Override
    public UserId createUser(String name) {
        UserEntity userEntity = new UserEntity(idGenerator.generateId(), name, Instant.now(clock));
        UserEntity saved = userRepository.save(userEntity);
        return new UserId(saved.getId());
    }

    @Override
    public Optional<UserInfo> getUser(long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.map(user -> new UserInfo(userId, user.getName()));
    }
}
