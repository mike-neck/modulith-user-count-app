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
package com.example.users;

import com.example.id.IdGenerator;
import com.example.users.repositories.UserRepository;
import com.example.users.service.UserService;
import com.example.users.service.UserServiceImpl;
import de.olivergierke.moduliths.test.ModuleTest;
import example.jpa.users.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ModuleTest
class UserReadServiceTest {

    @Autowired
    UserReadService userReadService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        Instant instant = OffsetDateTime.of(2019, 1, 2, 15, 4, 5, 6, ZoneOffset.UTC).toInstant();
        UserEntity entity1 = new UserEntity(1L, "test-user-1", instant);
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity1));
    }

    @Test
    void findUserById() {
        Optional<UserInfo> user = userReadService.getUser(new UserId(1L));
        assertThat(user)
                .isPresent()
                .hasValueSatisfying(u -> assertThat(u.id).isEqualTo(1L))
                .hasValueSatisfying(u -> assertThat(u.name).isEqualTo("test-user-1"));
    }

    @Configuration
    static class Config {

        private static final Logger logger = LoggerFactory.getLogger(Config.class);

        @Bean
        IdGenerator idGenerator() {
            return System::currentTimeMillis;
        }

        @Bean
        ApplicationEventPublisher applicationEventPublisher() {
            return event -> logger.info("event published, event: {}", event);
        }

        @Bean
        Clock clock() {
            return Clock.systemUTC();
        }

        @Bean
        UserReadService userReadService(
                IdGenerator idGenerator,
                ApplicationEventPublisher applicationEventPublisher,
                UserRepository userRepository,
                Clock clock) {
            return userServiceImpl(idGenerator, applicationEventPublisher, userRepository, clock);
        }

        @Bean
        UserService userServiceImpl(
                IdGenerator idGenerator,
                ApplicationEventPublisher applicationEventPublisher,
                UserRepository userRepository,
                Clock clock) {
            return new UserServiceImpl(idGenerator, userRepository, applicationEventPublisher, clock);
        }
    }
}
