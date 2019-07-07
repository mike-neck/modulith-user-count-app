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

import com.example.Utils;
import com.example.Validation;

public class UserCreateRequest {

    private String name;

    private String email;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    Validation<String> validator() {
        Validation<String> nameIsRequired = Utils.notNull(name, "name is required");
        Validation<String> emailIsRequired = Utils.notNull(email, "email is required");
        Validation<String> nameCannotBeEmpty = Utils.notEmpty(name, "name cannot be empty");
        Validation<String> emailCannotBeEmpty = Utils.notEmpty(email, "email cannot be empty");

        return Validation.all(nameIsRequired, emailIsRequired, nameCannotBeEmpty, emailCannotBeEmpty);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserCreateRequest{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
