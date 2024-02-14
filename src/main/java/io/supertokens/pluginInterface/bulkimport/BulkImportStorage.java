package io.supertokens.pluginInterface.bulkimport;

import java.util.ArrayList;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

public interface BulkImportStorage extends NonAuthRecipeStorage {
      /**
       * Add users to the bulk_import_users table
       */
      void addBulkImportUsers(AppIdentifier appIdentifier, ArrayList<BulkImportUser> users)
                  throws StorageQueryException, TenantOrAppNotFoundException;

      /**
       * Get users from the bulk_import_users table
       */
      // void getBulkImportUsers(AppIdentifier appIdentifier, @Nullable String status, @Nonnull Integer limit, @Nullable String bulkImportUserId)
      //             throws StorageQueryException;

      /**
       * Delete users by id from the bulk_import_users table
       */
      // void deleteBulkImportUsers(AppIdentifier appIdentifier, @Nullable ArrayList<String> bulkImportUserIds)
      //             throws StorageQueryException;
}
