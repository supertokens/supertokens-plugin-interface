package io.supertokens.pluginInterface.dashboard;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class DashboardSearchTags {

    public ArrayList<String> emails;
    public ArrayList<String> phoneNumbers;
    public ArrayList<String> providers;
    public ArrayList<String> recipeIds;

    public DashboardSearchTags(@Nullable ArrayList<String> emails, @Nullable ArrayList<String> phones, @Nullable ArrayList<String> providers,
            @Nullable ArrayList<String> recipeIds) {
        this.emails = emails;
        this.phoneNumbers = phones;
        this.providers = providers;
        this.recipeIds = recipeIds;
    }

}
