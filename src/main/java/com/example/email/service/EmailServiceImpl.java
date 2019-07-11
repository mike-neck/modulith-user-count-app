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
package com.example.email.service;

import com.example.id.IdGenerator;
import com.example.email.Email;
import com.example.email.infrastructure.EmailStore;
import com.example.users.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private final IdGenerator idGenerator;
    private final EmailStore emailStore;

    public EmailServiceImpl(IdGenerator idGenerator, EmailStore emailStore) {
        this.idGenerator = idGenerator;
        this.emailStore = emailStore;
    }

    @Override
    public Optional<UserId> findUserIdByEmailAddress(Email email) {
        return emailStore.findUserIdByEmailAddress(email).map(UserId::new);
    }

    @Override
    public Optional<Long> allocateEmailToUser(String emailAddress, UserId userId) {
        Email email = new Email(emailAddress);
        if (emailStore.allocated(email)) {
            return Optional.empty();
        }
        Optional<Long> emailId = emailStore.findUserIdByEmailAddress(email);
        if (emailId.isPresent()) {
            emailStore.createNewAllocation(emailId.get(), userId);
            return emailId;
        }
        return emailStore.createNewEmail(idGenerator.generateId(), email, userId);
    }
}
