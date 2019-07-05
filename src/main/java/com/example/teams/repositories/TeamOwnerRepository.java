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
package com.example.teams.repositories;

import com.example.teams.entities.TeamOwnerEntity;
import com.example.teams.entities.TeamOwnerEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamOwnerRepository extends JpaRepository<TeamOwnerEntity, TeamOwnerEntityKey> {

    @Query(name = "findByOwnerUserIdAndTeamName", value = "select to from TeamOwnerEntity to join TeamEntity t on to.team = t where t.name = :team_name and to.id.userId = :user_id")
    Optional<TeamOwnerEntity> findByTeamNameAndOwnerUserId(@Param("user_id") Long ownerUserId, @Param("team_name") String name);
}
