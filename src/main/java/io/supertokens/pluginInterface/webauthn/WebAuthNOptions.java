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

package io.supertokens.pluginInterface.webauthn;

public class WebAuthNOptions {

    public String generatedOptionsId;
    public String relyingPartyId;
    public String relyingPartyName;
    public String userEmail;
    public Long timeout;
    public String challenge; //base64 url encoded
    public String origin;
    public Long expiresAt;
    public Long createdAt;
    public boolean userPresenceRequired;
    public String userVerification;
}
