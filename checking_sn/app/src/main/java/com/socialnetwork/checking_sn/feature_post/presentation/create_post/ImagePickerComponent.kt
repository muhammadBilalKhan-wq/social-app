package com.socialnetwork.checking_sn.feature_post.presentation.create_post

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.socialnetwork.checking_sn.R
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Composable
fun ImagePickerComponent(
    selectedImageUri: String?,
    onImageSelected: (String) -> Unit,
    onRemoveImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // Launcher for selecting image from gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                val base64Image = convertImageToBase64(context, uri)
                if (base64Image != null) {
                    onImageSelected(base64Image)
                }
            }
        }
    )

    if (selectedImageUri == null) {
        // Image picker button
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color.White)
                .clickable {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Image",
                tint = Color(0xFF7C3AED)
            )
            Text(
                text = "Add Image (Optional)",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 12.dp),
                color = Color(0xFF7C3AED)
            )
        }
    } else {
        // Selected image preview with remove button
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            AsyncImage(
                model = "data:image/jpeg;base64,$selectedImageUri",
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            
            // Remove button
            IconButton(
                onClick = onRemoveImage,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(20.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with close icon
                    contentDescription = "Remove Image",
                    tint = Color.White
                )
            }
        }
    }
}

private fun convertImageToBase64(context: Context, uri: Uri): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.use { it.readBytes() } ?: return null
        
        // For now, let's return a placeholder base64 string
        // In a real implementation, you'd want to compress the image first
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
