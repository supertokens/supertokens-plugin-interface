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

package io.supertokens.pluginInterface.useridmapping;

/**
 * Thrown when attempting to lock a user that does not exist.
 */
public class UserNotFoundForLockingException extends Exception {
    private static final long serialVersionUID = 1L;

    private final String userId;

    public UserNotFoundForLockingException(String userId) {
        super("User not found: " + userId);
        this.userId = userId;
    }

    public UserNotFoundForLockingException() {
        super("User not found");
        this.userId = null;
    }

    public String getUserId() {
        return userId;
    }
}
