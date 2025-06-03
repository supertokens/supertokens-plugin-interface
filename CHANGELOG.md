# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [8.0.1]

- Removes unnecessary `implementationDependencies.json` file

## [8.0.0]

- Updates JRE to 21.

## [7.1.1]

- Adds support for BulkImportUser's id generation while the upload is happening

## [7.1.0]

- Adds support for Webauthn (passkeys)

## [7.0.0] 

- Adds support for Bulk Import
- Adds `BulkImportUser` class to represent a bulk import user
- Adds `BulkImportStorage` interface
- Adds `DuplicateUserIdException` class
- Adds `createBulkImportProxyStorageInstance` method in `Storage` class
- Adds `closeConnectionForBulkImportProxyStorage`, `commitTransactionForBulkImportProxyStorage`, and `rollbackTransactionForBulkImportProxyStorage` method in `SQLStorage` class
- Adds `BulkImportTransactionRolledBackException` for signaling if the transaction was rolled back by the DBMS

## [6.3.0] - 2024-10-02

- Adds `OAuthStorage` interface for OAuth Provider support

## [6.2.0] - 2024-05-24

- Adds new class `ConfigFieldInfo` that represents a core config field
- Adds new method `getPluginConfigFieldsInfo` to fetch the plugin config as json in `DashboardStorage`
- Updates `TenantConfig` to support `null` and empty array state for `firstFactors`

## [6.1.0] - 2024-04-17

- Adds `addTenantIdentifier` to the storage interface.

## [6.0.0] - 2024-03-13

- Replace `TotpNotEnabledException` with `UnknownUserTotpIdException`
- ActiveUsersSQLStorage interface changes
    - Adds `deleteUserActive_Transaction` function
- ActiveUsersStorage interface changes
    - Removes `countUsersEnabledTotp`, `countUsersEnabledTotpAndActiveSince` and `deleteUserActive_Transaction`
      functions
    - Adds `countUsersThatHaveMoreThanOneLoginMethodOrTOTPEnabledAndActiveSince` function
- AuthRecipeStorage interface changes
    - Adds `getUsersCountWithMoreThanOneLoginMethodOrTOTPEnabled` function
- TenantConfig changes
    - Adds `firstFactors` and `requiredSecondaryFactors` fields
- Adds `createdAt` field to `TOTPDevice`
- TOTPSQLStorage interface changes
    - Adds `getDeviceByName_Transaction` and `createDevice_Transaction` functions
- Adds a new `useStaticKey` param to `updateSessionInfo_Transaction`
    - This enables smooth switching between `useDynamicAccessTokenSigningKey` settings by allowing refresh calls to
      change the signing key type of a session
      Adds `appIdentifier` parameter to `getUserIdMappingForSuperTokensIds` in `UserIdMappingStorage`

## [5.0.0] - 2024-03-05

- Removes types `AppIdentifierWithStorage` and `TenantIdentifierWithStorage`
- Adds `deleteAllUserRoleAssociationsForRole` function to `UserRolesStorage`

## [4.0.5] - 2023-12-05

- Adds `InvalidConfigException` to throws list of `canBeUsed` function

## [4.0.4] - 2023-11-28

- Removes the error `Please use a CDI version that is greater than the one in which account linking feature was enabled`
  while querying users with linked accounts, but from an older version of CDI. We return details of the oldest login
  method in this case.

## [4.0.3] - 2023-11-10

- Adds function to update userId to externalUserId for email verification

## [4.0.2] - 2023-10-19

- Fixes serialization of thirdParty config

## [4.0.1] - 2023-10-19

- Fixes cloning of `TenantConfig` object to include `null` values

## [4.0.0] - 2023-09-19

- Adds support for account linking
- Adds `AuthRecipeUserInfo` class and removes `UserInfo` from emailpassword, passwordless and thirdparty.
- ActiveUsersStorage interface changes
    - Removes `deleteUserActive`
    - Adds `deleteUserActive_Transaction`
    - Adds `countUsersThatHaveMoreThanOneLoginMethodAndActiveSince`
- EmailPasswordStorage interfaces changes
    - Removes `deleteEmailPasswordUser`, `getUserInfoUsingId`, `getUserInfoUsingEmail`
    - Changes return type of `signUp` from `UserInfo` to `AuthRecipeUserInfo`
    - Changes `PasswordResetTokenInfo` to accept additional param `email`
- EmailPasswordSQLStorage interface changes
    - Removes `getUserInfoUsingId_Transaction`
    - Adds `deleteEmailPasswordUser_Transaction`
- EmailVerificationStorage interface changes
    - Removes `deleteEmailVerificationUserInfo`
- EmailVerificationSQLStorage interface changes
    - Adds `deleteEmailVerificationUserInfo_Transaction`
- MultitenancyStorage interface changes
    - Removes `addUserIdToTenant`
- MultitenancySQLStorage interface changes
    - Adds `addUserIdToTenant_Transaction`
- PasswordlessStorage interface changes
    - Changes return type of `createUser` from `UserInfo` to `AuthRecipeUserInfo`
    - Removes `deletePasswordlessUser`, `getUserById`, `getUserByEmail`, `getUserByPhoneNumber`
- PasswordlessSQLStorage interface changes
    - Adds `deletePasswordlessUser_Transaction`
- SessionInfo accepts additional parameter `recipeUserId`
- SessionSQLStorage interface changes
    - Adds `deleteSessionsOfUser_Transaction`
- ThirdPartyStorage interface changes
  -
  Removes `deleteThirdPartyUser`, `getThirdPartyUserInfoUsingId`, `getThirdPartyUserInfoUsingId`, `getThirdPartyUsersByEmail`
    - Changes return type of `signUp` from `UserInfo` to `AuthRecipeUserInfo`
- ThirdPartySQLStorage interface changes
    - Adds `deleteThirdPartyUser_Transaction`
    - Removes `getUserInfoUsingId_Transaction`
- UserIdMappingSQLStorage interface changes
    - Adds `getUserIdMapping_Transaction`, `getUserIdMapping_Transaction`
- UserMetadataSQLStorage interface changes
    - Adds `deleteUserMetadata_Transaction`
- UserRolesStorage interface changes
    - Removes `deleteAllRolesForUser`
- UserRolesSQLStorage interface changes
    - Adds `deleteAllRolesForUser_Transaction`

## [3.0.1] - 2023-07-04

- Updates `TenantConfig` toJson function to protect core config as well.

## [3.0.0] - 2023-06-02

- Adds support for multi-tenancy

## [2.23.0] - 2023-04-05

- Added `useStaticKey ` into session info classes
- Added `hashCode` override for JWTSigningKeyInfo

## [2.22.0] - 2023-03-30

- Adds Support for Dashboard Search

## [2.21.0] - 2023-03-27

- Introduce TOTP Recipe plugin interface
- Introduce Active users storage plugin interface

## [2.20.0] - 2023-02-21

- Dashboard Recipe Interface

## [2.19.0] - 2022-11-07

- Updates version so that new plugin versions can only be used by new core versions due to dependency changes.
- Updates ThirdParty recipe errors to now extend `ThirdPartyException`

## [2.18.0] - 2022-08-18

- Adds `LOG_LEVEL` enum, and changes `initFileLogging` interface function to accept a log level.

## [2.17.0] - 2022-08-10

- Adds `getUserIdMappingForSuperTokensIds` to the UserIdMapping Interface
- Adds `isUserIdBeingUsedInNonAuthRecipe` and `addInfoToNonAuthRecipesBasedOnUserId` to the Storage Interface
- Adds `NonAuthRecipeStorage` Interface
- All non-auth recipes now extend the `NonAuthRecipeStorage` Interface

## [2.16.0] - 2022-07-25

- UserId Mapping interface

## [2.15.0] - 2022-06-07

- Changes name of `getAllSessionHandlesForUser` to `getAllNonExpiredSessionHandlesForUser`.

## [2.14.0] - 2022-05-05

- User Roles interface

## [2.13.0] - 2022-03-04

### Added

- usermetadata recipe functions

## [2.12.0] - 2022-02-23

# Added

- Allow starting transactions with lower isolation level (the default is SERIALIZABLE) through an optional flag in
  startTransaction
- add workflow to verify if pr title follows conventional commits

## [2.11.0] - 2022-01-14

# Added

- Passwordless interface

## [2.10.0] - 2021-11-16

# Added

- User deletion methods

## [2.9.0] - 2021-08-08

### Added

- JWT recipe related changes
- Multiple access token signing key related changes: https://github.com/supertokens/supertokens-core/issues/305

## [2.8.0] - 2021-06-21

### Added

- Pagination and count functions for all recipes: https://github.com/supertokens/supertokens-core/issues/259
- GetUsersByEmail function for ThirdParty recipe: https://github.com/supertokens/supertokens-core/issues/277
- 2 email verification functions: `unverifyEmail` and `revokeAllTokens` for EmailVerification: https
  ://github.com/supertokens/supertokens-core/issues/270
- Add change email interface method within transaction: https://github.com/supertokens/supertokens-core/issues/275

## [2.6.0] - 2021-02-16

### Changed

- Extracted email verification into its own recipe
- ThirdParty interface

## [2.5.0] - 2021-01-14

### Added

- Added RowMapper interface for db queries
- Email verification related changes
- User pagination related queries
