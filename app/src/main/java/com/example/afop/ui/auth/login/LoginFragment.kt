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
import com.example.afop.data.model.User
import com.google.firebase.auth.FirebaseAuth
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
        viewModel = ViewModelProvider(
            viewModelStore,
            LoginViewModelFactory()
        ).get(LoginViewModel::class.java)
        mActivity = activity as MyActivity
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

        //리스너 등록
        loginEmailTextInputEditText.addTextChangedListener(afterTextChangedListener)
        loginPasswordTextInputEditText.addTextChangedListener(afterTextChangedListener)
        loginButton.setOnClickListener {
            login()
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
    }

    private fun login() {/*
        mActivity.enableBlock()
        User.auth.signInWithEmailAndPassword(loginEmailTextInputEditText.text.toString(), loginPasswordTextInputEditText.text.toString()).addOnCompleteListener { task ->
            mActivity.disableBlock()
            if(task.isSuccessful) {
                if (User.auth.currentUser?.isEmailVerified!!) {
                    mActivity.startActivity(Intent(mActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                } else {
                    AlertDialog.Builder(mActivity).apply {
                        setCancelable(false)
                        setMessage(getString(R.string.dialog_message_need_email_verify))
                        setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        }
                        show()
                    }
                }
            } else {
                AlertDialog.Builder(mActivity).apply {
                    setCancelable(false)
                    setMessage(getString(R.string.dialog_message_error_login_fail))
                    setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        loginEmailTextInputEditText.setText("")
                        loginPasswordTextInputEditText.setText("")
                    }
                    show()
                }
            }
        }
        */
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