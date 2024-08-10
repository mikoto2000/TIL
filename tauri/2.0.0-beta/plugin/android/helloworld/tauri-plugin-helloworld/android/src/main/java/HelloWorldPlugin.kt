package com.plugin.helloworld

import android.app.Activity
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@InvokeArg
class PingArgs {
  var value: String? = null
}

@TauriPlugin
class HelloWorldPlugin(private val activity: Activity): Plugin(activity) {

    @Command
    fun hello(invoke: Invoke) {
        val ret = JSObject()
        ret.put("message", "Hello, World!")
        invoke.resolve(ret)
    }
}
