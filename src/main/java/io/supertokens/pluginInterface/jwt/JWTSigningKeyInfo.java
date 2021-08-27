/*
 *    Copyright (c) 2021, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.jwt;

public class JWTSigningKeyInfo {
    public String keyId;
    public long createdAtTime;
    public String algorithm;
    private String keyString;

    public JWTSigningKeyInfo(String keyId, long createdAtTime, String algorithm, String keyString) {
        this.keyId = keyId;
        this.createdAtTime = createdAtTime;
        this.algorithm = algorithm;
        this.keyString = keyString;
    }

    public String getKeyString() {
        return this.keyString;
    }

    public JWTAsymmetricSigningKeyInfo getAsAsymmetric() {
        return JWTAsymmetricSigningKeyInfo.withKeyString(this.keyId, this.createdAtTime, this.algorithm, this.keyString);
    }

    public JWTSymmetricSigningKeyInfo getAsSymmetric() {
        return new JWTSymmetricSigningKeyInfo(this.keyId, this.createdAtTime, this.algorithm, this.keyString);
    }
}
