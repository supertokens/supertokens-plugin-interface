/*
 *    Copyright (c) 2026, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.bulkimport;

import java.util.List;
import java.util.Objects;

import io.supertokens.pluginInterface.authRecipe.ACCOUNT_INFO_TYPE;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;

public class PrimaryUser {
    public static class AccountInfo {
        public final ACCOUNT_INFO_TYPE type;
        public final String value;

        public AccountInfo(ACCOUNT_INFO_TYPE type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AccountInfo that = (AccountInfo) o;
            return type == that.type && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, value);
        }
    }

    public final AppIdentifier appIdentifier;
    public final List<AccountInfo> accountInfos;
    public final List<String> tenantIds;
    public final String primaryUserId;

    public PrimaryUser(AppIdentifier appIdentifier, List<AccountInfo> accountInfos, List<String> tenantIds, String primaryUserId) {
        this.appIdentifier = appIdentifier;
        this.accountInfos = accountInfos;
        this.tenantIds = tenantIds;
        this.primaryUserId = primaryUserId;
    }
}
