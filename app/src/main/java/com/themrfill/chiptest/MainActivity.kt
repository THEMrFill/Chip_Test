package com.themrfill.chiptest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.themrfill.chiptest.ui.theme.ChipTestTheme
import com.themrfill.chiptest.ui.theme.primaryColor
import com.themrfill.chiptest.ui.theme.primaryColorDisabled
import com.themrfill.chiptest.ui.theme.secondaryColor
import com.themrfill.chiptest.ui.theme.secondaryColorDisabled
import com.themrfill.chiptest.vm.DogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val dogViewModel: DogViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                Text(stringResource(R.string.select_dog))
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(dogViewModel.textVisible)
                        ErrorMessage(dogViewModel.showError)
                        DogList(dogViewModel.breedList)
                    }
                }
            }
        }
    }
}

@Composable
fun DogList(dogs: SnapshotStateList<String>) {
    LazyColumn {
        items(dogs.size) { dog ->
            DogCard(dogs[dog])
        }
    }
}

@Composable
fun DogCard(data: String) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.inner_padding))) {
        Button(
            onClick = { showDogImages(data, context) },
            colors = ButtonColors(
                containerColor = primaryColor,
                contentColor = secondaryColor,
                disabledContainerColor = primaryColorDisabled,
                disabledContentColor = secondaryColorDisabled,
            )
        ) {
            Text(text = data)
        }
    }
}

fun showDogImages(data: String, context: Context) {
    val intent = Intent(context, ImagesActivity::class.java)
    intent.putExtra("dog", data)
    context.startActivity(intent)
}

@Composable
fun Greeting(textVisible: MutableTransitionState<Boolean>) {
    AnimatedVisibility(visibleState = textVisible) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.padding(dimensionResource(R.dimen.inner_padding)),
            )
            Text(
                text = stringResource(R.string.please_wait),
                modifier = Modifier.padding(dimensionResource(R.dimen.inner_padding)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChipTestTheme {
        Greeting(MutableTransitionState(true))
    }
}