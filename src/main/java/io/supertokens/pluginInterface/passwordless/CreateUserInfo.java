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

package io.supertokens.pluginInterface.passwordless;


import javax.annotation.Nullable;

public class CreateUserInfo {
    public final String id;
    public final String email;
    public final String phoneNumber;
    public final long timeJoined;

    public CreateUserInfo(String id, @Nullable String email, @Nullable String phoneNumber, long timeJoined) {
        this.id = id;

        if (email == null && phoneNumber == null) {
            throw new IllegalArgumentException("Both email and phoneNumber cannot be null");
        }

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.timeJoined = timeJoined;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CreateUserInfo) {
            CreateUserInfo otherUserInfo = (CreateUserInfo) other;
            return ((otherUserInfo.email == null && this.email == null) || otherUserInfo.email.equals(this.email))
                    && ((otherUserInfo.phoneNumber == null && this.phoneNumber == null)
                            || otherUserInfo.phoneNumber.equals(this.phoneNumber))
                    && otherUserInfo.id.equals(this.id) && otherUserInfo.timeJoined == this.timeJoined;
        }
        return false;
    }
}
