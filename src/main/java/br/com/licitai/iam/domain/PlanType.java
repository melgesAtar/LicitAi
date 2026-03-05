package br.com.licitai.iam.domain;

public enum PlanType {
    FREE(1,1,1,false,false,1440),
    PRO(3,Integer.MAX_VALUE, 3,true,true,60),
    BUSINESS(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, true, true, 15);

    private final int maxCompanies;
    private final int maxProfilesPerAccount;
    private final int maxUsers;
    private final boolean allowsScore;
    private final boolean allowsHighlights;
    private final int updateIntervalMinutes;


    PlanType(int maxCompanies,
             int maxProfilesPerAccount,
             int maxUsers,
             boolean allowsScore,
             boolean allowsHighlights,
             int updateIntervalMinutes) {
        this.maxCompanies = maxCompanies;
        this.maxProfilesPerAccount = maxProfilesPerAccount;
        this.maxUsers = maxUsers;
        this.allowsScore = allowsScore;
        this.allowsHighlights = allowsHighlights;
        this.updateIntervalMinutes = updateIntervalMinutes;
    }

    public int getMaxCompanies() {
        return maxCompanies;
    }

    public int getMaxProfilesPerAccount() {
        return maxProfilesPerAccount;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public boolean isAllowsScore() {
        return allowsScore;
    }

    public boolean isAllowsHighlights() {
        return allowsHighlights;
    }

    public int getUpdateIntervalMinutes() {
        return updateIntervalMinutes;
    }
}
