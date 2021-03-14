package uwu.iloveutoo.neko.emitter.impl;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.emitter.EndpointEmitter;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.model.emitter.EndpointImage;
import uwu.iloveutoo.neko.parser.ResponseParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class ImageEmitter implements EndpointEmitter<EndpointImage> {

    /* Fields */

    private final OkHttpClient client;


    /* Constructor */

    public ImageEmitter(OkHttpClient client) {
        this.client = client;
    }

    /* Implements */

    @NotNull
    @Override
    public CompletableFuture<EndpointImage> emit(Endpoint endpoint, ResponseParser.Result result) throws MalformedURLException {
        URL url = new URL(result.getDataAsString());
        Request request = new Request.Builder()
                .url(url)
                .build();

        CompletableFuture<EndpointImage> future = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if(body == null) {
                    return;
                }
                EndpointImage endpointImage = new EndpointImage(endpoint, url, new BufferedInputStream(body.byteStream()));
                future.complete(endpointImage);
            }
        });
        return future;
    }

    /* Overrides */

    /* Methods */

}
