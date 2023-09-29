package com.milad.hasin_project.presentation.ui.movieList

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.milad.hasin_project.R
import com.milad.hasin_project.domain.model.Movie
import com.milad.hasin_project.presentation.ui.movieList.utils.makePosterURL

@SuppressLint("RememberReturnType", "StateFlowValueCalledInComposition")
@Composable
fun MovieListScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    MovieListScreen(movieList = viewModel.uiState.value.collectAsLazyPagingItems()) {
        navController.navigate("movie/$it")
    }
}

@Composable
internal fun MovieListScreen(movieList: LazyPagingItems<Movie>, onItemClick: (Int) -> Unit) {

    Scaffold(
        topBar = { MyTopAppBar("Popular") {/*TODO*/ } },
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues = paddingValues),
            movieList = movieList,
            onClick = onItemClick
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier, movieList: LazyPagingItems<Movie>, onClick: (Int) -> Unit
) {
    MovieLazyList(
        modifier = modifier, movieList = movieList, onClick = onClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTopAppBar(
    title: String, onClick: () -> Unit
) {
    TopAppBar(title = { Text(title) }, colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = Color.Transparent,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    ), actions = {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.sort), contentDescription = "Sort"
            )
        }
    })
}

@Composable
private fun MovieLazyList(
    modifier: Modifier = Modifier, movieList: LazyPagingItems<Movie>, onClick: (Int) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = modifier) {
        items(movieList.itemCount) { index ->
            Row(
                modifier = Modifier.padding(2.dp),
            ) {
                movieList[index]?.let {
                    MovieListItem(
                        movie = it,
                        onClick = onClick,
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }
        when (movieList.loadState.append) {
            is LoadState.Error -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { movieList.retry() }
                        ) {
                            Text(
                                text = "Retry again",
                            )
                        }
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(color = Color.Red)
                    }
                }
            }
            else -> {}
        }
    }

    when (movieList.loadState.refresh) {
        is LoadState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "We couldn't fetch popular movies. Please try again.",
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = { movieList.retry() }
                ) {
                    Text(
                        text = "Retry again",
                    )
                }
            }
        }
        is LoadState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "Loading Popular Movies"
                )

                CircularProgressIndicator(color = Color.Red)
            }
        }
        else -> {}
    }
}

@Composable
private fun MovieListItem(
    movie: Movie, modifier: Modifier = Modifier, onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick.invoke(movie.id) },
        shape = CutCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterPath?.makePosterURL())
                    .crossfade(true).build(),
                contentDescription = movie.title,
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.TopCenter,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = movie.title,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}