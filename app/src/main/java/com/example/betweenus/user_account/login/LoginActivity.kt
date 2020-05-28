package com.example.betweenus.user_account.login

import android.util.Patterns
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.*
import com.example.betweenus.main.MainActivity
import com.example.betweenus.user_account.BaseActivity
import com.example.betweenus.user_account.sign_up.SignUpActivity
import com.example.domain.base.data
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()
    override var backPressToExitApp = true

    override val layoutRes = R.layout.activity_login

    override fun onCreateActivity() {
        super.onCreateActivity()
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
            start<MainActivity>(true)
        }
        viewModel.apply {
            getAuthStatusFlow()
            signInLiveData.observe(this@LoginActivity, Observer {
                observeResultStates(it)
            })
            authDataLiveData.observe(this@LoginActivity, Observer {
                it.data?.uid?.let {
                    start<MainActivity>(finishCurrent = true)
                }
            })
        }
    }

    private fun setupGoToSignUpButton() {
        go_to_sign_up.increaseTouchableArea()
        go_to_sign_up.setOnClickListener {
            start<SignUpActivity>(true)
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
