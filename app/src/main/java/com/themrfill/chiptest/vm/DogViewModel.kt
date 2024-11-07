package com.themrfill.chiptest.vm

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.themrfill.chiptest.api.DogService
import com.themrfill.chiptest.utils.caps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogViewModel(private val dogService: DogService): ViewModel() {
    private val TAG = "DogViewModel"
    val breedList = mutableStateListOf<String>()
    val textVisible = MutableTransitionState(true)
    val showError = MutableTransitionState(false)

    init {
        fetchDogList()
    }

    private fun fetchDogList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = dogService.getBreeds().execute()
            if (response.isSuccessful) {
                response.body()?.message?.let { breeds ->
                    breedList.clear()
                    textVisible.targetState = breeds.isEmpty()
                    for (breed in breeds) {
                        if (breed.value.isEmpty()) {
                            breedList.add(breed.key.caps())
                        } else {
                            for (subBreed in breed.value) {
                                breedList.add("${subBreed.caps()} ${breed.key.caps()}")
                            }
                        }
                    }
                }
            } else {
                textVisible.targetState = false
                showError.targetState = true
                Log.e(TAG, "call failed, ${response.raw().code}")
            }
        }
    }
}