package com.example.marcattinasi.jsplaygroundandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadModelScript(assets.open("model.js"))

        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        configureControls()

        seedData()

        updateTitle()
    }

    private fun configureControls() {
        findViewById<Button>(R.id.doItAgain).setOnClickListener {
            updateTitle()
        }
    }

    private fun seedData() {
        val jsm = JSManager.getInstance()
        jsm.addObject("a", "android")
        jsm.addObject("b", "brontosaurus")
        jsm.addObject("c", 3.1415)
        jsm.addObject("d", arrayOf(1,2,3,4,5))
        jsm.addObject("e", mapOf(Pair("x", 100), Pair("y", 200)))

        print("Seeded Data: ")
        jsm.printModel()
    }

    private fun updateTitle() {
        val titleView = findViewById<TextView>(R.id.title_label)
        val msg = JSManager.getInstance().getTitle() + "\n" + JSManager.getInstance().getModel()
        titleView.text = msg
    }
}

fun loadModelScript(inputStream: InputStream) {
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    var line: String?
    var contents = ""
    var eof = false

    while (!eof) {
        line = bufferedReader.readLine()
        if (line != null) {
            contents += line
        } else {
            eof = true
        }
    }

    JSManager.script = contents
}