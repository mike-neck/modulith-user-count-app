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

import com.example.users.entities.UserEntity;

import javax.persistence.*;

@Table(name = "team_owners")
@Entity
public class TeamOwnerEntity {

    @Id
    private TeamOwnerEntityKey id;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "team_id", referencedColumnName = "id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public TeamOwnerEntity() {
    }

    public TeamOwnerEntity(TeamOwnerEntityKey id, TeamEntity team, UserEntity user) {
        this.id = id;
        this.team = team;
        this.user = user;
    }

    public TeamOwnerEntityKey getId() {
        return id;
    }

    public void setId(TeamOwnerEntityKey id) {
        this.id = id;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamOwnerEntity{");
        sb.append("id=").append(id);
        sb.append(", team=").append(team);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
