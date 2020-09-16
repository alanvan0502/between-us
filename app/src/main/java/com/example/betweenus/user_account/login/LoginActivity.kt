package com.example.betweenus.user_account.login

import android.util.Patterns
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.start
import com.example.betweenus.helper.increaseTouchableArea
import com.example.betweenus.helper.addSimpleTextChangedListener
import com.example.betweenus.helper.toStringOrEmptyString
import com.example.betweenus.helper.hideSoftInputKeyboard
import com.example.betweenus.main.MainActivity
import com.example.betweenus.user_account.BaseActivity
import com.example.betweenus.user_account.sign_up.SignUpActivity
import com.example.domain.base.data
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()
    override var backPressToExitApp = true

    private lateinit var goToSignUp: TextView
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var loginButton: MaterialButton

    override val layoutRes = R.layout.activity_login

    override fun onCreateActivity() {
        super.onCreateActivity()

        goToSignUp = findViewById(R.id.go_to_sign_up)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        passwordInputLayout = findViewById(R.id.password_input_layout)
        emailInputLayout = findViewById(R.id.email_input_layout)
        loginButton = findViewById(R.id.login_button)

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
            signInLiveData.observe(
                this@LoginActivity,
                Observer {
                    observeResultStates(it)
                }
            )
            authDataLiveData.observe(
                this@LoginActivity,
                Observer {
                    it.data?.uid?.let {
                        start<MainActivity>(finishCurrent = true)
                    }
                }
            )
        }
    }

    private fun setupGoToSignUpButton() {
        goToSignUp.increaseTouchableArea()
        goToSignUp.setOnClickListener {
            start<SignUpActivity>(true)
        }
    }

    private fun setupEmailEditText() {
        emailInput.addSimpleTextChangedListener {
            getEmailErrorMessage(it)?.let { error ->
                emailInputLayout.error = error
                return@addSimpleTextChangedListener
            }
            emailInputLayout.error = null
        }
    }

    private fun setupPasswordEditText() {
        passwordInput.addSimpleTextChangedListener {
            getPasswordErrorMessage(it)?.let { error ->
                passwordInputLayout.error = error
                return@addSimpleTextChangedListener
            }
            passwordInputLayout.error = null
        }
    }

    private fun setupLoginButton() {
        loginButton.setOnClickListener {
            if (allFieldsValidated()) {
                viewModel.signIn(
                    email = emailInput.text.toStringOrEmptyString(),
                    password = passwordInput.text.toStringOrEmptyString()
                )
                this.hideSoftInputKeyboard(passwordInput.windowToken)
            } else {
                showError(getString(R.string.check_email_password))
            }
        }
    }

    private fun allFieldsValidated(): Boolean {
        return emailInputLayout.error == null && passwordInputLayout.error == null
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
