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

package io.supertokens.pluginInterface.bulkimport.exceptions;

import io.supertokens.pluginInterface.exceptions.StorageTransactionLogicException;

import java.io.Serial;
/*
    The purpose of this exception is to signal for the BulkImport cronjob that the mysql transaction was rolled back.
    PostgreSQL supports nested transactions while MySQL doesn't. When encountering a deadlock in the multithreaded
    bulkimport process, both DBMS issue a rollback to resolve the deadlock.
    In PostgreSQL: the innermost transaction gets reverted and later retried by the java logic.
    In MySQL: the rollback causes the whole "big" transaction to revert, but the java logic only retries the innermost
    automatically.
    This exception is intended for the ProcessBulkImportUsers cronjob, to signal to restart the whole transaction.
 */
public class BulkImportTransactionRolledBackException extends StorageTransactionLogicException {
    @Serial
    private static final long serialVersionUID = 5196064868023712426L;

    public BulkImportTransactionRolledBackException(Exception e) {
        super(e);
    }
}
