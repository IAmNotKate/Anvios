package com.anvios.android.ui.login.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.anvios.android.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private var viewModel: SignInViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        compositeDisposable.add(
            viewModel!!.getSignInButtonEnabledObservable()
                .distinctUntilChanged()
                .subscribe { signInButton.isEnabled = it }
        )

        setTextWatcherForInputs()

        signUpSuggestionButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_navigate_sign_up)
        )

        signInButton.setOnClickListener {
            viewModel?.signIn(usernameField.text.toString(), passwordField.text.toString())
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun setTextWatcherForInputs() {
        val textWatcher = getTextWatcher()
        usernameField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel?.setSignInButtonEnabled(
                    !(usernameField.text.isNullOrBlank() || passwordField.text.isNullOrBlank())
                )
            }
        }
    }
}