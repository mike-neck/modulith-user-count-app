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
package com.example.email.infrastructure;

import com.example.email.Email;
import example.jpa.emails.EmailAllocationEntity;
import example.jpa.emails.EmailAllocationKey;
import example.jpa.emails.EmailEntity;
import com.example.email.repositories.EmailAllocationRepository;
import com.example.email.repositories.EmailRepository;
import com.example.users.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailStoreImpl implements EmailStore {

    private final EmailRepository emailRepository;
    private final EmailAllocationRepository emailAllocationRepository;

    public EmailStoreImpl(
            EmailRepository emailRepository,
            EmailAllocationRepository emailAllocationRepository) {
        this.emailRepository = emailRepository;
        this.emailAllocationRepository = emailAllocationRepository;
    }

    @Override
    public boolean allocated(Email email) {
        int count = emailAllocationRepository.countByEmailAddress(email.email);
        return count > 0;
    }

    @Override
    public Optional<Long> findUserIdByEmailAddress(Email email) {
        return emailAllocationRepository.findByEmailAddress(email.email)
                .map(entity -> entity.getId().getEmailId());
    }

    @Override
    public void createNewAllocation(Long emailId, UserId userId) {
        EmailAllocationKey key = new EmailAllocationKey(userId.userId, emailId);
        EmailAllocationEntity entity = new EmailAllocationEntity(key);
        emailAllocationRepository.save(entity);
    }

    @Override
    public Optional<Long> createNewEmail(long id, Email email, UserId userId) {
        EmailEntity emailEntity = new EmailEntity(id, email.email);
        emailRepository.save(emailEntity);
        EmailAllocationKey key = new EmailAllocationKey(userId.userId, id);
        EmailAllocationEntity allocationEntity = new EmailAllocationEntity(key);
        emailAllocationRepository.save(allocationEntity);
        return Optional.of(id);
    }
}
