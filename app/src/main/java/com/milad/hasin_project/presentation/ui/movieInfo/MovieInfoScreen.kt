package com.milad.hasin_project.presentation.ui.movieInfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.tmdb_client.core.model.Genres

private val headerHeight = 250.dp
private val toolbarHeight = 56.dp

@Composable
fun MovieInfoScreen(
    viewModel: MovieInfoViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var state by rememberSaveable {
        mutableStateOf<MovieInfoState>(MovieInfoState.Loading)
    }
    LaunchedEffect(true) {
        if (lifecycle.currentState == Lifecycle.State.STARTED)
            viewModel.state.collect { state = it }
    }

    when (state) {
        is MovieInfoState.Success -> {
            MovieListScreen(
                movieInfo = (state as MovieInfoState.Success).data,
                {
                    navController.popBackStack()
                }
            )
        }
        is MovieInfoState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "Loading Movies Info"
                )

                CircularProgressIndicator(color = Color.Red)
            }
        }
        is MovieInfoState.Error -> {
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
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Retry again",
                    )
                }
            }
        }
    }
}

/**
 * we for implement this part of project got help from here:
 * @see <a href="https://medium.com/@morad.azzouzi/collapsing-toolbar-with-parallax-effect-and-curve-motion-in-jetpack-compose-9ed1c3c0393f">Medium</a>
 */
@Composable
fun MovieListScreen(
    movieInfo: FullMovieDetail,
    onBack: () -> Unit = {},
    onShare: () -> Unit = {},
    onFavorite: () -> Unit = {}
) {
    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Header(
            backdropPath = movieInfo.backdropPath,
            movieTitle = movieInfo.title,
            scroll = scroll,
            headerHeightPx = headerHeightPx
        )

        Body(movieInfo, scroll)

        Toolbar(
            movieInfo.title,
            scroll,
            headerHeightPx,
            toolbarHeightPx,
            onBack = onBack,
            onShare = onShare,
            onFavorite = onFavorite
        )
    }
}

@Composable
private fun Header(
    scroll: ScrollState,
    headerHeightPx: Float,
    backdropPath: String,
    movieTitle: String
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(headerHeight)
        .graphicsLayer {
            translationY = -scroll.value.toFloat() * 1.4F
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropPath.toBackdropUrl())
                .crossfade(true).build(),

            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight),
            contentScale = ContentScale.FillBounds,
            contentDescription = movieTitle,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    movieTitle: String,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    onBack: () -> Unit = {},
    onShare: () -> Unit = {},
    onFavorite: () -> Unit = {}
) {
    val toolbarBottom = headerHeightPx - (2 * toolbarHeightPx)
    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }
    val transition = updateTransition(targetState = showToolbar, label = "")
    val color by transition.animateColor(label = "") { visible ->
        if (visible)
            Color.DarkGray
        else
            Color.Transparent
    }

    TopAppBar(
        modifier = Modifier.background(
            color
        ),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "null",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(content = {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )

            }, onClick = onShare)
            IconButton(content = {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.White
                )

            }, onClick = onFavorite)
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ), title = {
            AnimatedVisibility(
                visible = showToolbar,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Text(
                    text = movieTitle,
                    color = Color.White
                )
            }
        }
    )
}

@Composable
private fun Body(
    movie: FullMovieDetail,
    scroll: ScrollState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight - toolbarHeight))

        HeadLine(
            posterPath = movie.posterPath,
            title = movie.title,
            genres = movie.genres
        ) {/*TODO*/ }

        GeneralInfo(
            originalLanguage = movie.originalLanguage,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage.toString(),
            voteCount = movie.voteCount.toString()
        )
        Overview(
            overview = movie.overview
        )
    }
}

@Composable
private fun HeadLine(
    posterPath: String,
    title: String,
    genres: List<Genres>,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ElevatedCard(
            modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            shape = CardDefaults.elevatedShape
        ) {
            AsyncImage(
                model = posterPath.makePosterURL(),
                contentDescription = title
            )
        }
        Column(modifier = Modifier.padding(top = toolbarHeight, bottom = 16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            FlowRow(
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 2.dp,
                mainAxisAlignment = MainAxisAlignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                genres.forEach {
                    ChipItem(genres = it) { id -> onClick.invoke(id) }
                }
            }
        }
    }
}

@Composable
fun GeneralInfo(
    voteAverage: String,
    originalLanguage: String,
    releaseDate: String,
    voteCount: String
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Divider(color = Color.Gray, thickness = 1.dp)

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = voteAverage,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        tint = Color.Gray,
                        contentDescription = "Voit"
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = voteCount,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        "Votes",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        tint = Color.Gray,
                        contentDescription = "Language"
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = originalLanguage,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Language",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WatchLater,
                        tint = Color.Gray,
                        contentDescription = "null"
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = releaseDate,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Release date",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}

@Composable
fun Overview(overview: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = overview,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
private fun ChipItem(genres: Genres, onClick: (Int) -> Unit) {
    AssistChip(
        onClick = { onClick.invoke(genres.id) },
        label = {
            Text(
                text = genres.name,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray
            )
        })
}

fun String.makePosterURL(): String = "https://image.tmdb.org/t/p/w342$this"

fun String.toBackdropUrl() = "https://image.tmdb.org/t/p/original$this"

