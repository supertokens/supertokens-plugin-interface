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
 *
 */

package io.supertokens.pluginInterface.saml;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SAMLClient {
    public final String clientId;
    public final String clientSecret;
    public final String ssoLoginURL;
    public final JsonArray redirectURIs;
    public final String defaultRedirectURI;
    public final String idpEntityId;
    public final String idpSigningCertificate;
    public final boolean allowIDPInitiatedLogin;
    public final boolean enableRequestSigning;
    // Nullable allow-list of ACS URLs. null = not configured (IdP-initiated Recipient pinning and
    // SP-initiated acsURL validation are skipped, preserving legacy behaviour). See GHSA-3r7x follow-up.
    public final JsonArray acsURLs;
    // Nullable mapping from userId / email / firstName / lastName to ordered lists of assertion claim names.
    // null = not configured.
    public final JsonObject attributeMapping;

    public SAMLClient(String clientId, String clientSecret, String ssoLoginURL, JsonArray redirectURIs, String defaultRedirectURI, String idpEntityId, String idpSigningCertificate, boolean allowIDPInitiatedLogin, boolean enableRequestSigning, JsonArray acsURLs) {
        this(clientId, clientSecret, ssoLoginURL, redirectURIs, defaultRedirectURI, idpEntityId,
                idpSigningCertificate, allowIDPInitiatedLogin, enableRequestSigning, acsURLs, null);
    }

    public SAMLClient(String clientId, String clientSecret, String ssoLoginURL, JsonArray redirectURIs,
                      String defaultRedirectURI, String idpEntityId, String idpSigningCertificate,
                      boolean allowIDPInitiatedLogin, boolean enableRequestSigning, JsonArray acsURLs,
                      JsonObject attributeMapping) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.ssoLoginURL = ssoLoginURL;
        this.redirectURIs = redirectURIs;
        this.defaultRedirectURI = defaultRedirectURI;
        this.idpEntityId = idpEntityId;
        this.idpSigningCertificate = idpSigningCertificate;
        this.allowIDPInitiatedLogin = allowIDPInitiatedLogin;
        this.enableRequestSigning = enableRequestSigning;
        // An empty stored value means "not configured", so it reads back as null and is left out of toJson().
        this.acsURLs = acsURLs == null || acsURLs.isEmpty() ? null : acsURLs;
        this.attributeMapping = attributeMapping == null || attributeMapping.isEmpty() ? null : attributeMapping;
    }

    public JsonObject toJson() {
        JsonObject res = new JsonObject();

        res.addProperty("clientId", this.clientId);
        if (this.clientSecret != null) {
            res.addProperty("clientSecret", this.clientSecret);
        }
        res.addProperty("defaultRedirectURI", this.defaultRedirectURI);
        res.add("redirectURIs", redirectURIs);
        res.addProperty("idpEntityId", this.idpEntityId);
        if (this.idpSigningCertificate != null) {
            res.addProperty("idpSigningCertificate", this.idpSigningCertificate);
        }
        res.addProperty("allowIDPInitiatedLogin", this.allowIDPInitiatedLogin);
        res.addProperty("enableRequestSigning", this.enableRequestSigning);
        if (this.acsURLs != null) {
            res.add("acsURLs", this.acsURLs);
        }
        if (this.attributeMapping != null) {
            res.add("attributeMapping", this.attributeMapping);
        }

        return res;
    }
}
