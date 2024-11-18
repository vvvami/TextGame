package net.vami.game.interactables.items.attunement;

import com.google.gson.*;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.util.ClassUtil;

import java.lang.reflect.Type;

public class AttunementAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public Attunement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String simpleName = jsonElement.getAsString();
        String interfaceName = Attunement.class.getName();
        String fullName = interfaceName.substring(0, interfaceName.lastIndexOf(".") + 1) + simpleName;
        Class klass = ClassUtil.getObjectClass(fullName);
        return (Attunement) new Gson().fromJson("{}", klass);
    }

    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(o.getClass().getSimpleName());
    }
}
