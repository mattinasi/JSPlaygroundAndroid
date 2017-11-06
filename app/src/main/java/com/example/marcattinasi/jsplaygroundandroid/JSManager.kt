package com.example.marcattinasi.jsplaygroundandroid

import org.liquidplayer.webkit.javascriptcore.JSContext
import org.liquidplayer.webkit.javascriptcore.JSFunction
import org.liquidplayer.webkit.javascriptcore.JSObject
import java.util.*


/**
 * JSManager singleton class that initializes and maintains the JavaScript runtime
 *
 * It will install the initial script, which has to be set by the app pr activity (since it is probably a resource or asset)
 *
 * Provides simple wrappers around the JS funtions exposed in the scripts to make calling them easier
 * Sets the native callbacks into the JavaScript runtime
 *
 */


class JSManager private constructor() {

    private val _context = PlaygroundContext()

    init {
        if(script.length > 0) {
            _context.evaluateScript(script)

            _context.property("initialize").toFunction().call(null)
        }
    }

    fun getTitle() : String {
        return _context.property("getTitle").toFunction().call()
                .toString()
    }

    fun getModel() : String {
        return _context.property("getModel").toFunction().call().toString()
    }

    fun addObject(name: String, value: Any) {
        _context.property("addObject").toFunction().call(null, name, value)
    }

    fun printModel() {
        _context.property("printModel").toFunction().call()
    }

    fun addSession(owner: String) : String {
        val session = _context.property("newSession").toFunction().call(null, owner)
        return session.toObject().property("id").toString()
    }

    fun getSession(sessionId: String) : ChatSession? {
        val session = _context.property("getSession").toFunction().call(null, sessionId)
        if (!session.isNull) {
            return ChatSession(session.toObject())
        }
        return null
    }

    fun addMessage(sessionId: String, messageText: String): Boolean {
        val message = ChatMessage(messageText)

        return _context.property("addMessage").toFunction().call(null, sessionId, message.toJS(_context))
                .toBoolean()
    }

    companion object {
        var script: String = ""

        fun getInstance(): JSManager {
            if (_instance == null) {
                _instance = JSManager()
            }
            return _instance!!
        }

        private var _instance: JSManager? = null
    }
}

/**
 * PlaygroundContext subclasses the JSContext only to install the native console-logger function
 */
class PlaygroundContext : JSContext() {

    init {
        property("consoleLog", getConsoleLogFcn(this))
        property("uuidGen", getUUIDGeneratorFcn(this))

        property("loadModel", getLoadFcn(this))
        property("saveModel", getSaveFcn(this))

        println("Native Methods installed in JS context")
    }
}

fun getConsoleLogFcn(context: JSContext): JSFunction {
    return object : JSFunction(context, "consoleLog") {
        fun consoleLog(msg: String) {
            print("JS: " + msg)
        }
    }
}

fun getUUIDGeneratorFcn(context: JSContext): JSFunction {
    return object : JSFunction(context, "uuidGen") {
        fun uuidGen(): String {
            return UUID.randomUUID().toString()
        }
    }
}

fun getLoadFcn(context: JSContext): JSFunction {
    return object : JSFunction(context, "loadModel") {
        fun loadModel(): String {
            print("Loading Model...")
            return "{\"title\":\"loaded\",\"platform\":\"iOS\"}"
        }
    }
}

fun getSaveFcn(context: JSContext): JSFunction {
    return object : JSFunction(context, "saveModel") {
        fun saveModel(model: String) {
            print("Save Model: " + model)
            // TODO: save something
        }
    }
}
