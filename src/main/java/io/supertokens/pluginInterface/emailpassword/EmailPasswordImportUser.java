/*
 *    Copyright (c) 2024, VRAI Labs and/or its affiliates. All rights reserved.
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

import io.supertokens.pluginInterface.bulkimport.ImportUserBase;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

public class EmailPasswordImportUser  extends ImportUserBase {

    public String passwordHash;

    public EmailPasswordImportUser(String userId, String email, String passwordHash, TenantIdentifier tenantId, long timeJoinedInMSSinceEpoch) {
        super(userId, email, tenantId, timeJoinedInMSSinceEpoch);
        this.passwordHash = passwordHash;
    }
}
