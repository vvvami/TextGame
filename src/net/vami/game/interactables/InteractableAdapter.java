package net.vami.game.interactables;

import com.google.gson.*;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.interactions.damagetypes.DamageType;

import java.lang.reflect.Type;

public class InteractableAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public Interactable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String fullName = jsonElement.getAsString();
        Class klass = getObjectClass(fullName);
        return (Interactable) new Gson().fromJson("{}", klass);
    }

    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(o.getClass().getSimpleName());
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
