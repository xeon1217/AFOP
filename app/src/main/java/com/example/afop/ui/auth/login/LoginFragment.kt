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
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.data.exception.EmailVerifiedException
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.FragmentLoginBinding
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.ui.activity.MainActivity
import com.example.afop.ui.activity.RegisterActivity
import com.example.afop.ui.activity.ResetPasswordActivity
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.fragment_login.*

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
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        viewModel = ViewModelProvider(viewModelStore, LoginViewModelFactory()).get(LoginViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        loginAutoLoginCheckBox.isChecked = DataSource.isAutoLogin()

        binding.fragment = this
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

        if (DataSource.isAutoLogin()) {
            mActivity.showLoading()
            viewModel.autoLogin()
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
            result.apply {
                mActivity.hideLoading()
                AlertDialog.Builder(mActivity).apply {
                    setCancelable(false)
                    result.result?.let {
                        (it as LoginResult).apply {
                            isLogin?.let {
                                mActivity.startActivity(Intent(mActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                            }
                        }
                    }
                    error?.apply {
                        Log.d("LoginException", "$this")
                        when (this) {
                            is EmailVerifiedException -> {
                                setTitle(getString(R.string.dialog_title_login_fail))
                                setMessage(R.string.dialog_message_need_email_verify)
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    loginEmailTextInputEditText.setText("")
                                    loginPasswordTextInputEditText.setText("")
                                }
                            }
                            is FirebaseAuthInvalidCredentialsException -> { // 아이디 혹은 패스워드 틀림
                                setTitle(getString(R.string.dialog_title_login_fail))
                                setMessage(getString(R.string.dialog_message_error_login_fail))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    loginEmailTextInputEditText.setText("")
                                    loginPasswordTextInputEditText.setText("")
                                }
                            }
                            is FirebaseAuthInvalidUserException -> { // 사용자 존재하지 않음
                                setTitle(getString(R.string.dialog_title_login_fail))
                                setMessage(getString(R.string.dialog_message_error_login_fail))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    loginEmailTextInputEditText.setText("")
                                    loginPasswordTextInputEditText.setText("")
                                }
                            }
                            is FirebaseTooManyRequestsException -> { // 너무 많은 로그인을 시도함!
                                setTitle(getString(R.string.dialog_title_login_fail))
                                setMessage(getString(R.string.dialog_message_error_login_fail))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    loginEmailTextInputEditText.setText("")
                                    loginPasswordTextInputEditText.setText("")
                                }
                            }
                            else -> {
                                setTitle(getString(R.string.dialog_title_login_fail))
                                setMessage(getString(R.string.dialog_message_unknown_error))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    Log.d("reg", "$error")
                                }
                            }
                        }.show()
                    }
                }
            }
        })
    }

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