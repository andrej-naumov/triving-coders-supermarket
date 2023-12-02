package org.thriving.coders.supermarket.util;

import com.google.gson.*;
import spark.ResponseTransformer;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Binds a JSON transformation to the HTTP responses
 */
public class JsonTransformer implements ResponseTransformer {

    private final Gson gson;

    public JsonTransformer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        gson = gsonBuilder.create();
    }

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    static class InstantDeserializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type,
                                     JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.toString());
        }
    }
}
