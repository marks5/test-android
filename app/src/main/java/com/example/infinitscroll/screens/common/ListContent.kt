package com.example.infinitscroll.screens.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.infinitscroll.R
import com.example.infinitscroll.domain.Repo

@ExperimentalCoilApi
@Composable
fun ListContent(items: LazyPagingItems<Repo>) {
    Log.d("Error", items.loadState.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { repo ->
                repo.id
            }
        ) { unsplashImage ->
            unsplashImage?.let { RepoItem(repo = it) }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun RepoItem(repo: Repo) {
    val painter = rememberImagePainter(data = repo.owner.avatarUrl) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
    }
    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Avatar Image",
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Author: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append(repo.fullName)
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
//            LikeCounter(
//                modifier = Modifier.weight(3f),
//                painter = painterResource(id = R.drawable.ic_heart),
//                likes = "${unsplashImage.likes}"
//            )
        }
    }
}

//@Composable
//fun LikeCounter(
//    modifier: Modifier,
//    painter: Painter,
//    likes: String
//) {
//    Row(
//        modifier = modifier.fillMaxSize(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.End
//    ) {
//        Icon(
//            painter = painter,
//            contentDescription = "Heart Icon",
//            tint = HeartRed
//        )
//        Divider(modifier = Modifier.width(6.dp))
//        Text(
//            text = likes,
//            color = Color.White,
//            fontSize = MaterialTheme.typography.subtitle1.fontSize,
//            fontWeight = FontWeight.Bold,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
//}
//
//@ExperimentalCoilApi
//@Composable
//@Preview
//fun UnsplashImagePreview() {
//    RepoItem(
//        repo = Repo(
//            id = "1",
//            url = "",
//        )
//    )
//}