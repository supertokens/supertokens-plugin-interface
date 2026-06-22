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

package io.supertokens.pluginInterface.auditlog;

public class AuditLogEvent {
    public final String appId;
    public final String tenantId;
    public final String recipeUserId;
    public final String primaryOrRecipeUserId;
    public final String eventType;
    public final String status;
    public final String authPrincipal;
    public final String identifier;
    public final long createdAt;
    /** JSON-encoded payload; may be null. */
    public final String payload;

    public AuditLogEvent(
            String appId,
            String tenantId,
            String recipeUserId,
            String primaryOrRecipeUserId,
            String eventType,
            String status,
            String authPrincipal,
            String identifier,
            long createdAt,
            String payload) {
        this.appId = appId;
        this.tenantId = tenantId;
        this.recipeUserId = recipeUserId;
        this.primaryOrRecipeUserId = primaryOrRecipeUserId;
        this.eventType = eventType;
        this.status = status;
        this.authPrincipal = authPrincipal;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.payload = payload;
    }
}
