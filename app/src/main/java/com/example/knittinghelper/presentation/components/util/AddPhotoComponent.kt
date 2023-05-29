package com.example.knittinghelper.presentation.components.util

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.knittinghelper.R


@Composable
fun AddPhotoComponent(selectedPhoto: MutableState<Uri?>) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        selectedPhoto.value = it
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(selectedPhoto.value != null) {
            AsyncImage(
                model = selectedPhoto.value,
                contentDescription = "kek",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(150.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "kek",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(150.dp)
            )
        }
        Button(
            onClick = { galleryLauncher.launch("image/*") },
        ) {
            Text(text = "Выбрать фото")
        }
    }
}

@Composable
fun UpdatePhotoComponent(selectedPhoto: MutableState<Uri?>, photoUri: Uri) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        selectedPhoto.value = it
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(selectedPhoto.value != null) {
            AsyncImage(
                model = selectedPhoto.value,
                contentDescription = "kek",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(150.dp)
            )
        } else {
            AsyncImage(
                model = photoUri,
                contentDescription = "kek",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(150.dp)
            )
        }
        Button(
            onClick = { galleryLauncher.launch("image/*") },
        ) {
            Text(text = "Выбрать фото")
        }
    }
}


@Composable
fun AddPhotosComponent(selectedPhotos: MutableState<List<Uri?>>) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) {
        selectedPhotos.value = it
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(90.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedPhotos.value.isNotEmpty()) {
                selectedPhotos.value.forEach { photo ->
                   AsyncImage(
                      model = photo,
                      contentDescription = "kek",
                      contentScale = ContentScale.FillBounds,
                      modifier = Modifier
                          .padding(start = 4.dp, end = 4.dp)
                          .size(90.dp)
                   )
                }
            } else {
                TextButton(onClick = { galleryLauncher.launch("image/*") }) {
                    Text(text = "Выберите фото!")
                }
            }
        }
        Button(
            modifier = Modifier.padding(top = 12.dp),
            onClick = { galleryLauncher.launch("image/*") },
        ) {
            Text(text = "Выбрать фото")
        }
    }
}