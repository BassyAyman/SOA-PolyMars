package com.marsy.teamb.commandservice.modele;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MarsyLogDeserializer extends JsonDeserializer<MarsyLog> {

    @Override
    public MarsyLog deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String service = node.get("service").asText();
        String message = node.get("message").asText();

        return new MarsyLog(service, message);
    }
}
