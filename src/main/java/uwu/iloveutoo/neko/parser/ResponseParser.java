package uwu.iloveutoo.neko.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import uwu.iloveutoo.neko.model.Endpoint;
import uwu.iloveutoo.neko.util.Util;

import java.util.concurrent.CompletableFuture;

public interface ResponseParser {

    @NotNull
    CompletableFuture<Result> downloadEndpointAsync(Endpoint endpoint);

    @RequiredArgsConstructor
    @Getter
    public static class Result {

        /* Fields */

        public static final Result EMPTY = new Result(null, null);

        private final Endpoint endpoint;
        private final Object data;

        /* Methods */

        public static boolean isEmpty(Result result){
            return result == Result.EMPTY;
        }

        public String getDataAsString(){
            return Util.getAsString(this.data);
        }

    }

}
