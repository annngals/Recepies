package com.example.recepies;

public class Response {
    int count;
    Hit[] hits;

    @Override
    public String toString() {
        return "totalHits = " + count;
    }
}
