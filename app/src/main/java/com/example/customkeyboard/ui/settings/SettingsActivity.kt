package com.example.customkeyboard.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.customkeyboard.R
import com.example.customkeyboard.ui.comments.CommentListActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchAutoPaste = findViewById<Switch>(R.id.switch_auto_paste)
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        
        switchAutoPaste.isChecked = prefs.getBoolean("auto_paste_enabled", true)
        switchAutoPaste.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("auto_paste_enabled", isChecked).apply()
        }

        findViewById<Button>(R.id.btn_manage_comments).setOnClickListener {
            startActivity(Intent(this, CommentListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_enable_keyboard).setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        findViewById<Button>(R.id.btn_select_keyboard).setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showInputMethodPicker()
        }
    }
}
