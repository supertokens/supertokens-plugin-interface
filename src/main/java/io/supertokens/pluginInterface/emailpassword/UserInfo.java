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

public class UserInfo {

    public final String id;

    public final String email;

    public final String passwordHash;

    public final long timeJoined;

    public final boolean isEmailVerified;

    public UserInfo(String id, String email, String passwordHash, long timeJoined, boolean isEmailVerified) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.timeJoined = timeJoined;
        this.isEmailVerified = isEmailVerified;
    }

    public UserInfo(String id, String email, String passwordHash, long timeJoined) {
        this(id, email, passwordHash, timeJoined, false);
    }
}
