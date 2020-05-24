package com.example.betweenus.user_account.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.addSimpleTextChangedListener
import com.example.betweenus.helper.hideSoftInputKeyboard
import com.example.betweenus.helper.startActivityAndFinish
import com.example.betweenus.helper.toStringOrEmptyString
import com.example.betweenus.main.MainActivity
import com.example.betweenus.user_account.BaseActivity
import com.example.betweenus.user_account.sign_up.SignUpActivity
import com.example.domain.base.data
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        setupLoginButton()
        setupEmailEditText()
        setupPasswordEditText()
        setupGoToSignUpButton()
    }

    private fun setupViewModel() {
        if (viewModel.isUserSignedIn()) {
            startActivityAndFinish(MainActivity::class.java)
        }
        viewModel.apply {
            getAuthStatusFlow()
            signInLiveData.observe(this@LoginActivity, Observer {
                observeResultStates(it)
            })
            authDataLiveData.observe(this@LoginActivity, Observer {
                it.data?.uid?.let {
                    startActivityAndFinish(MainActivity::class.java)
                }
            })
        }
    }

    private fun setupGoToSignUpButton() {
        go_to_sign_up.setOnClickListener {
            SignUpActivity.newIntent(this).also {
                startActivity(it)
                finish()
            }
        }
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

    private fun setupPasswordEditText() {
        password_input.addSimpleTextChangedListener {
            getPasswordErrorMessage(it)?.let { error ->
                password_input_layout.error = error
                return@addSimpleTextChangedListener
            }
            password_input_layout.error = null
        }
    }

    private fun setupLoginButton() {
        login_button.setOnClickListener {
            if (allFieldsValidated()) {
                viewModel.signIn(
                    email = email_input.text.toStringOrEmptyString(),
                    password = password_input.text.toStringOrEmptyString()
                )
                this.hideSoftInputKeyboard(password_input.windowToken)
            } else {
                showError(getString(R.string.check_email_password))
            }
        }
    }

    private fun allFieldsValidated(): Boolean {
        return email_input_layout.error == null && password_input_layout.error == null
    }

    private fun getEmailErrorMessage(email: CharSequence?): String? {
        return when {
            email.isNullOrEmpty() -> getString(R.string.email_is_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.invalid_email)
            else -> null
        }
    }

    private fun getPasswordErrorMessage(password: CharSequence?): String? {
        return when {
            password.isNullOrEmpty() -> getString(R.string.password_required)
            else -> null
        }
    }
}
