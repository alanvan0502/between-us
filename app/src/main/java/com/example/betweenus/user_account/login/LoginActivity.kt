package com.example.betweenus.user_account.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.betweenus.R
import com.example.betweenus.helper.addSimpleTextChangedListener
import com.example.betweenus.helper.toStringOrEmptyString
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onResume() {
        super.onResume()
        setupLoginButton()
        setupEmailEditText()
    }

    private fun setupEmailEditText() {
        email_input.addSimpleTextChangedListener {
            getEmailErrorMessage(it)?.let { error ->
                email_input_layout.error = error
                return@addSimpleTextChangedListener
            }
            email_input_layout.error = null
        }
    }

    private fun setupLoginButton() {
        login_button.setOnClickListener {
            viewModel.signIn(
                email = email_input.text.toStringOrEmptyString(),
                password = password_input.text.toStringOrEmptyString()
            )
        }
    }

    private fun getEmailErrorMessage(email: CharSequence?): String? {
        return when {
            email.isNullOrEmpty() -> getString(R.string.email_is_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.invalid_email)
            else -> null
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
