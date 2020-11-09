package com.example.afop.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.data.exception.EmailCheckFailedException
import com.example.afop.data.exception.NickNameCheckFailedException
import com.example.afop.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        viewModel = ViewModelProvider(this, RegisterViewModelFactory()).get(RegisterViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(R.string.title_register)

        binding.fragment = this
        binding.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.registerStateChanged(
                    email = registerEmailTextInputEditText.text.toString(),
                    name = registerNameTextInputEditText.text.toString(),
                    password = registerPasswordTextInputEditText.text.toString(),
                    verifyPassword = registerVerifyPasswordTextInputEditText.text.toString(),
                    nickName = registerNickNameTextInputEditText.text.toString(),
                    state = registerCheckEmailButton
                )
            }
        }

        //관찰자 등록
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                registerCheckEmailButton.isEnabled = isEmailDataValid
                registerEmailTextInputLayout.error = emailError?.let { getString(it) }

                registerSubmitButton.isEnabled = isFormDataValid
                registerNameTextInputLayout.error = nameError?.let { getString(it) }
                registerPasswordTextInputLayout.error = passwordError?.let { getString(it) }
                registerVerifyPasswordTextInputLayout.error = verifyPasswordError?.let { getString(it) }
                registerNickNameTextInputLayout.error = nickNameError?.let { getString(it) }
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
                        (it as RegisterResult).apply {
                            isCheckEmail?.let {
                                setMessage(getString(R.string.dialog_message_can_email))
                                setPositiveButton(getString(R.string.action_use)) { _, _ ->
                                    registerEmailTextInputEditText.isEnabled = false
                                    registerCheckEmailButton.visibility = View.GONE
                                    registerFormLayout.visibility = View.VISIBLE
                                }
                                setNegativeButton(getString(R.string.action_not_use)) { _, _ ->
                                    registerEmailTextInputEditText.setText("")
                                }
                            }
                            isCheckNickName?.let {
                                setTitle(getString(R.string.dialog_title_register_fail))
                                setMessage(getString(R.string.dialog_message_cant_nickname))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    registerNickNameTextInputEditText.setText("")
                                }
                            }
                            isRegister?.let {
                                setMessage(
                                    "${getString(R.string.dialog_message_success_request)}\n${
                                        getString(R.string.dialog_message_register_email_verify)
                                    }"
                                )
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    mActivity.finish()
                                }
                            }
                        }
                    }
                    error?.apply {
                        when (this) {
                            is EmailCheckFailedException -> {
                                setTitle(getString(R.string.dialog_title_register_fail))
                                setMessage(getString(R.string.dialog_message_cant_email))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    registerEmailTextInputEditText.setText("")
                                }
                            }
                            is NickNameCheckFailedException -> {
                                setTitle(getString(R.string.dialog_title_register_fail))
                                setMessage(getString(R.string.dialog_message_cant_nickname))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    registerNickNameTextInputEditText.setText("")
                                }
                            }
                            is FirebaseAuthUserCollisionException -> {
                                setTitle(getString(R.string.dialog_title_register_fail))
                                setMessage(getString(R.string.dialog_message_cant_email))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    Log.d("reg", "$error")
                                }
                            }
                            else -> {
                                setTitle(getString(R.string.dialog_title_register_fail))
                                setMessage(getString(R.string.dialog_message_unknown_error))
                                setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                    Log.d("reg", "$error")
                                    mActivity.finish()
                                }
                            }
                        }
                    }
                }.show()
            }
        })
    }

    fun checkEmail(view: View) {
        mActivity.showLoading()
        viewModel.checkEmail(registerEmailTextInputEditText.text.toString())
    }

    fun checkForm(view: View) {
        AlertDialog.Builder(mActivity).apply {
            setMessage(
                "${getString(R.string.dialog_message_submit_register_form)}\n" +
                        "Email : ${registerEmailTextInputEditText.text.toString()}\n" +
                        "Name : ${registerNameTextInputEditText.text.toString()}\n" +
                        "NickName : ${registerNickNameTextInputEditText.text.toString()}"
            )
            setPositiveButton(getString(R.string.action_register)) { _, _ ->
                mActivity.showLoading()
                viewModel.register(
                    email = registerEmailTextInputEditText.text.toString(),
                    name = registerNameTextInputEditText.text.toString(),
                    password = registerPasswordTextInputEditText.text.toString(),
                    verifyPassword = registerVerifyPasswordTextInputEditText.text.toString(),
                    nickName = registerNickNameTextInputEditText.text.toString()
                )
            }
            setNegativeButton(getString(R.string.action_cancel)) { _, _ -> }
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}

//https://github.com/firebase/quickstart-android/blob/c1bef4ad0b96094c8693014d3bae35532ed70441/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin/EmailPasswordActivity.kt#L25