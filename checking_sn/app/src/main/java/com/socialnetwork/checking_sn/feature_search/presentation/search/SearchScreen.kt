package com.socialnetwork.checking_sn.feature_search.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.ui.theme.Spacing

// App background color to match HomeScreen
val AppBackgroundColor = Color(0xFFF5F5F7)

// Data classes for search results
data class SearchResult(
    val id: String,
    val name: String,
    val username: String,
    val avatarUrl: String? = null,
    val isUser: Boolean = true,
    val postSnippet: String? = null,
    val timestamp: Long? = null,
    val followersCount: Int? = null
)

// Mock data
val mockUsers = listOf(
    SearchResult("1", "John Doe", "@johndoe", null, true, null, null, 1250),
    SearchResult("2", "Jane Smith", "@janesmith", null, true, null, null, 890),
    SearchResult("3", "Mike Johnson", "@mikej", null, true, null, null, 2341),
    SearchResult("4", "Sarah Wilson", "@sarahw", null, true, null, null, 567),
    SearchResult("5", "Emma Davis", "@emmad", null, true, null, null, 1456)
)

val mockPosts = listOf(
    SearchResult("6", "Tech News", "@technews", null, false, "Exciting updates in Android development...", System.currentTimeMillis() - 2 * 60 * 60 * 1000), // 2 hours ago
    SearchResult("7", "Mobile Dev", "@mobiledev", null, false, "Jetpack Compose is amazing for UI development!", System.currentTimeMillis() - 5 * 60 * 60 * 1000), // 5 hours ago
    SearchResult("8", "Design Studio", "@designstudio", null, false, "New design trends for 2024", System.currentTimeMillis() - 24 * 60 * 60 * 1000), // 1 day ago
    SearchResult("9", "Food Blog", "@foodblog", null, false, "Best recipes for healthy eating", System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000) // 2 days ago
)

val recentSearches = listOf("Android", "Compose", "Kotlin", "Jetpack")
val trendingSearches = listOf("AI", "Machine Learning", "Blockchain", "Web3", "Sustainable Tech")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onResultClick: (SearchResult) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    // Filter results based on search query
    val filteredResults by remember(searchQuery) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                emptyList()
            } else {
                (mockUsers + mockPosts).filter { result ->
                    result.name.contains(searchQuery, ignoreCase = true) ||
                    result.username.contains(searchQuery, ignoreCase = true) ||
                    (result.postSnippet?.contains(searchQuery, ignoreCase = true) == true)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = AppBackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppBackgroundColor)
        ) {
            // Search input with clear button
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search posts, users…") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                )
            )

            // Show recent searches or trending when search is empty
            if (searchQuery.isBlank()) {
                RecentSearchesSection()
                TrendingSearchesSection()
            } else {
                // Show search results or no results message
                if (filteredResults.isEmpty()) {
                    NoResultsFound()
                } else {
                    SearchResultsList(filteredResults, onResultClick)
                }
            }
        }
    }
}

@Composable
fun RecentSearchesSection() {
    Column(modifier = Modifier.padding(horizontal = Spacing.Medium)) {
        Text(
            text = "Recent",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = Spacing.Small)
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            recentSearches.forEach { search ->
                AssistChip(
                    onClick = { /* Handle chip click */ },
                    label = { Text(search) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun TrendingSearchesSection() {
    Column(modifier = Modifier.padding(horizontal = Spacing.Medium, vertical = Spacing.Medium)) {
        Text(
            text = "Trending",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = Spacing.Small)
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            trendingSearches.forEach { search ->
                AssistChip(
                    onClick = { /* Handle chip click */ },
                    label = { Text(search) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun NoResultsFound() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "No results",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Spacing.Medium))
            Text(
                text = "No results found",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(Spacing.Small))
            Text(
                text = "Try adjusting your search to find what you're looking for",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SearchResultsList(results: List<SearchResult>, onResultClick: (SearchResult) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Small)
    ) {
        items(results) { result ->
            SearchResultItem(result = result, onClick = { onResultClick(result) })
        }
    }
}

@Composable
fun SearchResultItem(
    result: SearchResult,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Medium)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            verticalAlignment = Alignment.Top
        ) {
            // Placeholder avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.name.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(Spacing.Medium))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = result.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = result.username,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // User/Page indicator and timestamp
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = if (result.isUser) "User" else "Post",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        result.timestamp?.let { timestamp ->
                            Text(
                                text = formatTimestamp(timestamp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Post snippet for posts
                result.postSnippet?.let { snippet ->
                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                    Text(
                        text = snippet,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Followers count for users
                result.followersCount?.let { count ->
                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                    Text(
                        text = "$count followers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val minutes = diff / (1000 * 60)
    val hours = diff / (1000 * 60 * 60)
    val days = diff / (1000 * 60 * 60 * 24)

    return when {
        minutes < 1 -> "now"
        minutes < 60 -> "${minutes}m"
        hours < 24 -> "${hours}h"
        days < 7 -> "${days}d"
        else -> {
            val date = java.util.Date(timestamp)
            val format = java.text.SimpleDateFormat("MMM d", java.util.Locale.getDefault())
            format.format(date)
        }
    }
}
