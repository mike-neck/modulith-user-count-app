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
package com.example.teams.internal;

import com.example.teams.TeamId;
import com.example.teams.entities.TeamEntity;
import com.example.teams.entities.TeamOwnerEntity;
import com.example.users.UserId;

import java.time.Instant;

public class Team {

    private final Long teamId;
    private final String name;
    private final Instant createdAt;
    private final Long ownerUserId;

    private Team(Long teamId, String name, Instant createdAt, Long ownerUserId) {
        this.teamId = teamId;
        this.name = name;
        this.createdAt = createdAt;
        this.ownerUserId = ownerUserId;
    }

    public static Team of(long teamId, String name, Instant createdAt, long ownerUserId) {
        return new Team(teamId, name, createdAt, ownerUserId);
    }

    public static Team fromEntities(TeamEntity teamEntity, TeamOwnerEntity ownerEntity) {
        if (!teamEntity.getId().equals(ownerEntity.getId().getTeamId())) {
            throw new IllegalArgumentException();
        }
        return new Team(teamEntity.getId(), teamEntity.getName(), teamEntity.getCreatedAt(), ownerEntity.getId().getUserId());
    }

    public TeamEntity teamEntity() {
        return new TeamEntity(teamId, name, createdAt);
    }

    public TeamOwnerEntity teamOwnerEntity() {
        return new TeamOwnerEntity(ownerUserId, teamId);
    }

    public TeamId id() {
        return new TeamId(teamId);
    }

    public String name() {
        return name;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public UserId ownerUserId() {
        return new UserId(ownerUserId);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Team{");
        sb.append("teamId=").append(teamId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", ownerUserId=").append(ownerUserId);
        sb.append('}');
        return sb.toString();
    }
}
