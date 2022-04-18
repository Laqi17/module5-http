package com.util;

public class Util {
    public static String getQueryURI(String uri, String[] queries) {
        StringBuilder stringBuilder = new StringBuilder(uri);
        if (queries.length == 0) {
            return uri;
        }
        else {
            stringBuilder.append("?");
            for (String query: queries) {
                stringBuilder.append("status=");
                stringBuilder.append(query.equals(queries[queries.length - 1]) ? query : query + "&");
            }
        }
        return stringBuilder.toString();
    }

}

