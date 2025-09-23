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
    public final String ssoLoginURL;
    public final JsonArray redirectURIs;
    public final String defaultRedirectURI;
    public final String spEntityId;
    public final String idpEntityId;
    public final String idpSigningCertificate;

    public SAMLClient(String clientId, String ssoLoginURL, JsonArray redirectURIs, String defaultRedirectURI, String spEntityId, String idpEntityId, String idpSigningCertificate) {
        this.clientId = clientId;
        this.ssoLoginURL = ssoLoginURL;
        this.redirectURIs = redirectURIs;
        this.defaultRedirectURI = defaultRedirectURI;
        this.spEntityId = spEntityId;
        this.idpEntityId = idpEntityId;
        this.idpSigningCertificate = idpSigningCertificate;
    }

    public JsonObject toJson() {
        JsonObject res = new JsonObject();

        res.addProperty("clientId", this.clientId);
        res.addProperty("defaultRedirectURI", this.defaultRedirectURI);
        res.add("redirectURIs", redirectURIs);

        if (this.spEntityId != null) {
            res.addProperty("spEntityId", spEntityId);
        }

        return res;
    }
}
