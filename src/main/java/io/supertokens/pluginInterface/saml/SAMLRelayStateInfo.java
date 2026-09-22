/*
 *    Copyright (c) 2025, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.saml;

public class SAMLRelayStateInfo {
    public final String relayState;
    public final String clientId;
    public final String state;
    public final String redirectURI;
    // The ID of the AuthnRequest issued for this login. The IdP echoes it back in the
    // SAML Response as InResponseTo; the callback binds the response to this login by
    // requiring the two to match. Null only for rows written before this column existed.
    public final String inResponseTo;
    // The AssertionConsumerService URL this login was started with. The callback pins the
    // response Destination / SubjectConfirmationData Recipient to it, so an assertion minted
    // for a different endpoint cannot be redirected here. Null for pre-existing rows.
    public final String acsURL;

    public SAMLRelayStateInfo(String relayState, String clientId, String state, String redirectURI,
                              String inResponseTo, String acsURL) {
        this.relayState = relayState;
        this.clientId = clientId;
        this.state = state;
        this.redirectURI = redirectURI;
        this.inResponseTo = inResponseTo;
        this.acsURL = acsURL;
    }
}
