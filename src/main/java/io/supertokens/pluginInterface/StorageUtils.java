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

package io.supertokens.pluginInterface;

import io.supertokens.pluginInterface.authRecipe.sqlStorage.AuthRecipeSQLStorage;
import io.supertokens.pluginInterface.bulkimport.sqlStorage.BulkImportSQLStorage;
import io.supertokens.pluginInterface.dashboard.sqlStorage.DashboardSQLStorage;
import io.supertokens.pluginInterface.emailpassword.sqlStorage.EmailPasswordSQLStorage;
import io.supertokens.pluginInterface.emailverification.sqlStorage.EmailVerificationSQLStorage;
import io.supertokens.pluginInterface.multitenancy.MultitenancyStorage;
import io.supertokens.pluginInterface.oauth.OAuthStorage;
import io.supertokens.pluginInterface.passwordless.sqlStorage.PasswordlessSQLStorage;
import io.supertokens.pluginInterface.session.SessionStorage;
import io.supertokens.pluginInterface.thirdparty.sqlStorage.ThirdPartySQLStorage;
import io.supertokens.pluginInterface.totp.sqlStorage.TOTPSQLStorage;
import io.supertokens.pluginInterface.useridmapping.UserIdMappingStorage;
import io.supertokens.pluginInterface.usermetadata.sqlStorage.UserMetadataSQLStorage;
import io.supertokens.pluginInterface.userroles.sqlStorage.UserRolesSQLStorage;
import io.supertokens.pluginInterface.webauthn.slqStorage.WebAuthNSQLStorage;

public class StorageUtils {
    public static AuthRecipeSQLStorage getAuthRecipeStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }

        return (AuthRecipeSQLStorage) storage;
    }

    public static EmailPasswordSQLStorage getEmailPasswordStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (EmailPasswordSQLStorage) storage;
    }

    public static PasswordlessSQLStorage getPasswordlessStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (PasswordlessSQLStorage) storage;
    }

    public static ThirdPartySQLStorage getThirdPartyStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (ThirdPartySQLStorage) storage;
    }

    public static EmailVerificationSQLStorage getEmailVerificationStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (EmailVerificationSQLStorage) storage;
    }

    public static SessionStorage getSessionStorage(Storage storage) {
        return (SessionStorage) storage;
    }

    public static UserMetadataSQLStorage getUserMetadataStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }

        return (UserMetadataSQLStorage) storage;
    }

    public static UserIdMappingStorage getUserIdMappingStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }

        return (UserIdMappingStorage) storage;
    }

    public static UserRolesSQLStorage getUserRolesStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (UserRolesSQLStorage) storage;
    }

    public static DashboardSQLStorage getDashboardStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (DashboardSQLStorage) storage;
    }

    public static TOTPSQLStorage getTOTPStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (TOTPSQLStorage) storage;
    }

    public static ActiveUsersSQLStorage getActiveUsersStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (ActiveUsersSQLStorage) storage;
    }

    public static MultitenancyStorage getMultitenancyStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (MultitenancyStorage) storage;
    }

    public static BulkImportSQLStorage getBulkImportStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }

        return (BulkImportSQLStorage) storage;
    }

    public static OAuthStorage getOAuthStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (OAuthStorage) storage;
    }

    public static WebAuthNSQLStorage getWebAuthNStorage(Storage storage) {
        if (storage.getType() != STORAGE_TYPE.SQL) {
            throw new UnsupportedOperationException("");
        }
        return (WebAuthNSQLStorage) storage;
    }
}
