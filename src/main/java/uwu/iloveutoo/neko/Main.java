package uwu.iloveutoo.neko;

import org.apache.commons.cli.*;
import uwu.iloveutoo.neko.app.AppSettings;
import uwu.iloveutoo.neko.model.ContentType;
import uwu.iloveutoo.neko.util.Util;

import java.io.File;
import java.util.stream.Collectors;

/* Args -t any -c baka -a 10 -s="./xd/" */
public class Main {

    /* Fields */

    public static final String AMOUNT_ARG = "amount";
    public static final String SAVE_ARG = "save";
    public static final String CATEGORY_ARG = "category";
    public static final String CONTENT_TYPE_ARG = "content";
    public static final String HELP_ARG = "help";

    /* Constructor */

    /* Implements */

    /* Overrides */

    /* Methods */

    public static void main(String[] args) throws Exception {

        Option amountOption = Option.builder("a")
                .longOpt(AMOUNT_ARG)
                .type(PatternOptionBuilder.NUMBER_VALUE)
                .desc("Sets amount of content")
                .required(false)
                .hasArg(true)
                .build();

        Option saveDirectoryOption = Option.builder("s")
                .longOpt(SAVE_ARG)
                .type(PatternOptionBuilder.FILE_VALUE)
                .desc("Sets save directory of images")
                .required(false)
                .hasArg(true)
                .build();

        Option categoryOption = Option.builder("c")
                .longOpt(CATEGORY_ARG)
                .type(PatternOptionBuilder.STRING_VALUE)
                .desc("Sets category of content")
                .required(false)
                .hasArg(true)
                .optionalArg(true)
                .numberOfArgs(69)
                .build();

        Option contentTypeOption = Option.builder("t")
                .longOpt(CONTENT_TYPE_ARG)
                .type(PatternOptionBuilder.STRING_VALUE)
                .desc("Sets type of content")
                .required(false)
                .hasArg(true)
                .build();

        Option helpOption = Option.builder("h")
                .longOpt(HELP_ARG)
                .desc("Get help")
                .required(false)
                .build();

        Options options = new Options();
        options.addOption(amountOption);
        options.addOption(saveDirectoryOption);
        options.addOption(categoryOption);
        options.addOption(contentTypeOption);
        options.addOption(helpOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        Long amount = (Long) cmd.getParsedOptionValue(AMOUNT_ARG);
        AppSettings appSettings = AppSettings.builder()
                .amount(amount == null ? 1 : amount.intValue())
                .saveDirectory((File) cmd.getParsedOptionValue(SAVE_ARG))
                .saveImages(cmd.hasOption(SAVE_ARG))
                .categories(Util.caseInsensitiveSet(cmd.getOptionValues(CATEGORY_ARG)))
                .contentType(ContentType.ofNullable((String) cmd.getParsedOptionValue(CONTENT_TYPE_ARG)))
                .create();


        Bootstrapper bootstrapper = new Bootstrapper(appSettings);

        if (cmd.hasOption(HELP_ARG)) {
            printHelp(options, bootstrapper);
            return;
        }

        bootstrapper.run();
    }

    public static void printHelp(Options options, Bootstrapper bootstrapper){
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("neko-downloader", options);

        System.out.println("Available categories: ");

        for (ContentType type : ContentType.values()) {
            System.out.printf(" %s:%n", type.getAlias());

            String endpointsFormatted = bootstrapper.getEndpoints(type)
                    .stream()
                    .map(endpoint -> String.format("[%s]", endpoint.getName()))
                    .collect(Collectors.joining(", "));

            System.out.println(endpointsFormatted);
        }
    }

}
