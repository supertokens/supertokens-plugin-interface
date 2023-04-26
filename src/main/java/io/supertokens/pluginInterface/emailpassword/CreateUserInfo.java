/*
 *    Copyright (c) 2020, VRAI Labs and/or its affiliates. All rights reserved.
 *
 *    This software is licensed under the Apache License, Version 2.0 (the
 *    "License") as published by the Apache Software Foundation.
 *
 *    You may not use this file except in compliance with the License. You may
 *    obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */

package io.supertokens.pluginInterface.emailpassword;

public class CreateUserInfo {

    public final String id;
    public final String email;

    // using transient, we tell Gson not to include this when creating a JSON
    public transient final String passwordHash;
    public final long timeJoined;

    public CreateUserInfo(String id, String email, String passwordHash, long timeJoined) {
        this.id = id;
        this.timeJoined = timeJoined;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CreateUserInfo) {
            CreateUserInfo otherUserInfo = (CreateUserInfo) other;
            return otherUserInfo.email.equals(this.email) && otherUserInfo.passwordHash.equals(this.passwordHash)
                    && otherUserInfo.id.equals(this.id) && otherUserInfo.timeJoined == this.timeJoined;
        }
        return false;
    }
}
