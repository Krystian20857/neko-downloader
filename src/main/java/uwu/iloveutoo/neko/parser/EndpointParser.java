package uwu.iloveutoo.neko.parser;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.model.ContentType;
import uwu.iloveutoo.neko.model.Endpoint;

import java.util.Collection;

public interface EndpointParser {

    @NotNull
    Collection<Endpoint> getEndpoints(JsonObject json, ContentType contentType);


}
