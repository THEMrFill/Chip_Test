package com.themrfill.chiptest.vm

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.themrfill.chiptest.api.DogService
import com.themrfill.chiptest.utils.toBreedName
import com.themrfill.chiptest.utils.toSubBreedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesViewModel(private val dogService: DogService): ViewModel() {
    private val TAG = "ImagesViewModel"
    val imageList = mutableStateListOf<String>()
    val showError = MutableTransitionState(false)

    fun fetchImageList(dog: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val breed = dog.toBreedName()
            val subBreed = dog.toSubBreedName()
            val response = if (subBreed.isBlank()) {
                dogService.getBreedImages(breed).execute()
            } else {
                dogService.getSubBreedImages(breed, subBreed).execute()
            }
            if (response.isSuccessful) {
                response.body()?.message?.let { images ->
                    imageList.clear()
                    if (images.size <= 10) {
                        for (image in images) {
                            imageList.add(image)
                        }
                    } else {
                        val randomIds = HashSet<Int>()
                        while (randomIds.size < 10) {
                            val rand = (0..<images.size).random()
                            if (!randomIds.contains(rand)) {
                                randomIds.add(rand)
                            }
                        }
                        for (i in randomIds) {
                            imageList.add(images[i])
                        }
                    }
                }
            } else {
                showError.targetState = true
                Log.e(TAG, "call failed, ${response.raw().code}")
            }
        }
    }
}