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
package example.jpa.teams;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TeamOwnerEntityKey implements Serializable {

    @Column(name = "user_id", updatable = false, nullable = false)
    private Long userId;

    @Column(name = "team_id", updatable = false, nullable = false)
    private Long teamId;

    public TeamOwnerEntityKey() {
    }

    @SuppressWarnings("WeakerAccess")
    public TeamOwnerEntityKey(Long userId, Long teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamOwnerEntityKey)) return false;

        TeamOwnerEntityKey that = (TeamOwnerEntityKey) o;

        if (!userId.equals(that.userId)) return false;
        return teamId.equals(that.teamId);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + teamId.hashCode();
        return result;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamOwnerEntityKey{");
        sb.append("userId=").append(userId);
        sb.append(", teamId=").append(teamId);
        sb.append('}');
        return sb.toString();
    }
}
