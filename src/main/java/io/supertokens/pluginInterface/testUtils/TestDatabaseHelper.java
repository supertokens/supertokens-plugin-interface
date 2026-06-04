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
 *
 */

package io.supertokens.pluginInterface.testUtils;

/**
 * SPI for per-worker test database lifecycle management.
 * Implementations are discovered via {@link java.util.ServiceLoader}.
 * Only storage plugins that need to create databases before tests run should implement this.
 */
public interface TestDatabaseHelper {
    /** Create (or return the existing) per-worker test database and return its name. */
    String createTestDatabase();

    /** Clear the thread-local database reference for the current test. Does not drop the database. */
    void dropCurrentTestDatabase();

    /** Return the database name set by the most recent {@link #createTestDatabase()} call on this thread. */
    String getCurrentTestDatabase();

    String getHost();
    String getPort();
    String getUser();
    String getPassword();
}
