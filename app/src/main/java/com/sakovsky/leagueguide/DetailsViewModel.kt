package com.sakovsky.leagueguide

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakovsky.leagueguide.repository.ChampionDetail
import com.sakovsky.leagueguide.repository.ChampionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    private val championRepository = ChampionRepository()

    private val mutableChampionDetailData = MutableLiveData<UiState<Map<String, ChampionDetail>>?>()
    private val immutableChampionDetailData: MutableLiveData<UiState<Map<String, ChampionDetail>>?> = mutableChampionDetailData
    val immutableChampionDetail: LiveData<UiState<List<ChampionDetail>>> = MediatorLiveData<UiState<List<ChampionDetail>>>().apply {
        addSource(immutableChampionDetailData) { championDetailUiState ->
            val championsList = championDetailUiState?.values?.values?.toList() ?: emptyList()
            value = UiState(values = championsList)
        }
    }
    fun getData(id: String){

        viewModelScope.launch(Dispatchers.IO) {
            try{
                val request = championRepository.getChampionDetailResponse(id)
                val champions = request.body()?.data
                if (champions != null) {
                    for (champion in champions) {
                        Log.d("Name:", "${champion}, ")
                    }
                }
                mutableChampionDetailData.postValue(UiState(values = champions))
            }
            catch(e: Exception){
                Log.e("DetailsViewModel", "Operation failed", e)
            }
        }
    }
}