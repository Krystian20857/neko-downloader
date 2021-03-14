package uwu.iloveutoo.neko.parser.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.parser.ResponseParser;
import uwu.iloveutoo.neko.util.Util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleResponseParser implements ResponseParser {

    /* Fields */

    private final ExecutorService executor = Executors.newCachedThreadPool(Util.namedFactory("response-processing-pool-%d"));
    private final OkHttpClient client;

    /* Constructor */

    public SimpleResponseParser(OkHttpClient client){
        this.client = client;
    }

    /* Implements */

    @NotNull
    @Override
    public CompletableFuture<Result> downloadEndpointAsync(Endpoint endpoint) {
        return this.getResponse(endpoint)
                .thenApplyAsync(response -> {
                    ResponseBody body = response.body();
                    if (body == null) {
                        return Result.EMPTY;
                    }
                    String stringBody = Util.getResponseAsString(body);
                    JsonObject jsonObject = JsonParser.parseString(stringBody).getAsJsonObject();

                    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        return new Result(endpoint, entry.getValue());
                    }
                    return Result.EMPTY;
                }, executor);
    }

    /* Overrides */

    /* Methods */

    private CompletableFuture<Response> getResponse(Endpoint endpoint){
        Request request = new Request.Builder()
                .url(Endpoint.buildUrl(endpoint))
                .build();

        CompletableFuture<Response> result = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                result.complete(response);
            }
        });
        return result;
    }

}
