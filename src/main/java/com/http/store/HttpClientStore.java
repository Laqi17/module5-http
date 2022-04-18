package com.http.store;

import com.exceptions.CustomException;
import com.google.gson.Gson;
import com.model.ApiResponse;
import com.model.Order;
import com.model.Pet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientStore {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static Order sendPost(String uri, Order order) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(order)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), Order.class);
    }

    public static Order sendGet(String uri, Long id) throws IOException, InterruptedException, CustomException {
        if (id<0||id>10){
            System.out.println("For valid response try integer IDs with value >= 1 and <= 10.");
            return null;
        }
        else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + id))
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return GSON.fromJson(response.body(), Order.class);
            }
            else throw new CustomException("There no this id");
        }
    }

    public static ApiResponse sendDelete(String uri, Long id) throws IOException, InterruptedException {
        if (id < 0) {
            System.out.println("For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors");
            return null;
        } else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + id))
                    .header("Content-type", "application/x-www-form-urlencoded")
                    .DELETE()
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return GSON.fromJson(response.body(), ApiResponse.class);
        }
    }
}
