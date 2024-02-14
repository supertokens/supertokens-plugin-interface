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

package io.supertokens.pluginInterface.bulkimport;

import java.util.List;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.supertokens.pluginInterface.bulkimport.exceptions.InvalidBulkImportDataException;

import java.util.ArrayList;
import java.util.Arrays;

public class BulkImportUser {
    public String id;
    public JsonObject userData;
    public String externalUserId;
    public JsonObject userMetadata;
    public List<String> userRoles;
    public List<TotpDevice> totpDevices;
    public List<LoginMethod> loginMethods;
    public ArrayList<String> errors = new ArrayList<>();

    public BulkImportUser(JsonObject userData, ArrayList<String> validTenantIds, String id) throws InvalidBulkImportDataException {
        this.id =  id != null ? id : UUID.randomUUID().toString();
        this.userData = userData;
        this.externalUserId = parseAndValidateField(userData, "externalUserId", ValueType.STRING, false, String.class,
                ".");
        this.userMetadata = parseAndValidateField(userData, "userMetadata", ValueType.OBJECT, false, JsonObject.class,
                ".");
        this.userRoles = getParsedUserRoles(userData);
        this.totpDevices = getParsedTotpDevices(userData);
        this.loginMethods = getParsedLoginMethods(userData, validTenantIds);

        if (errors.size() > 0) {
            throw new InvalidBulkImportDataException(errors);
        }
    }

    public String toString() {
        return this.userData.toString();
    }

    private ArrayList<String> getParsedUserRoles(JsonObject userData) {
        JsonArray jsonUserRoles = parseAndValidateField(userData, "roles", ValueType.ARRAY_OF_STRING, false,
                JsonArray.class, ".");

        if (jsonUserRoles == null) {
            return null;
        }

        ArrayList<String> userRoles = new ArrayList<>();
        jsonUserRoles.forEach(role -> userRoles.add(role.getAsString()));
        return userRoles;
    }

    private ArrayList<TotpDevice> getParsedTotpDevices(JsonObject userData) {
        JsonArray jsonTotpDevices = parseAndValidateField(userData, "totp", ValueType.ARRAY_OF_OBJECT, false,
                JsonArray.class, ".");
        if (jsonTotpDevices == null) {
            return null;
        }

        ArrayList<TotpDevice> totpDevices = new ArrayList<>();
        for (JsonElement jsonTotpDevice : jsonTotpDevices) {
            totpDevices.add(new TotpDevice(jsonTotpDevice.getAsJsonObject()));
        }
        return totpDevices;
    }

    private ArrayList<LoginMethod> getParsedLoginMethods(JsonObject userData, ArrayList<String> validTenantIds) {
        JsonArray jsonLoginMethods = parseAndValidateField(userData, "loginMethods", ValueType.ARRAY_OF_OBJECT, true,
                JsonArray.class, ".");

        if (jsonLoginMethods == null) {
            return new ArrayList<>();
        }

        if (jsonLoginMethods.size() == 0) {
            errors.add("At least one loginMethod is required.");
            return new ArrayList<>();
        }

        Boolean hasPrimaryLoginMethod = false;

        ArrayList<LoginMethod> loginMethods = new ArrayList<>();
        for (JsonElement jsonLoginMethod : jsonLoginMethods) {
            JsonObject jsonLoginMethodObj = jsonLoginMethod.getAsJsonObject();

            if (validateJsonFieldType(jsonLoginMethodObj, "isPrimary", ValueType.BOOLEAN)) {
                if (jsonLoginMethodObj.get("isPrimary").getAsBoolean()) {
                    if (hasPrimaryLoginMethod) {
                        errors.add("No two loginMethods can have isPrimary as true.");
                    }
                    hasPrimaryLoginMethod = true;
                }
            }

            loginMethods.add(new LoginMethod(jsonLoginMethodObj));
        }

        return loginMethods;
    }

    @SuppressWarnings("unchecked")
    private <T> T parseAndValidateField(JsonObject jsonObject, String key, ValueType expectedType, boolean isRequired,
            Class<T> targetType, String errorSuffix) {
        if (jsonObject.has(key)) {
            if (validateJsonFieldType(jsonObject, key, expectedType)) {
                T value;
                switch (expectedType) {
                    case STRING:
                        value = (T) jsonObject.get(key).getAsString();
                        break;
                    case NUMBER:
                        value = (T) jsonObject.get(key).getAsNumber();
                        break;
                    case BOOLEAN:
                        Boolean boolValue = jsonObject.get(key).getAsBoolean();
                        value = (T) boolValue;
                        break;
                    case OBJECT:
                        value = (T) jsonObject.get(key).getAsJsonObject();
                        break;
                    case ARRAY_OF_OBJECT, ARRAY_OF_STRING:
                        value = (T) jsonObject.get(key).getAsJsonArray();
                        break;
                    default:
                        value = null;
                        break;
                }
                if (value != null) {
                    return targetType.cast(value);
                } else {
                    errors.add(key + " should be of type " + getTypeForErrorMessage(expectedType) + errorSuffix);
                }
            } else {
                errors.add(key + " should be of type " + getTypeForErrorMessage(expectedType) + errorSuffix);
            }
        } else if (isRequired) {
            errors.add(key + " is required" + errorSuffix);
        }
        return null;
    }

    public enum ValueType {
        STRING,
        NUMBER,
        BOOLEAN,
        OBJECT,
        ARRAY_OF_STRING,
        ARRAY_OF_OBJECT
    }

    private String getTypeForErrorMessage(ValueType type) {
        return switch (type) {
            case STRING -> "string";
            case NUMBER -> "number";
            case BOOLEAN -> "boolean";
            case OBJECT -> "object";
            case ARRAY_OF_STRING -> "array of string";
            case ARRAY_OF_OBJECT -> "array of object";
        };
    }

    private Boolean validateJsonFieldType(JsonObject jsonObject, String key, ValueType expectedType) {
        if (jsonObject.has(key)) {
            return switch (expectedType) {
                case STRING -> jsonObject.get(key).isJsonPrimitive() && jsonObject.getAsJsonPrimitive(key).isString();
                case NUMBER -> jsonObject.get(key).isJsonPrimitive() && jsonObject.getAsJsonPrimitive(key).isNumber();
                case BOOLEAN -> jsonObject.get(key).isJsonPrimitive() && jsonObject.getAsJsonPrimitive(key).isBoolean();
                case OBJECT -> jsonObject.get(key).isJsonObject();
                case ARRAY_OF_OBJECT, ARRAY_OF_STRING -> jsonObject.get(key).isJsonArray()
                        && validateArrayElements(jsonObject.getAsJsonArray(key), expectedType);
                default -> false;
            };
        }
        return false;
    }

    private boolean validateArrayElements(JsonArray array, ValueType expectedType) {
        List<JsonElement> elements = new ArrayList<>();
        array.forEach(elements::add);

        return switch (expectedType) {
            case ARRAY_OF_OBJECT -> elements.stream().allMatch(JsonElement::isJsonObject);
            case ARRAY_OF_STRING ->
                elements.stream().allMatch(el -> el.isJsonPrimitive() && el.getAsJsonPrimitive().isString());
            default -> false;
        };
    }

    public class TotpDevice {
        public String secretKey;
        public Number period;
        public Number skew;
        public String deviceName;

        public TotpDevice(JsonObject jsonTotpDevice) {
            this.secretKey = parseAndValidateField(jsonTotpDevice, "secretKey", ValueType.STRING, true, String.class,
                    " for a totp device.");
            this.period = parseAndValidateField(jsonTotpDevice, "period", ValueType.NUMBER, true, Number.class,
                    " for a totp device.");
            this.skew = parseAndValidateField(jsonTotpDevice, "skew", ValueType.NUMBER, true, Number.class,
                    " for a totp device.");
            this.deviceName = parseAndValidateField(jsonTotpDevice, "deviceName", ValueType.STRING, false, String.class,
                    " for a totp device.");
        }
    }

    public class LoginMethod {
        public String tenantId;
        public Boolean isVerified;
        public Boolean isPrimary;
        public long timeJoinedInMSSinceEpoch;
        public String recipeId;

        public EmailPasswordLoginMethod emailPasswordLoginMethod;
        public ThirdPartyLoginMethod thirdPartyLoginMethod;
        public PasswordlessLoginMethod passwordlessLoginMethod;

        public LoginMethod(JsonObject jsonLoginMethod) {
            this.recipeId = parseAndValidateField(jsonLoginMethod, "recipeId", ValueType.STRING, true, String.class,
                    " for a loginMethod.");
            this.tenantId = parseAndValidateField(jsonLoginMethod, "tenantId", ValueType.STRING, false, String.class,
                    " for a loginMethod.");
            this.isVerified = parseAndValidateField(jsonLoginMethod, "isVerified", ValueType.BOOLEAN, false,
                    Boolean.class, " for a loginMethod.");
            this.isPrimary = parseAndValidateField(jsonLoginMethod, "isPrimary", ValueType.BOOLEAN, false,
                    Boolean.class, " for a loginMethod.");
            Number timeJoined = parseAndValidateField(jsonLoginMethod, "timeJoinedInMSSinceEpoch", ValueType.NUMBER,
                    false, Number.class, " for a loginMethod");
            this.timeJoinedInMSSinceEpoch = timeJoined != null ? timeJoined.longValue() : 0;

            if ("emailpassword".equals(this.recipeId)) {
                this.emailPasswordLoginMethod = new EmailPasswordLoginMethod(jsonLoginMethod);
            } else if ("thirdparty".equals(this.recipeId)) {
                this.thirdPartyLoginMethod = new ThirdPartyLoginMethod(jsonLoginMethod);
            } else if ("passwordless".equals(this.recipeId)) {
                this.passwordlessLoginMethod = new PasswordlessLoginMethod(jsonLoginMethod);
            } else if (this.recipeId != null) {
                errors.add(
                        "Invalid recipeId for loginMethod. Pass one of emailpassword, thirdparty or, passwordless!");
            }
        }

        public class EmailPasswordLoginMethod {
            public String email;
            public String passwordHash;
            public String hashingAlgorithm;

            public EmailPasswordLoginMethod(JsonObject jsonLoginMethod) {
                this.email = parseAndValidateField(jsonLoginMethod, "email", ValueType.STRING, true, String.class,
                        " for an emailpassword recipe.");
                this.passwordHash = parseAndValidateField(jsonLoginMethod, "passwordHash", ValueType.STRING, true,
                        String.class, " for an emailpassword recipe.");
                this.hashingAlgorithm = parseAndValidateField(jsonLoginMethod, "hashingAlgorithm", ValueType.STRING,
                        true, String.class, " for an emailpassword recipe.");

                if (this.hashingAlgorithm != null && !Arrays.asList("bcrypt", "argon2", "firebase_scrypt").contains(hashingAlgorithm)) {
                    errors.add(
                            "Invalid hashingAlgorithm for emailpassword recipe. Pass one of bcrypt, argon2 or, firebase_scrypt!");
                }
            }
        }

        public class ThirdPartyLoginMethod {
            public String email;
            public String thirdPartyId;
            public String thirdPartyUserId;

            public ThirdPartyLoginMethod(JsonObject jsonObject) {
                this.email = parseAndValidateField(jsonObject, "email", ValueType.STRING, true, String.class,
                        " for a thirdparty recipe.");
                this.thirdPartyId = parseAndValidateField(jsonObject, "thirdPartyId", ValueType.STRING, true,
                        String.class, " for a thirdparty recipe.");
                this.thirdPartyUserId = parseAndValidateField(jsonObject, "thirdPartyUserId", ValueType.STRING, true,
                        String.class, " for a thirdparty recipe.");
            }
        }

        public class PasswordlessLoginMethod {
            public String email;
            public String phoneNumber;

            public PasswordlessLoginMethod(JsonObject jsonObject) {
                this.email = parseAndValidateField(jsonObject, "email", ValueType.STRING, false, String.class,
                        " for a passwordless recipe.");
                this.phoneNumber = parseAndValidateField(jsonObject, "phoneNumber", ValueType.STRING, false,
                        String.class, " for a passwordless recipe.");

                if ((email != null && email.isEmpty()) && (phoneNumber != null && phoneNumber.isEmpty())) {
                    errors.add(
                            "Either email or phoneNumber is required for a passwordless recipe.");
                }
            }
        }
    }
}
