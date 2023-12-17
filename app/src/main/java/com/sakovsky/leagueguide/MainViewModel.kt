package com.sakovsky.leagueguide

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakovsky.leagueguide.repository.Champion
import com.sakovsky.leagueguide.repository.ChampionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val championRepository = ChampionRepository()

    private val mutableChampionsData = MutableLiveData<UiState<Map<String, Champion>>?>()
    private val immutableChampionsData: MutableLiveData<UiState<Map<String, Champion>>?> = mutableChampionsData
    val immutableChampions: LiveData<UiState<List<Champion>>> = MediatorLiveData<UiState<List<Champion>>>().apply {
        addSource(immutableChampionsData) { championsUiState ->
            val championsList = championsUiState?.values?.values?.toList() ?: emptyList()
            value = UiState(values = championsList)
        }
    }
    fun getData(){

        viewModelScope.launch(Dispatchers.IO) {
            try{
                val request = championRepository.getChampionResponse()
                val champions = request.body()?.data
                if (champions != null) {
                    for (champion in champions) {
                        Log.d("Name:", "${champion}, ")
                    }
                }
                mutableChampionsData.postValue(UiState(values = champions))
            }
            catch(e: Exception){
                Log.e("MainViewModel", "Operation failed", e)
            }
        }
    }
}
