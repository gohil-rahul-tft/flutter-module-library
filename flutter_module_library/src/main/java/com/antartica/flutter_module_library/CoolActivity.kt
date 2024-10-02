package com.antartica.flutter_module_library

import android.content.Context
import android.widget.Toast


class CoolActivity {

    fun hello(context: Context) {
        Toast.makeText(context, "Hello From Library", Toast.LENGTH_SHORT).show()
    }
}