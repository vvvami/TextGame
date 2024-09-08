package net.vami.game.world;

import com.google.gson.*;
import net.vami.game.interactables.entities.Player;
import net.vami.game.interactables.interactions.abilities.Ability;
import net.vami.game.interactables.interactions.damagetypes.DamageType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class DamageTypeAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public DamageType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String simpleName = jsonElement.getAsString();
        String interfaceName = DamageType.class.getName();
        String fullName = interfaceName.substring(0, interfaceName.lastIndexOf(".") + 1) + simpleName;
        Class klass = getObjectClass(fullName);
        return (DamageType) new Gson().fromJson("{}", klass);
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
