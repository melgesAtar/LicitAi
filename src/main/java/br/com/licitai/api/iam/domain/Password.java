package br.com.licitai.api.iam.domain;

public final class Password {

    private final String hash;

    private Password(String hash) {
        this.hash = hash;
    }

    public static Password fromHash(String hash) {
        return new Password(hash);
    }

    public String getHash() {
        return hash;
    }

}