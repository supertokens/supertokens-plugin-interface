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

package io.supertokens.pluginInterface.oauth;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.oauth.exceptions.OAuth2ClientAlreadyExistsForAppException;

public interface OAuthStorage extends NonAuthRecipeStorage {

    public boolean doesClientIdExistForThisApp(AppIdentifier appIdentifier, String clientId) throws
            StorageQueryException;

    public void addClientForApp(AppIdentifier appIdentifier, String clientId) throws StorageQueryException,
            OAuth2ClientAlreadyExistsForAppException;

    public boolean isClientIdAlreadyExists(String clientId) throws StorageQueryException;

    public void removeAppClientAssociation(AppIdentifier appIdentifier, String clientId) throws StorageQueryException;
}
