package io.supertokens.pluginInterface.thirdparty;

public class ThirdPartyTenantConfig {

    public final String supertokensTenantId;

    public final String thirdPartyId;

    public final String config;

    public final boolean wasCreated;
    
    public ThirdPartyTenantConfig(String supertokensTenantId, String thirdPartyId, String config, Boolean wasCreated){
        this.supertokensTenantId = supertokensTenantId;
        this.thirdPartyId = thirdPartyId;
        this.config = config;
        this.wasCreated = wasCreated;
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
