package io.supertokens.pluginInterface;

public enum ACCOUNT_INFO_TYPE {
    EMAIL("email"), PHONE_NUMBER("phone"), THIRD_PARTY("tparty");

    private final String name;

    ACCOUNT_INFO_TYPE(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
