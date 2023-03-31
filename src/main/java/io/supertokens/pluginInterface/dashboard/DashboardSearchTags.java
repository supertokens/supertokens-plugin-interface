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

    public boolean shouldEmailPasswordTableBeSearched() {

        ArrayList<SUPPORTED_SEARCH_TAGS> nonNullSearchTags = getNonNullSearchFields();
        return nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.EMAIL) && nonNullSearchTags.size() == 1;

    }

    public boolean shouldThirdPartyTableBeSearched() {
        
        ArrayList<SUPPORTED_SEARCH_TAGS> nonNullSearchTags = getNonNullSearchFields();
        if(nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.EMAIL) && nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.PROVIDER)){
            return nonNullSearchTags.size() == 2;
        }

        if(nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.EMAIL) || nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.PROVIDER)){
            return nonNullSearchTags.size() == 1;
        }

        return false;
    }

    public boolean shouldPasswordlessTableBeSearched() {
        ArrayList<SUPPORTED_SEARCH_TAGS> nonNullSearchTags = getNonNullSearchFields();
        if(nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.EMAIL) && nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.PHONE)){
            return nonNullSearchTags.size() == 2;
        }

        if(nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.EMAIL) || nonNullSearchTags.contains(SUPPORTED_SEARCH_TAGS.PHONE)){
            return nonNullSearchTags.size() == 1;
        }

        return false;
    }

    private ArrayList<SUPPORTED_SEARCH_TAGS> getNonNullSearchFields() {
        ArrayList<SUPPORTED_SEARCH_TAGS> nonNullSearchTags = new ArrayList<>();

        if (this.emails != null) {
            nonNullSearchTags.add(SUPPORTED_SEARCH_TAGS.EMAIL);
        }

        if (this.phoneNumbers != null) {
            nonNullSearchTags.add(SUPPORTED_SEARCH_TAGS.PHONE);
        }

        if (this.providers != null) {
            nonNullSearchTags.add(SUPPORTED_SEARCH_TAGS.PROVIDER);
        }

        return nonNullSearchTags;
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
