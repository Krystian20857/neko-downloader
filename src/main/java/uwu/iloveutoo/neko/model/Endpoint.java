package uwu.iloveutoo.neko.model;

import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Endpoint {

    /* Fields */

    public static final URI NEKO_API_URL = URI.create("https://nekos.life/api/v2");

    private final String name;
    private final String url;
    private final ContentType contentType;

    /* Constructor */

    /* Implements */

    /* Overrides */

    @Override
    public String toString() {
        return String.format("%s[%s]", name, contentType.name());
    }

    /* Methods */

    @NotNull
    public ResponseType getResponseType(){
        if (this.url.startsWith("/img/")) {
            return ResponseType.IMAGE;
        }

        return ResponseType.TEXT;
    }

    public static Endpoint formJson(String name, JsonElement element, ContentType contentType) {
        return new Endpoint(name, element.getAsString(), contentType);
    }

    public static URL buildUrl(Endpoint endpoint){
        try {
            return new URL(NEKO_API_URL.toString() + endpoint.getUrl());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Cannot create URL from endpoint: " + endpoint);
        }
    }

}
