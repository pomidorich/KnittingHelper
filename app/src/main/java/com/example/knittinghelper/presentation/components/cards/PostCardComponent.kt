package com.example.knittinghelper.presentation.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.knittinghelper.R
import com.example.knittinghelper.domain.model.Post
import com.example.knittinghelper.domain.model.User
import com.example.knittinghelper.presentation.components.util.PhotoCards
import com.example.knittinghelper.util.Needles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PostCardComponent(post: Post, navController: NavController, user: Boolean, userInfo: User) {
    val menu = remember { mutableStateOf(false) }

    val date = Date(post.postDate.seconds * 1000L)
    val format = SimpleDateFormat("HH:mm dd/MM/yy", Locale.getDefault())
    val dateString = format.format(date)

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp, pressedElevation = 3.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        onClick = {
                            if (!user) {
                                if (userInfo.following.contains(post.userId))
                                    navController.navigate("social/${post.userId}/true")
                                else
                                    navController.navigate("social/${post.userId}/false")
                            }
                        }
                    )
                ) {
                    if (post.userPhotoUri != "") {
                        AsyncImage(
                            model = post.userPhotoUri,
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.photo),
                            contentDescription = "Project Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = post.userName,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                if (user) {
                    Box {
                        IconButton(onClick = {
                            menu.value = !menu.value
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "Localized description"
                            )
                        }
                        DropdownMenu(
                            expanded = menu.value,
                            onDismissRequest = {
                                menu.value = false
                            },
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Удалить проект") },
                                onClick = {
                                    menu.value = false
//                                delete.value = part
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.DeleteOutline,
                                        contentDescription = "nav_icon",
                                    )
                                }
                            )
                        }
                    }
                }
            }
            PhotoCards(post.photoUris)
            Text(text = post.text, modifier = Modifier.padding(bottom = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = Needles.chooseNeedle(post.needle).icon,
                    contentDescription = "image",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(text = dateString, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}