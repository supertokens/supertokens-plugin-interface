/*
 *    Copyright (c) 2026, VRAI Labs and/or its affiliates. All rights reserved.
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SAMLClientTest {

    private static JsonArray array(String... values) {
        JsonArray res = new JsonArray();
        for (String value : values) {
            res.add(value);
        }
        return res;
    }

    private static JsonObject mapping() {
        JsonObject res = new JsonObject();
        res.add("userId", array("id", "NameID"));
        res.add("email", array("email"));
        return res;
    }

    private static SAMLClient client(JsonArray acsURLs) {
        return new SAMLClient("client", "secret", "https://idp.example.com/sso", array("https://app.example.com/cb"),
                "https://app.example.com/cb", "idp-entity", "cert", false, false, acsURLs);
    }

    private static SAMLClient client(JsonArray acsURLs, JsonObject attributeMapping) {
        return new SAMLClient("client", "secret", "https://idp.example.com/sso", array("https://app.example.com/cb"),
                "https://app.example.com/cb", "idp-entity", "cert", false, false, acsURLs, attributeMapping);
    }

    @Test
    public void existingConstructorLeavesAttributeMappingUnset() {
        SAMLClient client = client(array("https://app.example.com/acs"));

        assertNull(client.attributeMapping);
        assertFalse(client.toJson().has("attributeMapping"));
        assertEquals(array("https://app.example.com/acs"), client.toJson().get("acsURLs"));
    }

    @Test
    public void toJsonIncludesAttributeMappingWhenSet() {
        SAMLClient client = client(null, mapping());

        assertEquals(mapping(), client.attributeMapping);
        assertEquals(mapping(), client.toJson().get("attributeMapping"));
    }

    @Test
    public void nullAttributeMappingIsOmittedFromToJson() {
        SAMLClient client = client(null, null);

        assertNull(client.attributeMapping);
        assertFalse(client.toJson().has("attributeMapping"));
        assertFalse(client.toJson().has("acsURLs"));
    }

    @Test
    public void emptyAttributeMappingReadsBackAsUnset() {
        SAMLClient client = client(null, new JsonObject());

        assertNull(client.attributeMapping);
        assertFalse(client.toJson().has("attributeMapping"));
    }

    @Test
    public void emptyAcsURLsReadsBackAsUnset() {
        assertNull(client(new JsonArray()).acsURLs);
        assertFalse(client(new JsonArray()).toJson().has("acsURLs"));
        assertNull(client(new JsonArray(), mapping()).acsURLs);
        assertFalse(client(new JsonArray(), mapping()).toJson().has("acsURLs"));
    }

    @Test
    public void toJsonIsUnchangedForAClientWithoutAMapping() {
        JsonObject json = client(array("https://app.example.com/acs")).toJson();

        JsonObject expected = new JsonObject();
        expected.addProperty("clientId", "client");
        expected.addProperty("clientSecret", "secret");
        expected.addProperty("defaultRedirectURI", "https://app.example.com/cb");
        expected.add("redirectURIs", array("https://app.example.com/cb"));
        expected.addProperty("idpEntityId", "idp-entity");
        expected.addProperty("idpSigningCertificate", "cert");
        expected.addProperty("allowIDPInitiatedLogin", false);
        expected.addProperty("enableRequestSigning", false);
        expected.add("acsURLs", array("https://app.example.com/acs"));
        assertEquals(expected, json);
        assertTrue(json.has("acsURLs"));
    }
}
