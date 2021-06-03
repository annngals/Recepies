package com.example.recepies;

public class Hit {
    int id;
    String previewURL;
    String label;

    @Override
    public String toString() {
        return "Hit{" +
                "id=" + id +
                ", previewURL='" + previewURL + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
