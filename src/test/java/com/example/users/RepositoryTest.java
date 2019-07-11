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
import example.jpa.users.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(RepositoryTest.Config.class)
class RepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void save() {
        UserEntity entity = userRepository.save(new UserEntity(1L, "test-name-1", Instant.now()));
        assertThat(entity.getName()).isEqualTo("test-name-1");
    }

    @DataJpaTest
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackageClasses = {UserRepository.class})
    @EntityScan(basePackageClasses = UserEntity.class)
    static class Config {

        @Bean
        IdGenerator idGenerator() {
            return System::currentTimeMillis;
        }

        @Bean
        ApplicationEventPublisher applicationEventPublisher() {
            return event -> {};
        }

        @Bean
        Clock clock() {
            return Clock.systemUTC();
        }
    }
}
