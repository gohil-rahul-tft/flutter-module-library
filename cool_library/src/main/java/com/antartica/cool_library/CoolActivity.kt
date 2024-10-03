package com.antartica.cool_library

import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity

class CoolActivity {
    fun hello(context: Context): Intent {
        Toast.makeText(context, "Hello From Library", Toast.LENGTH_SHORT).show()
        return FlutterActivity.createDefaultIntent(context)
    }
}