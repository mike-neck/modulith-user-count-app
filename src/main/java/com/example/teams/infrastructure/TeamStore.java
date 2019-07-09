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
package com.example.teams.infrastructure;

import example.jpa.teams.TeamEntity;
import example.jpa.teams.TeamOwnerEntity;
import com.example.teams.internal.Team;
import com.example.teams.repositories.TeamOwnerRepository;
import com.example.teams.repositories.TeamRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TeamStore {

    private final TeamOwnerRepository ownerRepository;
    private final TeamRepository teamRepository;

    public TeamStore(TeamOwnerRepository ownerRepository, TeamRepository teamRepository) {
        this.ownerRepository = ownerRepository;
        this.teamRepository = teamRepository;
    }

    public Team create(Team team) {
        TeamEntity teamEntity = teamRepository.save(team.teamEntity());
        TeamOwnerEntity teamOwnerEntity = ownerRepository.save(team.teamOwnerEntity());
        return Team.fromEntities(teamEntity, teamOwnerEntity);
    }

    public boolean ownerHasSameNamedTeam(Long ownerUserId, String teamName) {
        return ownerRepository.findByTeamNameAndOwnerUserId(ownerUserId, teamName).isPresent();
    }

    public Optional<Team> findById(Long teamId) {
        Optional<TeamEntity> teamEntity = teamRepository.findById(teamId);
        Optional<TeamOwnerEntity> ownerEntity = ownerRepository.findByTeamId(teamId);
        return teamEntity.flatMap(team -> ownerEntity.map(owner -> Team.fromEntities(team, owner)));
    }
}
