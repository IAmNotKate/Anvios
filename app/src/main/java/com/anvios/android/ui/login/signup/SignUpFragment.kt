package com.anvios.android.ui.login.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.anvios.android.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private var viewModel: SignUpViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        setTextWatcherForInputs()

        compositeDisposable.add(viewModel!!.getSignUpButtonEnabledObservable()
            .distinctUntilChanged()
            .subscribe { signUpButton.isEnabled = it })

        signUpButton.setOnClickListener {
            viewModel?.signUp(
                usernameField.text.toString(),
                nameField.text.toString(),
                surnameField.text.toString(),
                emailField.text.toString(),
                passwordField.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun setTextWatcherForInputs() {
        val textWatcher = getTextWatcher()
        usernameField.addTextChangedListener(textWatcher)
        emailField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)
        nameField.addTextChangedListener(textWatcher)
        surnameField.addTextChangedListener(textWatcher)
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel?.setSignUpButtonEnabled(
                    !(usernameField.text.isNullOrBlank() ||
                            nameField.text.isNullOrBlank() ||
                            surnameField.text.isNullOrBlank() ||
                            emailField.text.isNullOrBlank() ||
                            passwordField.text.isNullOrBlank())
                )
            }
        }
    }
}