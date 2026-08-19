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

package io.supertokens.pluginInterface.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Thrown by {@link io.supertokens.pluginInterface.Storage#verifySchema()} when the database is missing tables or
 * columns that this version of the storage reads or writes. This typically means a release's manual migration
 * (the {@code ### Migration} section of the CHANGELOG) was not applied before upgrading.
 *
 * <p>{@link #getMessage()} is operator-facing: it lists what is missing and the SQL that would add it.
 */
public class SchemaMismatchException extends Exception {
    private static final long serialVersionUID = 1L;

    private final List<String> missingTables;
    private final Map<String, List<String>> missingColumnsByTable;
    private final List<String> suggestedSql;

    public SchemaMismatchException(String message, List<String> missingTables,
                                   Map<String, List<String>> missingColumnsByTable, List<String> suggestedSql) {
        super(message);
        this.missingTables = Collections.unmodifiableList(missingTables);
        this.missingColumnsByTable = Collections.unmodifiableMap(missingColumnsByTable);
        this.suggestedSql = Collections.unmodifiableList(suggestedSql);
    }

    /** Fully qualified names of tables that do not exist at all. */
    public List<String> getMissingTables() {
        return missingTables;
    }

    /** Fully qualified table name -> columns that exist in the expected schema but not in the database. */
    public Map<String, List<String>> getMissingColumnsByTable() {
        return missingColumnsByTable;
    }

    /** DDL statements that would bring the database up to the expected schema (to be reviewed by the operator). */
    public List<String> getSuggestedSql() {
        return suggestedSql;
    }
}
