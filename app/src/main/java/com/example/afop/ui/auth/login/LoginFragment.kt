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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.activity.MainActivity
import com.example.afop.activity.MyActivity
import com.example.afop.activity.RegisterActivity
import com.example.afop.activity.ResetPasswordActivity
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.User
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var mActivity: MyActivity

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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        viewModel = ViewModelProvider(viewModelStore, LoginViewModelFactory()).get(LoginViewModel::class.java)
        mActivity = activity as MyActivity
        loginAutoLoginCheckBox.isChecked = DataSource.isAutoLogin()
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.loginDataChanged(
                    email = loginEmailTextInputEditText.text.toString(),
                    password = loginPasswordTextInputEditText.text.toString()
                )
            }
        }

        Log.d("Login", "${DataSource.isAutoLogin()}")
        if(DataSource.isAutoLogin()) {
            mActivity.showLoding()
            viewModel.autoLogin()
        }

        //리스너 등록
        loginEmailTextInputEditText.addTextChangedListener(afterTextChangedListener)
        loginPasswordTextInputEditText.addTextChangedListener(afterTextChangedListener)
        loginButton.setOnClickListener {
            mActivity.showLoding()
            viewModel.login(loginEmailTextInputEditText.text.toString(), loginPasswordTextInputEditText.text.toString())
        }
        loginAutoLoginCheckBox.setOnClickListener {
            viewModel.setAutoLogin(loginAutoLoginCheckBox.isChecked)
            Log.d("Login", "${loginAutoLoginCheckBox.isChecked}")
            Log.d("Login", "${DataSource.isAutoLogin()}")
        }
        resetPasswordButton.setOnClickListener {
            mActivity.startActivity(Intent(mActivity, ResetPasswordActivity::class.java))
        }
        registerButton.setOnClickListener {
            mActivity.startActivity(Intent(mActivity, RegisterActivity::class.java))
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
            if(result == null) {
                return@Observer
            }
            result.apply {
                mActivity.hideLoding()
                AlertDialog.Builder(mActivity).apply {
                    setCancelable(false)
                    (state as LoginResult).apply {
                        isLogin?.let {
                            if(it) {
                                mActivity.startActivity(Intent(mActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                            }
                        }
                    }
                    error?.apply {
                        Log.d("LoginException", "$this")
                        when(this) {
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

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}