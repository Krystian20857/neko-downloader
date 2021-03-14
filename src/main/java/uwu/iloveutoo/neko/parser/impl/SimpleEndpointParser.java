package uwu.iloveutoo.neko.parser.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.model.ContentType;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.parser.EndpointParser;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleEndpointParser implements EndpointParser {

    /* Fields */

    /* Constructor */

    /* Implements */

    @NotNull
    @Override
    public Collection<Endpoint> getEndpoints(JsonObject json, ContentType type) {
        switch (type) {

            case ANY: {
                return Arrays.stream(ContentType.values())
                        .filter(newType -> newType != ContentType.ANY)
                        .map(newType -> getEndpointList(json, newType))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            }

            default: {
                return getEndpointList(json, type);
            }

        }
    }

    /* Overrides */

    /* Methods */

    private Collection<Endpoint> getEndpointList(JsonObject json, ContentType type){
        JsonObject targetObject = json.getAsJsonObject(type.getAlias());
        if(targetObject == null) {
            return Collections.emptyList();
        }

        final List<Endpoint> endpoints = new LinkedList<>();
        for (Map.Entry<String, JsonElement> entry : targetObject.entrySet()) {
            String name = entry.getKey();
            JsonElement element = entry.getValue();
            Endpoint endpoint = Endpoint.formJson(name, element, type);
            endpoints.add(endpoint);
        }

        return endpoints;
    }


}
