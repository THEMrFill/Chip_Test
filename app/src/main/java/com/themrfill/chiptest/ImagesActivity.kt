package com.themrfill.chiptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.themrfill.chiptest.ui.theme.ChipTestTheme
import com.themrfill.chiptest.ui.theme.primaryColor
import com.themrfill.chiptest.ui.theme.secondaryColor
import com.themrfill.chiptest.vm.ImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class ImagesActivity: ComponentActivity() {
    private val imagesViewModel: ImagesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent?.let { bundle ->
            bundle.getStringExtra("dog").toString()
        } ?: run { "" }

        setContent {
            ChipTestTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = primaryColor,
                                titleContentColor = secondaryColor,
                            ),
                            title = {
                                Text(stringResource(R.string.dog_name, dog))
                            },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = stringResource(R.string.back_button),
                                        tint = secondaryColor,
                                    )
                                }
                            },
                        )
                    }
                ) { innerPadding ->
                    InnerScroller(innerPadding)
                }
            }
        }
        imagesViewModel.fetchImageList(dog)
    }
}

@Composable
fun InnerScroller(innerPadding: PaddingValues) {
    val imagesViewModel = viewModel<ImagesViewModel>()
    ChipTestTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            ErrorMessage(imagesViewModel.showError)
            ImagesList(imagesViewModel.imageList)
        }
    }
}

@Composable
fun ImagesList(images: SnapshotStateList<String>) {
    LazyColumn {
        items(images.size) { image ->
            AsyncImage(
                model = images[image],
                modifier = Modifier.padding(dimensionResource(R.dimen.inner_padding)),
                contentDescription = stringResource(R.string.dog_image),
            )
        }
    }
}

@Composable
fun ErrorMessage(textVisible: MutableTransitionState<Boolean>) {
    AnimatedVisibility(visibleState = textVisible) {
        Text(
            text = stringResource(R.string.load_error),
            modifier = Modifier.padding(dimensionResource(R.dimen.inner_padding)),
        )
    }
}