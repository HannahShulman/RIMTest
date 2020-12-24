package com.hanna.test.rimtest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hanna.test.rimtest.R
import com.hanna.test.rimtest.datasource.Status
import com.hanna.test.rimtest.extensions.provideViewModel
import com.hanna.test.rimtest.utils.ViewStates
import com.hanna.test.rimtest.viewmodels.ForecastViewModel
import com.hanna.test.rimtest.viewmodels.ForecastViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.forecast_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.forecast_fragment) {

    private val adapter = TramsAdapter()

    private val viewState: ViewStates by lazy {
        ViewStates(requireView())
    }

    @Inject
    lateinit var factory: ForecastViewModelFactory
    val viewModel: ForecastViewModel by provideViewModel { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tram_list.adapter = adapter
        refresh_btn.setOnClickListener { viewModel.refreshForecast() }
        viewModel.stopInfo.observe(viewLifecycleOwner, {
            val state = when (it.status) {
                Status.LOADING -> {
                    ViewStates.ViewState.LOADING
                }
                Status.SUCCESS -> {
                    stop_tv.text = it.data?.stop
                    adapter.submitList(it.data?.trams)
                    ViewStates.ViewState.MAIN
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message ?: "An error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                    ViewStates.ViewState.ERROR
                }
            }
            viewState.setState(state)
        })
    }
}