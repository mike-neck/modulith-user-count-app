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
package com.example.teams.api;

import com.example.teams.TeamId;
import com.example.teams.internal.Team;

import java.time.Instant;

@SuppressWarnings("WeakerAccess")
public class TeamJson {

    public final long id;
    public final String name;
    public final OwnerUserJson owner;
    public final Instant createdAt;

    public TeamJson(Team team, OwnerUserJson owner) {
        this(team.id(), team.name(), owner, team.createdAt());
    }

    TeamJson(TeamId id, String name, OwnerUserJson owner, Instant createdAt) {
        this.id = id.id;
        this.name = name;
        this.owner = owner;
        this.createdAt = createdAt;
    }

    @SuppressWarnings({"Duplicates", "StringBufferReplaceableByString"})
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamJson{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", owner=").append(owner);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
