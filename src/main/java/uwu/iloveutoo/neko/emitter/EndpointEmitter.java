package uwu.iloveutoo.neko.emitter;

import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.model.emitter.EndpointModel;
import uwu.iloveutoo.neko.parser.ResponseParser;

import java.util.concurrent.CompletableFuture;

public interface EndpointEmitter<V extends EndpointModel> {

    @NotNull
    CompletableFuture<V> emit(Endpoint endpoint, ResponseParser.Result result) throws Throwable;

}
