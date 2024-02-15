package io.supertokens.pluginInterface.bulkimport;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

public interface BulkImportStorage extends NonAuthRecipeStorage {
      /**
       * Add users to the bulk_import_users table
       */
      void addBulkImportUsers(AppIdentifier appIdentifier, ArrayList<BulkImportUser> users)
                  throws StorageQueryException,
                  io.supertokens.pluginInterface.bulkimport.exceptions.DuplicateUserIdException;

      /**
       * Get users from the bulk_import_users table
       */
      JsonObject[] getBulkImportUsers(AppIdentifier appIdentifier, @Nonnull Integer limit, @Nullable String status,
                  @Nullable String bulkImportUserId) throws StorageQueryException;

      /**
       * Delete users by id from the bulk_import_users table
       */
      // void deleteBulkImportUsers(AppIdentifier appIdentifier, @Nullable ArrayList<String> bulkImportUserIds)
      //             throws StorageQueryException;
}
