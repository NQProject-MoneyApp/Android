package com.nqproject.MoneyApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.ui.navigation.MainNavigation
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    MainContent()

                    MainNavigation()
                }
            }
        }
    }
}

data class ItemModel(
    val name: String,
    var checked: Boolean
)

@Composable
fun Screen() {

    val items = remember {
        mutableStateOf(listOf(
            ItemModel("Item 1", false),
            ItemModel("Item 2", true),
        ))
    }

    Column() {
        items.value.forEachIndexed { index, item ->

            ListItem(name = item.name, checked = item.checked) {

                // Option 1
                items.value[index].checked = !items.value[index].checked
                items.value = items.value

                // Option 2
//                items.value = items.value.mapIndexed { mappedIndex, value ->
//                    if(mappedIndex == index) {
//                        value.copy(checked = !item.checked)
//                    }
//                    else {
//                        value
//                    }
//                }
            }
        }
    }

}


@Composable
fun ListItem(name: String, checked: Boolean, onClick: () -> Unit) {



}



@Composable
fun MainContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painterResource(id = R.drawable.ic_icon),
            modifier = Modifier.size(100.dp),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "MoneyApp",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary,)
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = {}) {
            Text("Test")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoneyAppTheme {
        MainContent()
    }
}