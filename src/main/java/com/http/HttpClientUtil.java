package com.http;

import com.google.gson.Gson;
import com.model.Pet;
import org.json.JSONObject;

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

public class HttpClientUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static Pet sendGet(URI uri, Integer petId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri.toString() + petId))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static Pet sendPost(URI uri, Pet pet) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(pet)))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static String sendPostImage(URI uri, Integer id, Path file) throws IOException, InterruptedException {
        JSONObject body = new JSONObject();
        body.put("petId", id.toString());
        body.put("additionalMetadata", "additionalMetadata");
        body.put("file", file);

        Map<Object, Object> data = new LinkedHashMap<>();
        data.put("petId", id);
        data.put("additionalMetadata", "additionalMetadata");
        data.put("file", file);

        String boundary = new BigInteger(256, new Random()).toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri.toString() + id + "/uploadImage"))
                .header("Content-type", "multipart/form-data;boundary=" + boundary)
                .POST(ofMimeMultipartData(data, boundary))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data,
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
