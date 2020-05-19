package com.example.betweenus.user_account.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.betweenus.R
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
        login_button.setOnClickListener {
            viewModel.signIn(
                email = email_input.text.toStringOrEmptyString(),
                password = password_input.text.toStringOrEmptyString()
            )
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
