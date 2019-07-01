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
package com.example.teams.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "teams")
@Entity
public class TeamEntity {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    public TeamEntity() {
    }

    public TeamEntity(Long id, String name, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
