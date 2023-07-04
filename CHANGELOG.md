# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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
