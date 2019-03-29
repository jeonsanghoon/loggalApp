package com.altsoft.Framework.DataInfo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class DotNetDateConverter implements JsonDeserializer<Date>
{
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        String s = json.getAsJsonPrimitive().getAsString();
        long l = Long.parseLong(s.substring(s.indexOf("(")+1, s.indexOf("+")));
        Date d = new Date(l);
        return d;
    }
}