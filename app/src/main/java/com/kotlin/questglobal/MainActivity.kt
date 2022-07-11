package com.kotlin.questglobal

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.questglobal.databinding.ActivityMainBinding
import com.kotlin.questglobal.models.Login
import com.kotlin.questglobal.network.RetroService
import com.kotlin.questglobal.repository.LoginRepository
import com.kotlin.questglobal.util.EmailValidator
import com.kotlin.questglobal.viewmodel.LoginViewModel
import com.kotlin.questglobal.viewmodel.LoginViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: LoginViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var mEmailValidator: EmailValidator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelAndBindView()
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loginResponse.observe(this, {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            binding.editEmail.setText("")
            binding.editPwd.setText("")
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        binding.btnLogin.setOnClickListener {
            val email: String = binding.editEmail.text.toString();
            val pwd: String = binding.editPwd.text.toString()
            if (!mEmailValidator.checkIsValid(email, pwd)) {
                Toast.makeText(this, "Invalid Username and Password", Toast.LENGTH_SHORT).show()
            } else
                viewModel.loginUser(Login(email, pwd))
        }


    }

    private fun initViewModelAndBindView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetroService.getInstance()
        val mainRepository = LoginRepository(retrofitService)
        mEmailValidator = EmailValidator()
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(mainRepository)
        ).get(LoginViewModel::class.java)
    }
}