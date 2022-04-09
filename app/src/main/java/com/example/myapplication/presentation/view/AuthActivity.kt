package com.example.myapplication.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // not needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // not needed
        }

        override fun afterTextChanged(s: Editable?) {
            updateButtonStatus()
        }
    }

    companion object {
        private const val EDIT_TEXT_MIN_COUNT = 6
        private const val EMAIL_KEY = "email"
        private const val PASSWORD_KEY = "password"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_KEY, binding.editEmailAddress.text.toString())
        outState.putString(PASSWORD_KEY, binding.editTextPassword.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            binding.editEmailAddress.setText(savedInstanceState.getString(EMAIL_KEY))
            binding.editTextPassword.setText(savedInstanceState.getString(PASSWORD_KEY))
        }

        updateButtonStatus()
        binding.editEmailAddress.addTextChangedListener(textWatcher)
        binding.editTextPassword.addTextChangedListener(textWatcher)

        val intent = Intent(this, MainActivity::class.java)
        binding.authButton.setOnClickListener {
            startActivity(intent)
        }

        binding.toolbar.setNavigationOnClickListener {
            this.finishAffinity()
        }
    }

    private fun updateButtonStatus() {
        binding.authButton.isEnabled = !(
            binding.editEmailAddress.text.length < EDIT_TEXT_MIN_COUNT ||
                binding.editTextPassword.text.length < EDIT_TEXT_MIN_COUNT
            )
    }
}
