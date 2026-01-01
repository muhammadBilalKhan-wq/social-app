package com.socialnetwork.checking_sn.feature_profile.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.ui.theme.Spacing

// Data class for placeholder post data
data class UserPost(
    val id: String,
    val content: String,
    val timestamp: String,
    val likesCount: Int,
    val commentsCount: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit
) {
    // Placeholder user data
    val userName = "Alex Johnson"
    val userHandle = "@alexjohnson"
    val userBio = "Mobile developer passionate about creating amazing user experiences. Love coding, coffee, and hiking in the mountains."
    val followersCount = 1247
    val followingCount = 356
    val postsCount = 89

    // Placeholder posts
    val userPosts = remember {
        listOf(
            UserPost("1", "Just finished an amazing hike in the Rockies! The views were absolutely breathtaking. Nature never ceases to amaze me. 🏔️ #hiking #nature", "2 hours ago", 24, 8),
            UserPost("2", "Excited to share my latest project! Been working on a new mobile app that helps people track their daily habits. The design process was incredibly rewarding.", "1 day ago", 67, 23),
            UserPost("3", "Coffee and code - the perfect combination for a productive morning! ☕💻 Working on some exciting new features for my app.", "2 days ago", 45, 12),
            UserPost("4", "Attended an amazing tech conference today. So inspiring to see all the innovations happening in our industry. The future of tech looks bright!", "3 days ago", 89, 34),
            UserPost("5", "Weekend project: Built a simple weather app using Jetpack Compose. Loving how declarative UI frameworks make development so much more intuitive.", "5 days ago", 156, 67),
            UserPost("6", "Beautiful sunset from my balcony. Sometimes you need to pause and appreciate the simple moments in life. 🌅", "1 week ago", 78, 19)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
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
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Profile header
            item {
                ProfileHeader(
                    userName = userName,
                    userHandle = userHandle,
                    userBio = userBio,
                    followersCount = followersCount,
                    followingCount = followingCount,
                    postsCount = postsCount
                )
            }

            // Posts section header
            item {
                Text(
                    text = "Posts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = Spacing.Medium,
                        vertical = Spacing.Small
                    )
                )
            }

            // User posts
            items(userPosts) { post ->
                PostItem(post = post)
            }
        }
    }
}

@Composable
fun ProfileHeader(
    userName: String,
    userHandle: String,
    userBio: String,
    followersCount: Int,
    followingCount: Int,
    postsCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(Spacing.Medium)
    ) {
        // Profile picture and basic info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile picture placeholder
            androidx.compose.foundation.Canvas(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            ) {
                // Simple placeholder for profile picture
            }

            Spacer(modifier = Modifier.width(Spacing.Medium))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userHandle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.Medium))

        // Bio
        Text(
            text = userBio,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))

        // Stats row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(count = postsCount, label = "Posts")
            StatItem(count = followersCount, label = "Followers")
            StatItem(count = followingCount, label = "Following")
        }

        Spacer(modifier = Modifier.height(Spacing.Medium))

        // Follow button
        Button(
            onClick = { /* TODO: Follow/Unfollow logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C3AED)
            )
        ) {
            Text(
                text = "Follow",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatItem(
    count: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun PostItem(
    post: UserPost
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium)
        ) {
            // Post content
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacing.Small))

            // Timestamp
            Text(
                text = post.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(Spacing.Small))

            // Engagement row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* TODO: Like logic */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Like",
                            tint = Color.Red,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = post.likesCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(Spacing.Medium))

                    IconButton(onClick = { /* TODO: Comment logic */ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Comment",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = post.commentsCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
