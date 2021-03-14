package uwu.iloveutoo.neko.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
public enum ContentType {

    SFW("sfw"),
    NSFW("nsfw"),
    ANY("any")

    ;

    /* Fields */

    private static final Map<String, ContentType> byAlias = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @Getter
    private final String alias;

    /* Constructor */

    static {
        for (ContentType value : ContentType.values()) {
            byAlias.put(value.getAlias(), value);
        }
    }

    /* Methods */

    public static ContentType getByAlias(String alias){
        return byAlias.getOrDefault(alias, ContentType.ANY);
    }

    public static ContentType ofNullable(String string){
        try {
            return valueOf(string.toUpperCase(Locale.ROOT));
        } catch (Throwable throwable) {
            return ContentType.ANY;
        }
    }

}
