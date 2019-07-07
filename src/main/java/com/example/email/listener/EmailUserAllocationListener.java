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
package com.example.email.listener;

import com.example.ConditionException;
import com.example.email.service.EmailService;
import com.example.users.UserCreationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailUserAllocationListener implements ApplicationListener<UserCreationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EmailUserAllocationListener.class);

    private final EmailService emailService;

    public EmailUserAllocationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserCreationEvent event) {
        UserCreationEvent.Data data = event.getEventData();
        Optional<Long> emailId = emailService.allocateEmailToUser(data.email, data.userId);
        emailId.ifPresentOrElse(
                id -> logger.info("success: email-user-allocation, email: {}, email-id: {}, user: {}", data.email, id, data.userId),
                () -> logger.info("failure: email-user-allocation, email: {}, user: {}", data.email, data.userId)
        );
        emailId.orElseThrow(() -> new ConditionException("failed to store user and email"));
    }
}
