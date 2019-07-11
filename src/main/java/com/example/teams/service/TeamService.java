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
package com.example.teams.service;

import com.example.id.IdGenerator;
import com.example.Result;
import com.example.teams.infrastructure.TeamStore;
import com.example.teams.internal.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Service
public class TeamService {

    private final IdGenerator idGenerator;
    private final TeamStore teamStore;
    private final Clock clock;

    public TeamService(IdGenerator idGenerator, TeamStore teamStore, Clock clock) {
        this.idGenerator = idGenerator;
        this.teamStore = teamStore;
        this.clock = clock;
    }

    @Transactional
    public Result<String, Team> createNewTeam(Long ownerUserId, String name) {
        if (teamStore.ownerHasSameNamedTeam(ownerUserId, name)) {
            return Result.left("Owner already has a team named \"" + name + "\".");
        }
        Team team = Team.of(idGenerator.generateId(), name, Instant.now(clock), ownerUserId);
        Team result = teamStore.create(team);
        return Result.right(result);
    }

    public Optional<Team> findById(Long teamId) {
        return teamStore.findById(teamId);
    }
}
