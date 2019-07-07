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
package com.example.email.repositories;

import com.example.email.entities.EmailAllocationEntity;
import com.example.email.entities.EmailAllocationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmailAllocationRepository extends JpaRepository<EmailAllocationEntity, EmailAllocationKey> {

    @Query(name = "EmailAllocationRepository.countByEmailAddress",
            value = "select count(emailAllocation) from EmailAllocationEntity emailAllocation join EmailEntity email on emailAllocation.email = email where email.address = :email")
    int countByEmailAddress(@Param("email") String email);

    Optional<EmailAllocationEntity> findByEmailAddress(String emailAddress);
}
