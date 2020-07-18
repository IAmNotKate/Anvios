package com.anvios.android.ui.mainmenu.posts.create

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
import kotlinx.android.synthetic.main.fragment_create_post.*

class CreatePostFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private var viewModel: CreatePostViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CreatePostViewModel::class.java)

        compositeDisposable.add(
            viewModel!!.getCreateButtonEnabledObservable()
                .distinctUntilChanged()
                .subscribe { createButton.isEnabled = it }
        )

        setTextWatcherForInputs()

        createButton.setOnClickListener {
            viewModel?.uploadPost(titleField.text.toString(), textField.text.toString())
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun setTextWatcherForInputs() {
        val textWatcher = getTextWatcher()
        titleField.addTextChangedListener(textWatcher)
        textField.addTextChangedListener(textWatcher)
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel?.setCreateButtonEnabled(
                    !(titleField.text.isNullOrBlank() || textField.text.isNullOrBlank())
                )
            }
        }
    }
}