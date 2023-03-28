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

    public boolean shouldEmailPasswordTableBeSearched(){
        
        if (this.emails == null){
            return false;
        }

        ArrayList<SUPPORTED_SEARCH_TAGS> tags = new ArrayList<>();
        tags.add(SUPPORTED_SEARCH_TAGS.EMAIL);

        return checkThatSearchFieldsAreNull(tags);
    }

    public boolean shouldThirdPartyTableBeSearched(){
        if (this.emails == null && this.providers == null){
            return false;
        }

        ArrayList<SUPPORTED_SEARCH_TAGS> tags = new ArrayList<>();
        tags.add(SUPPORTED_SEARCH_TAGS.EMAIL);
        tags.add(SUPPORTED_SEARCH_TAGS.PROVIDER);

        return checkThatSearchFieldsAreNull(tags);
    }

    public boolean shouldPasswordlessTableBeSearched(){
        if (this.emails == null && this.phoneNumbers == null){
            return false;
        }

        ArrayList<SUPPORTED_SEARCH_TAGS> tags = new ArrayList<>();
        tags.add(SUPPORTED_SEARCH_TAGS.EMAIL);
        tags.add(SUPPORTED_SEARCH_TAGS.PHONE);

        return checkThatSearchFieldsAreNull(tags);
    }


    // checks that all fields of the current instance are null, ignores checks for fields set in the input
    private boolean checkThatSearchFieldsAreNull(ArrayList<SUPPORTED_SEARCH_TAGS> tagsToIgnore) {

        if (!tagsToIgnore.contains(SUPPORTED_SEARCH_TAGS.EMAIL) && this.emails != null) {
            return false;
        }

        if (!tagsToIgnore.contains(SUPPORTED_SEARCH_TAGS.PROVIDER) && this.providers != null) {
            return false;
        }

        if (!tagsToIgnore.contains(SUPPORTED_SEARCH_TAGS.PHONE) && this.phoneNumbers != null) {
            return false;
        }

        return true;
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
