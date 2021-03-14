package uwu.iloveutoo.neko.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.JsonPrimitive;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadFactory;

public final class Util {

    public static String getResponseAsString(ResponseBody body){
        try {
            return body.string();
        } catch (IOException e) {
            return "";
        }
    }

    public static String getAsString(Object object){
        if (object instanceof String) {
            return (String) object;
        }

        if(object instanceof JsonPrimitive){
            return ((JsonPrimitive) object).getAsString();
        }

        return "";
    }

    public static ThreadFactory namedFactory(String name){
        return new ThreadFactoryBuilder()
                .setNameFormat(name)
                .build();
    }

    public static Set<String> caseInsensitiveSet(String... array){
        if(array == null) {
            return Collections.emptySet();
        }
        Set<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String string : array) {
            set.add(string);
        }
        return set;
    }

    @Nullable
    public static FileInputStream getFileStream(String path){
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

}
