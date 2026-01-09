
package com.socialnetwork.checking_sn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.ui.theme.AppBackgroundColor
import com.socialnetwork.checking_sn.ui.theme.Checking_snTheme
import com.socialnetwork.checking_sn.ui.theme.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var secureTokenManager: SecureTokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true  // Dark icons on white background
            isAppearanceLightNavigationBars = true  // Dark buttons on white background
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        // Set status bar to app background color, keep navigation bar transparent for overlay
        window.statusBarColor = android.graphics.Color.argb(
            (AppBackgroundColor.alpha * 255).toInt(),
            (AppBackgroundColor.red * 255).toInt(),
            (AppBackgroundColor.green * 255).toInt(),
            (AppBackgroundColor.blue * 255).toInt()
        )
        window.navigationBarColor = android.graphics.Color.argb(
            (AppBackgroundColor.alpha * 255).toInt(),
            (AppBackgroundColor.red * 255).toInt(),
            (AppBackgroundColor.green * 255).toInt(),
            (AppBackgroundColor.blue * 255).toInt()
        )
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            Checking_snTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    Navigation(secureTokenManager = secureTokenManager)
                }
            }
        }
    }
}
