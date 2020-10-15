package com.example.afop.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.activity.MyActivity
import com.example.afop.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.lang.Exception
import java.util.*

class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        viewModel =
            ViewModelProvider(this, RegisterViewModelFactory()).get(RegisterViewModel::class.java)
        mActivity = activity as MyActivity
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.emailDataChanged(
                    email = registerEmailTextInputEditText.text.toString()
                )
                viewModel.formDataChanged(
                    name = registerNameTextInputEditText.text.toString(),
                    password = registerPasswordTextInputEditText.text.toString(),
                    verifyPassword = registerVerifyPasswordTextInputEditText.text.toString(),
                    nickName = registerNickNameTextInputEditText.text.toString()
                )
            }
        }

        //리스너 등록
        registerEmailTextInputEditText.addTextChangedListener(afterTextChangedListener)
        registerNameTextInputEditText.addTextChangedListener(afterTextChangedListener)
        registerPasswordTextInputEditText.addTextChangedListener(afterTextChangedListener)
        registerVerifyPasswordTextInputEditText.addTextChangedListener(afterTextChangedListener)
        registerNickNameTextInputEditText.addTextChangedListener(afterTextChangedListener)
        registerDoubleCheckButton.setOnClickListener {
            doubleCheckEmail()
        }
        registerSubmitButton.setOnClickListener {
            formCheck()
        }
        registerEmailTextInputEditText.setOnClickListener {

        }

        //관찰자 등록
        viewModel.emailState.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                registerDoubleCheckButton.isEnabled = isDataValid
                registerEmailTextInputLayout.error = emailError?.let { getString(it) }
            }
        })
        viewModel.formState.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                registerSubmitButton.isEnabled = isDataValid
                registerNameTextInputLayout.error = nameError?.let { getString(it) }
                registerPasswordTextInputLayout.error = passwordError?.let { getString(it) }
                registerVerifyPasswordTextInputLayout.error =
                    verifyPasswordError?.let { getString(it) }
                registerNickNameTextInputLayout.error = nickNameError?.let { getString(it) }
            }
        })
    }

    private fun doubleCheckEmail() {
        mActivity.enableBlock()
        User.auth.signInWithEmailAndPassword(registerEmailTextInputEditText.text.toString(), "0")
            .addOnCompleteListener { task ->
                mActivity.disableBlock()
                AlertDialog.Builder(mActivity).apply {
                    setCancelable(false)
                    when (task.exception) {
                        is FirebaseAuthInvalidUserException -> {
                            setTitle("사용 가능")
                            setMessage("")
                            setPositiveButton("사용하기") { _, _ ->
                                registerEmailTextInputEditText.isEnabled = false
                                registerDoubleCheckButton.visibility = View.GONE
                                registerFormLayout.visibility = View.VISIBLE
                            }
                            setNegativeButton("사용하지 않기") { _, _ ->
                                registerEmailTextInputEditText.setText("")
                            }
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            setTitle("")
                            setMessage("누군가 사용중인 이메일 주소입니다.\n다른 이메일 주소를 입력해주세요.")
                            setPositiveButton("확인") { _, _ ->
                                registerEmailTextInputEditText.setText("")
                            }
                        }
                        else -> {
                            setTitle("오류가 발생했습니다.")
                            setMessage("")
                            setPositiveButton("확인") { _, _ ->
                                Log.d("reg", "${task.exception}")
                                mActivity.finish()
                            }
                        }
                    }
                    show()
                }
            }
        //FirebaseAuthInvalidUserException -> 해당 유저 없음
        //FirebaseAuthInvalidCredentialsException -> 패스워드 맞지 않음
    }

    //회원가입시 블럭!!
    private fun formCheck() {
        AlertDialog.Builder(mActivity).apply {
            setCancelable(false)
            setTitle("")
            setMessage("")
            setPositiveButton(getString(R.string.action_yes)) { _, _ ->
                register()
            }
            setNegativeButton(getString(R.string.action_no)) { _, _ ->
                registerEmailTextInputEditText.setText("")
            }
            show()
        }
    }

    private fun register() {
        mActivity.enableBlock()
        AlertDialog.Builder(mActivity).apply {
            setCancelable(false)
            User.auth.createUserWithEmailAndPassword(
                registerEmailTextInputEditText.text.toString(),
                registerPasswordTextInputEditText.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    User.auth.currentUser?.sendEmailVerification()
                    User.auth.currentUser?.apply {
                        //User.databaseReference.reference.child("Users").child(uid).child("name").setValue(registerNameTextInputEditText.text.toString())
                        //User.databaseReference.reference.child("Users").child(uid).child("nickName").setValue(registerNickNameTextInputEditText.text.toString())
                        //User.databaseReference.reference.child("Users").child(uid).child("registerDate").setValue(Date().time)
                    }
                    setTitle("성공")
                    setMessage("이메일 확인 ㄱㄱ")
                    setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        mActivity.finish()
                    }
                    show()
                } else {
                    setTitle("")
                    setMessage("")
                    setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        mActivity.finish()
                    }
                    show()
                }
            }
        }
        mActivity.disableBlock()
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