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
import com.example.users.UserId;

import java.util.Optional;

public interface EmailStore {

    boolean allocated(Email email);

    Optional<Long> findUserIdByEmailAddress(Email email);

    void createNewAllocation(Long emailId, UserId userId);

    Optional<Long> createNewEmail(long id, Email email, UserId userId);
}
