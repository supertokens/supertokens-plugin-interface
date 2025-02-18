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

package io.supertokens.pluginInterface.bulkimport;

import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

public class ImportUserBase {

    public String userId;
    public String email;
    public TenantIdentifier tenantIdentifier;
    public long timeJoinedMSSinceEpoch;

    public ImportUserBase(String userId, String email, TenantIdentifier tenantIdentifier, long timeJoinedMSSinceEpoch) {
        this.userId = userId; //this will be the supertokens userId.
        this.email = email;
        this.tenantIdentifier = tenantIdentifier;
        this.timeJoinedMSSinceEpoch = timeJoinedMSSinceEpoch;
    }
}
