package com.hanna.test.rimtest.viewmodels

import androidx.lifecycle.*
import com.hanna.test.rimtest.repositories.ForecastRepository
import com.hanna.test.rimtest.utils.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ForecastViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {

    private val fetchForecast = MutableLiveData(true)

    val stopInfo = Transformations.switchMap(fetchForecast) {
        return@switchMap forecastRepository.fetchForecast().asLiveData()
    }

    fun refreshForecast() {
        fetchForecast.postValue(true)
    }
}

class ForecastViewModelFactory @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForecastViewModel(forecastRepository) as T
    }

}