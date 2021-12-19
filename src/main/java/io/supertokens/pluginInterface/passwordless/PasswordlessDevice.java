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

package io.supertokens.pluginInterface.passwordless;

public class PasswordlessDevice {
    public final String deviceIdHash;
    public final String email;
    public final String phoneNumber;
    public final String linkCodeSalt;
    public final int failedAttempts;

    public PasswordlessDevice(String deviceIdHash, String email, String phoneNumber, String linkCodeSalt,
            int failedAttempts) {
        this.deviceIdHash = deviceIdHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.failedAttempts = failedAttempts;
        this.linkCodeSalt = linkCodeSalt;
    }
}
