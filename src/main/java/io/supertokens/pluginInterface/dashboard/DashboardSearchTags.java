package io.supertokens.pluginInterface.dashboard;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class DashboardSearchTags {

    public ArrayList<String> emails;
    public ArrayList<String> phoneNumbers;
    public ArrayList<String> providers;

    public DashboardSearchTags(@Nullable ArrayList<String> emails, @Nullable ArrayList<String> phones,
            @Nullable ArrayList<String> providers) {
        this.emails = emails;
        this.phoneNumbers = phones;
        this.providers = providers;
    }

    public boolean shouldRecipeBeSearched(ArrayList<SUPPORTED_SEARCH_TAGS> tags){

        boolean resultCondition = false;

        if(tags.contains(SUPPORTED_SEARCH_TAGS.EMAIL)){
            resultCondition = resultCondition || (this.emails != null);
        } else {
            resultCondition = resultCondition && (this.emails == null);
        }

        if(tags.contains(SUPPORTED_SEARCH_TAGS.PROVIDER)){
            resultCondition = resultCondition || (this.providers != null);
        } else {
            resultCondition = resultCondition && (this.providers == null);
        }

        if(tags.contains(SUPPORTED_SEARCH_TAGS.PHONE)){
            resultCondition = resultCondition || (this.phoneNumbers != null);
        } else {
            resultCondition = resultCondition && (this.phoneNumbers == null);
        }

        return resultCondition;
    }

    public enum SUPPORTED_SEARCH_TAGS {
        EMAIL("email"), PHONE("phone"), PROVIDER("provider");

        private String tag;

        SUPPORTED_SEARCH_TAGS(String tag) {
            this.tag = tag;
        }

        public static SUPPORTED_SEARCH_TAGS fromString(String text) {
            for (SUPPORTED_SEARCH_TAGS t : SUPPORTED_SEARCH_TAGS.values()) {
                if (t.tag.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return tag;
        }
    }

}
