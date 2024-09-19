package net.vami.game.interactables;

import com.google.gson.*;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.damagetypes.DamageType;

import java.lang.reflect.Type;

public class InteractableAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public Interactable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject mainObj = jsonElement.getAsJsonObject();
        String fullName = mainObj.getAsJsonObject("klass").getAsString();
        Class klass = getObjectClass(fullName);
        return jsonDeserializationContext.deserialize(mainObj.getAsJsonObject("value"), klass);
    }

    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject classObj = new JsonObject();
        classObj.addProperty("klass", o.getClass().getName());
        classObj.add("value", jsonSerializationContext.serialize(o));
        return classObj;
    }

    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}
