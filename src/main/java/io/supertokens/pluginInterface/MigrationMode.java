/*
 *    Copyright (c) 2025, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface;

public enum MigrationMode {
    LEGACY,                // writeold + readold (current production)
    DUAL_WRITE_READ_OLD,   // writeold + writenew + readold
    DUAL_WRITE_READ_NEW,   // writeold + writenew + readnew
    MIGRATED;              // writenew + readnew

    public boolean readsFromOldTables() {
        return this == LEGACY || this == DUAL_WRITE_READ_OLD;
    }

    public boolean readsFromNewTables() {
        return this == DUAL_WRITE_READ_NEW || this == MIGRATED;
    }

    public boolean writesToOldTables() {
        return this != MIGRATED;
    }

    public boolean writesToNewTables() {
        return this != LEGACY;
    }
}
