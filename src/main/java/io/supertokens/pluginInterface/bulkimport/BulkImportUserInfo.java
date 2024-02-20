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

import com.google.gson.JsonObject;

import io.supertokens.pluginInterface.bulkimport.BulkImportStorage.BulkImportUserStatus;

public class BulkImportUserInfo {
    public String id;
    public String rawData;
    public BulkImportUserStatus status;
    public Long createdAt;
    public Long updatedAt;

    public BulkImportUserInfo(String id, String rawData, BulkImportUserStatus status, Long createdAt, Long updatedAt) {
        this.id = id;
        this.rawData = rawData;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        result.addProperty("id", id);
        result.addProperty("rawData", rawData);
        result.addProperty("status", status.toString());
        result.addProperty("createdAt", createdAt);
        result.addProperty("updatedAt", updatedAt);
        return result;
    }
}
