package net.vami.game.interactables.interactions.abilities;

import com.google.gson.*;
import net.vami.game.interactables.entities.Player;
import net.vami.game.interactables.interactions.damagetypes.DamageType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class AbilityAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public Ability deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String simpleName = jsonElement.getAsString();
        String interfaceName = Ability.class.getName();
        String fullName = interfaceName.substring(0, interfaceName.lastIndexOf(".") + 1) + simpleName;
        Class klass = getObjectClass(fullName);
        return (Ability) new Gson().fromJson("{}", klass);
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
