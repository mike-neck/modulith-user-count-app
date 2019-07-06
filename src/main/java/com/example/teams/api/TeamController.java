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

import com.example.Result;
import com.example.Utils;
import com.example.Validation;
import com.example.teams.TeamId;
import com.example.teams.internal.Team;
import com.example.teams.service.TeamService;
import com.example.users.UserInfo;
import com.example.users.UserReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("teams")
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    private final UserReadService userService;
    private final TeamService teamService;

    public TeamController(UserReadService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "application/json")
    ResponseEntity<Map<String, Object>> create(@RequestParam("owner") Long ownerUserId, @RequestParam("team_name") String teamName) {
        Validation<String> ownerCannotBeNull = Utils.notNull(ownerUserId, "owner cannot be null");
        Validation<String> teamNameCannotBeNull = Utils.notNull(teamName, "team_name cannot be null");
        Validation<String> invalidUserId = Utils.not0(ownerUserId, "owner does not exist");
        Validation<String> teamNameCannotBeEmpty = Utils.notEmpty(teamName, "team name cannot be empty");

        Result<String, Void> validationResult = Validation.all(ownerCannotBeNull, teamNameCannotBeNull, invalidUserId, teamNameCannotBeEmpty).validate();

        //noinspection ConstantConditions
        return validationResult.errorMap(message -> ResponseEntity.badRequest().<Map<String, Object>>body(Map.of("success", false, "message", message)))
                .map(v -> userExisting(ownerUserId).validate())
                .flatMap(result -> result.errorMap(message -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", message))))
                .map(v -> sameTeamNotExisting(ownerUserId, teamName))
                .flatMap(result -> result.errorMap(message -> ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("success", false, "message", message))))
                .map(teamId -> ResponseEntity.ok(Map.<String, Object>of("success", true, "teamId", teamId.id)))
                .doOnLeft(e -> logger.info("failure: create-new-team, user-id: {}, team-name: {}, message: {}", ownerUserId, teamName, e.getBody().get("message")))
                .doOnRight(entity -> logger.info("success: create-new-team, user-id: {}, team-name: {}, team-id: {}", ownerUserId, teamName, entity.getBody().get("teamId")))
                .rescue(entity -> entity);
    }

    private Validation<String> userExisting(Long userId) {
        return () -> {
            if (userService.getUser(userId).isPresent()) {
                return Result.right((Void) null);
            }
            return Result.left(String.format("User[%d] does not exist", userId));
        };
    }

    private Result<String, TeamId> sameTeamNotExisting(Long ownerUser, String teamName) {
        Result<String, Team> newTeam = teamService.createNewTeam(ownerUser, teamName);
        return newTeam.map(Team::id);
    }

    @GetMapping(path = "{teamId}", produces = "application/json")
    ResponseEntity<?> get(@PathVariable("teamId") Long teamId) {
        Optional<Team> team = teamService.findById(teamId);
        Optional<OwnerUserJson> ownerUser = team
                .map(Team::ownerUserId)
                .flatMap(userService::getUser)
                .map(user -> new OwnerUserJson(user.id, user.name));

        ResponseEntity<?> responseEntity = ownerUser.flatMap(user -> team.map(t -> new TeamJson(t, user)))
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> 
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.<String, Serializable>of(                                 
                                "success", false, 
                                "message", String.format("Team[%d] is not found.", teamId))));
        if (responseEntity.getStatusCode().isError()) {
            //noinspection unchecked,ConstantConditions
            logger.info("failure: get-team, team-id: {}, message: {}", teamId, ((Map<String, Object>)responseEntity.getBody()).get("message"));
        } else {
            logger.info("success: get-team, team-id: {}", teamId);
        }
        return responseEntity;
    }
}
