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

package io.supertokens.pluginInterface.session;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.session.sqlStorage.SessionSQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SessionSQLStorageTransactionTwinsTest {

    private static void assertTwin(String name, String autocommitName, Class<?>... autocommitParams)
            throws NoSuchMethodException {
        Method autocommit = SessionStorage.class.getMethod(autocommitName, autocommitParams);

        Class<?>[] twinParams = new Class<?>[autocommitParams.length + 1];
        twinParams[0] = TransactionConnection.class;
        System.arraycopy(autocommitParams, 0, twinParams, 1, autocommitParams.length);
        Method twin = SessionSQLStorage.class.getDeclaredMethod(name, twinParams);

        assertFalse(name + " must have no default implementation", twin.isDefault());
        assertEquals(autocommit.getReturnType(), twin.getReturnType());
        assertEquals(new HashSet<>(Arrays.asList(autocommit.getExceptionTypes())),
                new HashSet<>(Arrays.asList(twin.getExceptionTypes())));
        assertEquals(Collections.singleton(StorageQueryException.class),
                new HashSet<>(Arrays.asList(twin.getExceptionTypes())));
    }

    @Test
    public void deleteSessionTwinMirrorsAutocommitMethod() throws Exception {
        assertTwin("deleteSession_Transaction", "deleteSession", TenantIdentifier.class, String[].class);
    }

    @Test
    public void getAllNonExpiredSessionHandlesForUserTwinMirrorsAutocommitMethod() throws Exception {
        assertTwin("getAllNonExpiredSessionHandlesForUser_Transaction", "getAllNonExpiredSessionHandlesForUser",
                TenantIdentifier.class, String.class);
    }
}
