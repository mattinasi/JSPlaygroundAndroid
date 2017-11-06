package com.example.marcattinasi.jsplaygroundandroid

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val inputStream = appContext.assets.open("model.js")
        loadModelScript(inputStream)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.marcattinasi.jsplaygroundandroid", appContext.packageName)
    }

    @Test
    fun messWithJavascriptBridging() {
        val jsm = JSManager.getInstance()
        assertEquals("model script 0", jsm.getTitle())

        val owner = "Marc"
        val sessionId = JSManager.getInstance().addSession(owner)
        assertEquals("97a36bfc-9c49-45fc-93e2-439ef5ee0069".length, sessionId.length)

        val session = JSManager.getInstance().getSession(sessionId)
        assertNotNull(session!!)
        assertEquals(sessionId, session.id)
        assertEquals(owner, session.owner)
        assertEquals(0, session.messages.size)

        val msg = "Q. Are we not men?"
        val result = JSManager.getInstance().addMessage(sessionId, msg)
        assertTrue(result)

        val session2 = JSManager.getInstance().getSession(sessionId)
        assertNotNull(session2!!)
        assertEquals(1, session2.messages.size);
        assertEquals(msg, session2.messages[0].text)

        JSManager.getInstance().printModel()
    }
}
