package com.http.pet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.ApiResponse;
import com.model.Pet;
import com.util.Util;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HttpClientPet {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static Pet sendGet(String uri, Long petId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + petId))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static Pet sendPost(String uri, Pet pet) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(pet)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static ApiResponse sendPostImage(String uri, Long id, Path file) throws IOException, InterruptedException {
        Map<Object, Object> data = new LinkedHashMap<>();
        data.put("petId", id);
        data.put("additionalMetadata", "additionalMetadata");
        data.put("file", file);

        String boundary = new BigInteger(256, new Random()).toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + id + "/uploadImage"))
                .header("Content-type", "multipart/form-data;boundary=" + boundary)
                .POST(ofMimeMultipartData(data, boundary))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static Pet sendPut(String uri, Pet pet) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(pet)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static List<Pet> sendGetWithListOfResults(String uri, String[] queries) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Util.getQueryURI(uri, queries)))
                .header("Content-type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Pet>>(){}.getType());
    }

    public static ApiResponse sendUpdate(String uri, Long id, String name, String status) throws IOException, InterruptedException {
        Pet petToUpdate = sendGet(uri, id);
        petToUpdate.setName(name);
        petToUpdate.setStatus(status);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + id))
                .header("Content-type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(petToUpdate)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static ApiResponse sendDelete(String uri, Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + id))
                .header("Content-type", "application/x-www-form-urlencoded")
                .DELETE()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), ApiResponse.class);
    }



    // !!!This method is from stackoverflow!!!
    private static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data,
                                                                 String boundary) throws IOException {
        // Result request body
        List<byte[]> byteArrays = new ArrayList<>();

        // Separator with boundary
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);

        // Iterating over data parts
        for (Map.Entry<Object, Object> entry : data.entrySet()) {

            // Opening boundary
            byteArrays.add(separator);

            // If value is type of Path (file) append content type with file name and file binaries, otherwise simply append key=value
            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }
        }

        // Closing boundary
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));

        // Serializing as byte array
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
