package com.example.betweenus.injection

import com.example.betweenus.injection.example.TestConcreteImpl
import com.example.betweenus.injection.example.TestConcreteInput
import com.example.betweenus.injection.example.TestInterface
import com.example.betweenus.injection.example.TestInterfaceImpl
import com.example.betweenus.user_account.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModule {

    val TEST_STRING1 = named("test_string1")
    val TEST_STRING2 = named("test_string2")

    fun get() = module {
        factory<TestInterface> { TestInterfaceImpl() }
        viewModel { LoginViewModel() }
        single(TEST_STRING1) { "this is test string 1" }
        single(TEST_STRING2) { "this is test string 2" }

        single { TestConcreteInput() }
        factory { TestConcreteImpl(get()) }
    }
}