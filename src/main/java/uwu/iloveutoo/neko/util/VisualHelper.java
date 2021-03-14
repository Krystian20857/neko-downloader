package uwu.iloveutoo.neko.util;

import uwu.iloveutoo.neko.model.emitter.EndpointImage;
import uwu.iloveutoo.neko.model.emitter.EndpointModel;
import uwu.iloveutoo.neko.model.emitter.EndpointText;

import java.util.function.Function;

public final class VisualHelper {

    public static String formatImageDownload(EndpointImage image) {
        return formatEndpoint("Downloaded Image", image, image1 -> image1.getImageURL().toString());
    }

    public static String formatImage(EndpointImage image) {
        return formatEndpoint("Image", image, image1 -> image1.getImageURL().toString());
    }

    public static String formatTextEndpoint(EndpointText text) {
        return formatEndpoint("Downloaded Text:", text, text1 -> text1.getText());
    }

    private static<V extends EndpointModel> String formatEndpoint(String prefix, V model, Function<V, String> field){
        return String.format("%s: %s [%s][%s]",
                prefix,
                field.apply(model),
                ConsoleColors.RED + model.getEndpoint().getContentType().name() + ConsoleColors.RESET,
                ConsoleColors.CYAN + model.getEndpoint().getName() + ConsoleColors.RESET
        );
    }

}
