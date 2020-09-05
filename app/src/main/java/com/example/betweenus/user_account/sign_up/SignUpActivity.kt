package com.example.betweenus.user_account.sign_up

import android.util.Patterns
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.increaseTouchableArea
import com.example.betweenus.helper.start
import com.example.betweenus.helper.toStringOrEmptyString
import com.example.betweenus.helper.verify
import com.example.betweenus.main.MainActivity
import com.example.betweenus.user_account.BaseActivity
import com.example.betweenus.user_account.login.LoginActivity
import com.example.domain.account.data.SignUpData
import com.example.domain.account.data.User
import com.example.domain.base.BUResult
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_signup

    private lateinit var goToLogin: TextView
    private lateinit var signUpButton: MaterialButton
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout

    override fun onCreateActivity() {
        super.onCreateActivity()

        goToLogin = findViewById(R.id.go_to_login)
        signUpButton = findViewById(R.id.sign_up_button)
        nameInput = findViewById(R.id.name_input)
        emailInput = findViewById(R.id.email_input)
        confirmPasswordInput = findViewById(R.id.confirm_password_input)
        passwordInput = findViewById(R.id.password_input)
        nameInputLayout = findViewById(R.id.name_input_layout)
        passwordInputLayout = findViewById(R.id.password_input_layout)
        emailInputLayout = findViewById(R.id.email_input_layout)

        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        setupSignUpButton()
        setupGoToLoginButton()
    }

    private fun setupViewModel() {
        viewModel.apply {
            signUpLiveData.observe(this@SignUpActivity, Observer {
                observeResultStates(it)
                if (it is BUResult.Success && viewModel.isUserSignedIn()) {
                    start<MainActivity>(true)
                }
            })
        }
    }

    override fun onBackPressed() {
        // do nothing on back pressed
    }

    private fun setupGoToLoginButton() {
        goToLogin.increaseTouchableArea()
        goToLogin.setOnClickListener {
            start<LoginActivity>(true)
        }
    }

    private fun setupSignUpButton() {
        signUpButton.setOnClickListener {
            verifyAllFields()
            if (isAllFieldsValid()) {
                viewModel.signUp(data = getSignUpData())
            }
        }
    }

    private fun getSignUpData() = SignUpData(
        user = User(
            name = nameInput.text.toStringOrEmptyString(),
            email = emailInput.text.toStringOrEmptyString(),
            photoUrl = ""
        ),
        password = passwordInput.text.toStringOrEmptyString()
    )

    private fun isAllFieldsValid(): Boolean {
        return nameInputLayout.error == null
                && emailInputLayout.error == null
                && passwordInputLayout.error == null
    }

    private fun verifyAllFields() {
        verifyName()
        verifyPassword()
        verifyEmail()
    }

    private fun verifyName() = nameInputLayout.verify(
        errorPredicate = nameInput.text.toStringOrEmptyString().isEmpty(),
        errorMessage = getString(R.string.empty_name_error)
    )

    private fun verifyEmail() = emailInputLayout.verify(
        errorPredicate = !Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toStringOrEmptyString())
            .matches(),
        errorMessage = getString(R.string.invalid_email_error)
    )

    private fun verifyPassword() = passwordInputLayout.verify(
        errorPredicate = passwordInput.text.toStringOrEmptyString().isEmpty() ||
                passwordInput.text.toStringOrEmptyString() != confirmPasswordInput.text.toStringOrEmptyString(),
        errorMessage = getString(R.string.password_no_match_error)
    )
}
