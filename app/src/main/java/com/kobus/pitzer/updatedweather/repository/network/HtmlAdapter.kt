package com.kobus.pitzer.updatedweather.repository.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import org.apache.commons.text.StringEscapeUtils
import java.lang.reflect.Type

class HtmlAdapter : JsonDeserializer<String> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext): String? {
        return StringEscapeUtils.unescapeHtml4(json.asString)
    }
}