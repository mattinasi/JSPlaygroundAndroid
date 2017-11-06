package com.example.marcattinasi.jsplaygroundandroid

import org.liquidplayer.webkit.javascriptcore.JSBaseArray
import org.liquidplayer.webkit.javascriptcore.JSContext
import org.liquidplayer.webkit.javascriptcore.JSObject
import org.liquidplayer.webkit.javascriptcore.JSValue
import java.util.*

/**
 * Created by marc.attinasi on 11/3/17.
 */


class ChatSession(obj: JSObject) {
    val id: String = obj.property("id").toString()
    val owner: String = obj.property("owner").toString()
    val timeStamp: String = obj.property("timeStamp").toString()
    val messages: Array<ChatMessage> = getMessages(obj.property("messages").toJSArray())

    private fun getMessages(messages: JSBaseArray<Any>): Array<ChatMessage> {
        val result = Array<ChatMessage>(messages.size) { index ->
            val jsVal = messages[index] as JSValue
            ChatMessage(jsVal.toObject())
        }

        return result
    }
}

class ChatMessage {
    val id: String
    val timeStamp: String
    val text: String

    constructor(msg: JSObject) {
        id = msg.property("id").toString()
        timeStamp = msg.property("timeStamp").toString()
        text = msg.property("text").toString()
    }

    constructor(messageText: String) {
        id = UUID.randomUUID().toString()
        timeStamp = Date().toString()
        text = messageText
    }

    fun toJS(context: JSContext): JSObject {
        val jsobj = JSObject(context).apply {
            property("id", id)
            property("timeStamp", timeStamp)
            property("text", text)
        }
        return jsobj
    }
}