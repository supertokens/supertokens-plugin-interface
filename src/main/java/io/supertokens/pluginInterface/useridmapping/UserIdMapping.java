/*
 *    Copyright (c) 2022, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.useridmapping;

import javax.annotation.Nullable;

public class UserIdMapping {

    public final String superTokensUserId;

    public final String externalUserId;

    public final @Nullable String externalUserIdInfo;

    public UserIdMapping(String superTokensUserId, String externalUserId, @Nullable String externalUserIdInfo) {
        this.superTokensUserId = superTokensUserId;
        this.externalUserId = externalUserId;
        this.externalUserIdInfo = externalUserIdInfo;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UserIdMapping) {
            UserIdMapping otherUserIdMapping = (UserIdMapping) other;
            return otherUserIdMapping.superTokensUserId.equals(this.superTokensUserId)
                    && otherUserIdMapping.externalUserId.equals(this.externalUserId)
                    && otherUserIdMapping.externalUserIdInfo.equals(this.externalUserIdInfo);
        }
        return false;
    }
}