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

package io.supertokens.pluginInterface.sqlStorage;

import io.supertokens.pluginInterface.KeyValueInfo;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.emailpassword.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.exceptions.StorageTransactionLogicException;

public interface SQLStorage extends Storage {

    <T> T startSimpleTransactionHibernate(SimpleTransactionLogicHibernate<T> logic) throws Exception;

    <T> T startTransactionHibernate(TransactionLogicHibernate<T> logic)
            throws StorageQueryException, StorageTransactionLogicException;

    void commitTransaction(SessionObject sessionInstance) throws Exception;

    void setKeyValue_Transaction(SessionObject sessionInstance, String key, KeyValueInfo info)
            throws StorageQueryException;

    KeyValueInfo getKeyValue_Transaction(SessionObject sessionInstance, String key) throws StorageQueryException;

    interface TransactionLogicHibernate<T> {
        T mainLogicAndCommit(SessionObject sessionInstance) throws Exception;
    }

    interface SimpleTransactionLogicHibernate<T> {
        T mainLogic(SessionObject sessionInstance) throws Exception;
    }
}
