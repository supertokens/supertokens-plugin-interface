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

package io.supertokens.pluginInterface.thirdparty;

public class ThirdPartyTenantConfig {

    public final String supertokensTenantId;

    public final String thirdPartyId;

    public final String config;
    
    public ThirdPartyTenantConfig(String supertokensTenantId, String thirdPartyId, String config){
        this.supertokensTenantId = supertokensTenantId;
        this.thirdPartyId = thirdPartyId;
        this.config = config;
    }

    public boolean equals(Object other){
        if(other instanceof ThirdPartyTenantConfig){
            ThirdPartyTenantConfig otherThirdPartyTenantConfig = (ThirdPartyTenantConfig) other;
            return otherThirdPartyTenantConfig.supertokensTenantId.equals(this.supertokensTenantId)
                    && otherThirdPartyTenantConfig.thirdPartyId.equals(otherThirdPartyTenantConfig.thirdPartyId) 
                    && otherThirdPartyTenantConfig.config.equals(otherThirdPartyTenantConfig.config);
        }

        return false;
    }
}
