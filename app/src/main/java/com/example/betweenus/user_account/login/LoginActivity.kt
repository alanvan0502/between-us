package com.example.betweenus.user_account.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.betweenus.R
import com.example.betweenus.injection.AppModule.TEST_STRING1
import com.example.betweenus.injection.AppModule.TEST_STRING2
import com.example.betweenus.injection.example.TestConcreteImpl
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val context by inject<Context>()
    private val viewModel: LoginViewModel by viewModel()

    private val testString1 by inject<String>(TEST_STRING1)
    private val testString2 by inject<String>(TEST_STRING2)
    private val testConcreteImpl by inject<TestConcreteImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onResume() {
        super.onResume()

        viewModel.testValue += 1
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
