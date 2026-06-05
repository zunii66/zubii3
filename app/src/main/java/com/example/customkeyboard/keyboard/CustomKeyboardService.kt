package com.example.customkeyboard.keyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.customkeyboard.R
import com.example.customkeyboard.data.database.AppDatabase
import com.example.customkeyboard.data.repository.CommentRepository
import kotlinx.coroutines.*

class CustomKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private lateinit var repository: CommentRepository
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private var lastCommentId: Long = -1

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)
        repository = CommentRepository(database.commentDao())
    }

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        keyboard = Keyboard(this, R.xml.qwerty)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        
        // Check if auto-paste is enabled and if the current app is allowed
        if (shouldAutoPaste(info?.packageName)) {
            insertRandomComment()
        }
    }

    private fun shouldAutoPaste(packageName: String?): Boolean {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val autoPasteEnabled = prefs.getBoolean("auto_paste_enabled", true)
        val selectedApps = prefs.getStringSet("selected_apps", emptySet()) ?: emptySet()
        
        return autoPasteEnabled && (selectedApps.isEmpty() || selectedApps.contains(packageName))
    }

    private fun insertRandomComment() {
        serviceScope.launch {
            val comment = withContext(Dispatchers.IO) {
                var nextComment = repository.getRandomComment()
                // Simple non-repeating logic
                if (nextComment?.id == lastCommentId) {
                    nextComment = repository.getRandomComment()
                }
                nextComment
            }
            
            comment?.let {
                currentInputConnection?.commitText(it.text, 1)
                lastCommentId = it.id
            }
        }
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val ic = currentInputConnection ?: return
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> ic.deleteSurroundingText(1, 0)
            Keyboard.KEYCODE_SHIFT -> {
                keyboard.isShifted = !keyboard.isShifted
                keyboardView.invalidateAllKeys()
            }
            Keyboard.KEYCODE_DONE -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            -100 -> insertRandomComment() // Custom code for random button
            else -> {
                var code = primaryCode.toChar()
                if (keyboard.isShifted && code.isLetter()) {
                    code = code.uppercaseChar()
                }
                ic.commitText(code.toString(), 1)
            }
        }
    }

    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}
    override fun onText(text: CharSequence?) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
