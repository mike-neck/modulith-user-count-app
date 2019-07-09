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
package example.jpa.emails;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@SuppressWarnings("serialiVersionUID")
@Embeddable
public class EmailAllocationKey implements Serializable {

    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private Long userId;

    @Column(name = "email_id", nullable = false, unique = true)
    private Long emailId;

    public EmailAllocationKey(Long userId, Long emailId) {
        this.userId = userId;
        this.emailId = emailId;
    }

    public EmailAllocationKey() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAllocationKey)) return false;

        EmailAllocationKey that = (EmailAllocationKey) o;

        if (!userId.equals(that.userId)) return false;
        return emailId.equals(that.emailId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + emailId.hashCode();
        return result;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailAllocationKey{");
        sb.append("userId=").append(userId);
        sb.append(", emailId=").append(emailId);
        sb.append('}');
        return sb.toString();
    }
}
