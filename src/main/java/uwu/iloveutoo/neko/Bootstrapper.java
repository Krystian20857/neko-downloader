package uwu.iloveutoo.neko;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import uwu.iloveutoo.neko.app.AppSettings;
import uwu.iloveutoo.neko.emitter.EndpointEmitter;
import uwu.iloveutoo.neko.emitter.provider.OutputProviderRegister;
import uwu.iloveutoo.neko.emitter.provider.impl.ImageOutputProvider;
import uwu.iloveutoo.neko.emitter.provider.impl.TextOutputProvider;
import uwu.iloveutoo.neko.model.ContentType;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.model.emitter.EndpointImage;
import uwu.iloveutoo.neko.model.emitter.EndpointText;
import uwu.iloveutoo.neko.parser.EndpointParser;
import uwu.iloveutoo.neko.parser.ResponseParser;
import uwu.iloveutoo.neko.parser.impl.SimpleEndpointParser;
import uwu.iloveutoo.neko.parser.impl.SimpleResponseParser;
import uwu.iloveutoo.neko.util.Util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Bootstrapper {

    /* Fields */

    @Getter private static Bootstrapper instance;

    @Getter private final OkHttpClient okHttpClient = new OkHttpClient();
    @Getter private final AppSettings settings;
    @Getter private final EndpointParser parser;
    @Getter private final ResponseParser responseParser;
    @Getter private final Collection<Endpoint> endpoints;
    @Getter private final Collection<Endpoint> appEndpoints;

    private final JsonObject endpointsJson;

    /* Constructor */

    public Bootstrapper(AppSettings settings) {
        instance = this;
        this.settings = settings;
        this.parser = new SimpleEndpointParser();
        this.responseParser = new SimpleResponseParser(this.okHttpClient);

        OutputProviderRegister.register(EndpointImage.class,
                settings.isSaveImages() ?
                        new ImageOutputProvider(settings.getSaveDirectory())
                        : new ImageOutputProvider()
        );

        OutputProviderRegister.register(EndpointText.class, new TextOutputProvider());

        this.endpointsJson = getEndpointJson();
        this.endpoints = getEndpointsInternal();
        this.appEndpoints = getAppEndpointsInternal();
    }

    /* Implements */

    /* Overrides */

    /* Methods */

    private JsonObject getEndpointJson() {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("endpoints.json");
        if (stream == null) {
            stream = Util.getFileStream("./endpoints.json");
        }
        if (stream == null) {
            throw new IllegalStateException("Cannot find endpoints file");
        }
        return JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
    }

    private Collection<Endpoint> getAppEndpointsInternal(){
        Set<String> categories = this.settings.getCategories();
        if (categories.size() == 0) {
            return this.endpoints;
        }
        return this.endpoints.stream()
                .filter(endpoint -> categories.contains(endpoint.getName()))
                .collect(Collectors.toList());
    }

    private Collection<Endpoint> getEndpointsInternal() {
        return parser.getEndpoints(this.endpointsJson, this.settings.getContentType());
    }

    public Collection<Endpoint> getEndpoints(ContentType contentType) {
        return this.parser.getEndpoints(this.endpointsJson, contentType);
    }

    public void run(){
        int amount = this.settings.getAmount();
        IntStream.range(0, amount).forEach(n -> {
            this.run(this.appEndpoints);
        });
    }

    private void run(Collection<Endpoint> endpoints) {
        for (Endpoint endpoint : endpoints) {
            this.responseParser.downloadEndpointAsync(endpoint)
                    .thenAccept(this::handleResult);
        }
    }

    @SneakyThrows
    private void handleResult(ResponseParser.Result result) {
        EndpointEmitter<?> emitter = result.getEndpoint().getResponseType().getEmitter();
        emitter.emit(result.getEndpoint(), result).thenAccept(OutputProviderRegister::accept);
    }

}
