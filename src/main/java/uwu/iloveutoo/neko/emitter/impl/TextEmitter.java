package uwu.iloveutoo.neko.emitter.impl;

import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.emitter.EndpointEmitter;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.model.emitter.EndpointText;
import uwu.iloveutoo.neko.parser.ResponseParser;

import java.util.concurrent.CompletableFuture;

public class TextEmitter implements EndpointEmitter<EndpointText> {

    /* Fields */

    /* Constructor */

    /* Implements */

    @NotNull
    @Override
    public CompletableFuture<EndpointText> emit(Endpoint endpoint, ResponseParser.Result result) {
        EndpointText endpointText = new EndpointText(endpoint, result.getDataAsString());
        return CompletableFuture.completedFuture(endpointText);
    }

    /* Overrides */

    /* Methods */

}
