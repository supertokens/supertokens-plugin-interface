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
 */

package io.supertokens.pluginInterface.emailverification;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.emailverification.exception.DuplicateEmailVerificationTokenException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;

public interface EmailVerificationStorage extends Storage {

    void addEmailVerificationToken(EmailVerificationTokenInfo emailVerificationInfo)
            throws StorageQueryException, DuplicateEmailVerificationTokenException;

    EmailVerificationTokenInfo getEmailVerificationTokenInfo(String token)
            throws StorageQueryException;

    void deleteExpiredEmailVerificationTokens() throws StorageQueryException;

    EmailVerificationTokenInfo[] getAllEmailVerificationTokenInfoForUser(String userId, String email)
            throws StorageQueryException;

    boolean isEmailVerified(String userId, String email) throws StorageQueryException;

}