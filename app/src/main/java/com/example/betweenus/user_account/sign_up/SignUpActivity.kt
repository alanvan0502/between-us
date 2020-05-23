package com.example.betweenus.user_account.sign_up

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.Observer
import com.example.betweenus.R
import com.example.betweenus.helper.toStringOrEmptyString
import com.example.betweenus.helper.verify
import com.example.betweenus.user_account.BaseActivity
import com.example.domain.account.data.SignUpData
import com.example.domain.account.data.User
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModel()

    companion object {
        fun newIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        bindViews()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        setupSignUpButton()
    }

    private fun setupViewModel() {
        viewModel.apply {
            signUpLiveData.observe(this@SignUpActivity, Observer {
                observeResultStates(it)
            })
        }
    }

    private fun setupSignUpButton() {
        sign_up_button.setOnClickListener {
            verifyAllFields()
            if (isAllFieldsValid()) {
                viewModel.signUp(
                    data = SignUpData(
                        user = User(
                            name = name_input.text.toStringOrEmptyString(),
                            email = email_input.text.toStringOrEmptyString(),
                            photoUrl = "",
                            phoneNumber = phone_input.text.toStringOrEmptyString(),
                            statusUrl = "",
                            statusTime = ""
                        ),
                        password = password_input.text.toStringOrEmptyString()
                    )
                )
            }
        }
    }

    private fun isAllFieldsValid(): Boolean {
        return name_input_layout.error == null
                && phone_input_layout.error == null
                && email_input_layout.error == null
                && password_input_layout.error == null
    }

    private fun verifyAllFields() {
        verifyName()
        verifyPhone()
        verifyPassword()
        verifyEmail()
    }

    private fun verifyName() = name_input_layout.verify(
        errorPredicate = name_input.text.toStringOrEmptyString().isEmpty(),
        errorMessage = getString(R.string.empty_name_error)
    )

    private fun verifyPhone() = phone_input_layout.verify(
        errorPredicate = phone_input.text.toStringOrEmptyString().isEmpty(),
        errorMessage = getString(R.string.empty_phone_error)
    )

    private fun verifyEmail() = email_input_layout.verify(
        errorPredicate = !Patterns.EMAIL_ADDRESS.matcher(email_input.text.toStringOrEmptyString())
            .matches(),
        errorMessage = getString(R.string.invalid_email_error)
    )

    private fun verifyPassword() = password_input_layout.verify(
        errorPredicate = password_input.text.toStringOrEmptyString().isEmpty() ||
                password_input.text.toStringOrEmptyString() != confirm_password_input.text.toStringOrEmptyString(),
        errorMessage = getString(R.string.password_no_match_error)
    )
}
