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
package com.example.email.entities;

import com.example.users.entities.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "email_allocations")
public class EmailAllocationEntity {

    @EmbeddedId
    private EmailAllocationKey id;

    @OneToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false, name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false, name = "email_id", referencedColumnName = "id")
    private EmailEntity email;

    public EmailAllocationEntity() {
    }

    public EmailAllocationEntity(EmailAllocationKey id) {
        this.id = id;
    }

    public EmailAllocationEntity(EmailAllocationKey id, UserEntity user, EmailEntity email) {
        this.id = id;
        this.user = user;
        this.email = email;
    }

    public EmailAllocationKey getId() {
        return id;
    }

    public void setId(EmailAllocationKey id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public EmailEntity getEmail() {
        return email;
    }

    public void setEmail(EmailEntity email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailAllocationEntity{user_id=" + id.getUserId() + ",email_id=" + id.getEmailId() + "}";
    }
}
