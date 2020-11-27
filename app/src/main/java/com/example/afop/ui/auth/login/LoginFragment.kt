package com.example.afop.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.response.ErrorCode
import com.example.afop.databinding.FragmentLoginBinding
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.ui.activity.RegisterActivity
import com.example.afop.ui.activity.ResetPasswordActivity
import com.example.afop.ui.auth.register.RegisterResponse
import kotlinx.android.synthetic.main.fragment_login.*
import com.example.afop.data.response.Result
import com.example.afop.ui.activity.MainActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mActivity: ActivityExtendFunction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.fragment = this
        viewModel = ViewModelProvider(viewModelStore, LoginViewModelFactory()).get(LoginViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        subscribeUi()

        return binding.root
    }

    fun subscribeUi() {
        if(DataSource.getAutoLogin()) {
            mActivity.showLoading()
            viewModel.autoLogin()
        }

        binding.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.loginDataChanged(
                    email = loginEmailTextInputEditText.text.toString(),
                    password = loginPasswordTextInputEditText.text.toString()
                )
            }
        }

        //관찰자 등록
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                loginButton.isEnabled = isDataValid
                loginEmailTextInputLayout.error = emailError?.let { getString(it) }
                loginPasswordTextInputLayout.error = passwordError?.let { getString(it) }
            }
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
            mActivity.hideLoading()

            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                result.response?.let {
                    (it as LoginResponse).apply {
                        isLogin?.let {
                            successLogin()
                        }
                    }
                }
                result.error?.apply {
                    setTitle(getString(R.string.dialog_title_fail))
                    setMessage(message)
                    when(this) {
                        ErrorCode.EXPIRED_TOKEN -> {
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                DataSource.logout()
                            }
                        }
                        ErrorCode.NOT_VERIFY_EMAIL -> {
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ -> }
                        }
                        ErrorCode.FAILED_LOGIN -> {
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                failedLogin()
                            }
                        }
                        else -> {
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ -> }
                        }
                    }.show()
                }
            }
        })
    }

    //로그인 성공
    fun successLogin() {

        Toast.makeText(context, "안녕하세요 ${DataSource.getUser().nickname}님!", Toast.LENGTH_SHORT).show()
        mActivity.startActivity(Intent(mActivity, MainActivity::class.java))
    }

    fun failedLogin() {
        loginEmailTextInputEditText.setText("")
        loginPasswordTextInputEditText.setText("")
    }

    //로그인
    fun login(view: View) {
        mActivity.showLoading()
        viewModel.login(
            loginEmailTextInputEditText.text.toString(),
            loginPasswordTextInputEditText.text.toString()
        )
    }

    fun autoLogin(view: View) {
        viewModel.setAutoLogin(loginAutoLoginCheckBox.isChecked)
    }

    fun register(view: View) {
        mActivity.startActivity(Intent(mActivity, RegisterActivity::class.java))
    }

    fun resetPassword(view: View) {
        mActivity.startActivity(Intent(mActivity, ResetPasswordActivity::class.java))
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}