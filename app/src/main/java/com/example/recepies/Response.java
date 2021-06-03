package com.example.recepies;

public class Response {
    int count;
    Hit[] hits;

    @Override
    public String toString() {
        String result =
         "totalHits = " + count;
        for (int i = 0; i < hits.length; i++) {
             result+=hits[i]+"\n";
        }
        return  result;
    }
}
