package com.example.androidapp4

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp4.data.AffirmationRepository
import com.example.androidapp4.data.Prefs

class MainActivity : AppCompatActivity() {

    private lateinit var tvAffirmation: TextView
    private lateinit var tvStatus: TextView

    // Holds the currently displayed affirmation text.
    private var currentText: String = "Tap NEW to begin ✨"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ----- UI References -----
        tvAffirmation = findViewById(R.id.tvAffirmation)
        tvStatus = findViewById(R.id.tvStatus)

        val btnNew: Button = findViewById(R.id.btnNew)
        val btnSave: Button = findViewById(R.id.btnSave)
        val btnCopy: Button = findViewById(R.id.btnCopy)
        val btnShare: Button = findViewById(R.id.btnShare)

        // ----- Load last saved affirmation (DailyVerse-style persistence) -----
        val last = Prefs.loadLastAffirmation(this)
        if (!last.isNullOrBlank()) {
            currentText = last
            tvAffirmation.text = currentText
            tvStatus.text = "Loaded your saved affirmation ✅"
        } else {
            tvAffirmation.text = currentText
            tvStatus.text = "Tap NEW to get your first affirmation ✨"
        }

        // ----- Button: NEW -----
        btnNew.setOnClickListener {
            val item = AffirmationRepository.randomAffirmation()
            currentText = item.text
            tvAffirmation.text = currentText
            tvStatus.text = "New affirmation generated ✅"
        }

        // ----- Button: SAVE -----
        btnSave.setOnClickListener {
            Prefs.saveLastAffirmation(this, currentText)
            tvStatus.text = "Saved! It will stay after closing the app ✅"
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }

        // ----- Button: COPY -----
        btnCopy.setOnClickListener {
            copyToClipboard(currentText)
            tvStatus.text = "Copied to clipboard ✅"
        }

        // ----- Button: SHARE -----
        btnShare.setOnClickListener {
            shareText(currentText)
        }
    }

    // Copies text to the device clipboard.
    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Affirmation", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
    }

    // Opens Android Share Sheet so user can share via WhatsApp, email, etc.
    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "✨ DailyAffirm ✨\n\n$text")
        }
        startActivity(Intent.createChooser(intent, "Share your affirmation"))
    }
}
