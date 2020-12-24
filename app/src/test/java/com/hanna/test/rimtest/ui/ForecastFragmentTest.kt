package com.hanna.test.rimtest.ui

import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.hanna.test.rimtest.R
import com.hanna.test.rimtest.datasource.Resource
import com.hanna.test.rimtest.entities.StopInfoResponse
import com.hanna.test.rimtest.entities.Tram
import com.hanna.test.rimtest.extensions.replace
import com.hanna.test.rimtest.viewmodels.ForecastViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class TramForecastFragmentTest {

    lateinit var scenario: FragmentScenario<ForecastFragment>
    lateinit var factory: TramForecastFragmentTestFactory
    private val stopInfoResponse = Resource.success(StopInfoResponse())
    private val listValue = MutableLiveData(stopInfoResponse)
    private val viewModelMock: ForecastViewModel = mock {
        onBlocking { stopInfo } doReturn listValue
    }

    @Before
    fun setupScenario() {
        factory = TramForecastFragmentTestFactory(viewModelMock)
        scenario = FragmentScenario.launchInContainer(
            ForecastFragment::class.java,
            null, R.style.Theme_AppCompat_NoActionBar, factory
        )
    }

    @Test
    fun `when stop info resource is successful, list adapter item count, is equals to tram count`() {
        val data = stopInfoResponse.data!!
        data.trams = listOf(Tram("destination", "2"))

        listValue.value = Resource.success(data)
        scenario.onFragment {
            val rv = it.view!!.findViewById<RecyclerView>(R.id.tram_list)
            assertThat(rv!!.adapter?.itemCount).isEqualTo(1)
        }
    }

    @Test
    fun `when stopInfo resource is successful, ViewStates_MAIN resource id is visible`() {
        listValue.value = stopInfoResponse
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.main_view)
            assertThat(mainView.isVisible).isTrue()
        }
    }


    @Test
    fun `when stopInfo resource is loading with no data, ViewStates_LOADING resource id is visible`() {
        listValue.value = Resource.loading(null)
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.loading_view)
            assertThat(mainView.isVisible).isTrue()
        }
    }

    @Test
    fun `when stopInfo resource is error with no data, ViewStates_LOADING resource id is visible`() {
        listValue.value = Resource.error("An error occurred", null)
        scenario.onFragment {
            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("An error occurred")
        }
    }

}

class TramForecastFragmentTestFactory constructor(
    var viewModelMock: ForecastViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        ForecastFragment().apply {
            replace(ForecastFragment::viewModel, viewModelMock)
        }
}