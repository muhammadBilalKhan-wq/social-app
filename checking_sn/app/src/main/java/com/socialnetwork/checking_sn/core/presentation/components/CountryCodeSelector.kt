package com.socialnetwork.checking_sn.core.presentation.components

import android.telephony.TelephonyManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socialnetwork.checking_sn.core.data.Country
import com.socialnetwork.checking_sn.core.data.allCountries
import com.socialnetwork.checking_sn.ui.theme.InputBackground
import com.socialnetwork.checking_sn.ui.theme.InputFieldShape
import com.socialnetwork.checking_sn.ui.theme.SoftPeriwinkleBlue
import com.socialnetwork.checking_sn.ui.theme.Spacing
import java.util.*

@Composable
fun CountryCodeSelector(
    selectedCountryCode: String,
    selectedCountryIsoCode: String,
    onCountrySelected: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    var showCountryPicker by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Auto-detect country on first composition if not already set
    val defaultCountryCode = remember {
        if (selectedCountryCode != "+92") {
            selectedCountryCode // Already set
        } else {
            try {
                // Try TelephonyManager first (most reliable for network-based detection)
                val telephonyManager = context.getSystemService(android.content.Context.TELEPHONY_SERVICE) as? TelephonyManager
                val networkIso = telephonyManager?.networkCountryIso?.uppercase()

                // If network ISO is available and valid, use it
                if (!networkIso.isNullOrEmpty() && networkIso != "ZZ") {
                    allCountries.find { it.isoCode == networkIso }?.callingCode ?: run {
                        // Fallback to locale if network ISO not in our list
                        val localeIso = Locale.getDefault().country.uppercase()
                        allCountries.find { it.isoCode == localeIso }?.callingCode ?: "+92"
                    }
                } else {
                    // Fallback to device locale
                    val localeIso = Locale.getDefault().country.uppercase()
                    allCountries.find { it.isoCode == localeIso }?.callingCode ?: "+92"
                }
            } catch (e: Exception) {
                "+92" // Default to Pakistan if detection fails
            }
        }
    }

    // Update country code when switching to phone if not already set
    LaunchedEffect(defaultCountryCode) {
        if (selectedCountryCode == "+92" && defaultCountryCode != "+92") {
            val defaultCountry = allCountries.find { it.callingCode == defaultCountryCode }
            if (defaultCountry != null) {
                onCountrySelected(defaultCountry.callingCode, defaultCountry.isoCode)
            }
        }
    }

    val selectedCountry = remember(selectedCountryIsoCode) {
        allCountries.find { it.isoCode == selectedCountryIsoCode }
    }

    if (compact) {
        // Compact layout for text field leading icon
        Row(
            modifier = modifier
                .clickable { showCountryPicker = true }
                .padding(horizontal = Spacing.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country flag (smaller)
            selectedCountry?.let { country ->
                Text(
                    text = country.flag,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = Spacing.ExtraSmall)
                )
            }

            // Country code display (smaller)
            Text(
                text = selectedCountryCode,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            // Separator
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .padding(start = Spacing.Small)
            )
        }
    } else {
        // Full layout for standalone use
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(Spacing.InputFieldHeight)
                .clickable { showCountryPicker = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country code display
            Text(
                text = selectedCountryCode,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(end = Spacing.Small)
            )

            // Country flag
            selectedCountry?.let { country ->
                Text(
                    text = country.flag,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = Spacing.Small)
                )
            }

            // Separator
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
        }
    }

    if (showCountryPicker) {
        CountryPickerModal(
            onDismiss = {
                showCountryPicker = false
                searchQuery = ""
            },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onCountrySelected = { country ->
                onCountrySelected(country.callingCode, country.isoCode)
                showCountryPicker = false
                searchQuery = ""
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryPickerModal(
    onDismiss: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCountrySelected: (Country) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.ScreenPaddingHorizontal, vertical = Spacing.ScreenPaddingVertical)
        ) {
            // Header
            Text(
                text = "Select Country",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = Spacing.Medium)
            )

            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        text = "Search countries...",
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(bottom = Spacing.Medium),
                shape = InputFieldShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = InputBackground,
                    unfocusedContainerColor = InputBackground
                ),
                singleLine = true
            )

            // Filtered Countries
            val filteredCountries = remember(searchQuery) {
                if (searchQuery.isEmpty()) {
                    allCountries
                } else {
                    allCountries.filter { country ->
                        country.name.contains(searchQuery, ignoreCase = true) ||
                        country.callingCode.contains(searchQuery) ||
                        country.isoCode.contains(searchQuery, ignoreCase = true)
                    }
                }
            }

            // Country List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = Spacing.ScreenPaddingVertical)
            ) {
                items(filteredCountries) { country ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCountrySelected(country) }
                            .padding(vertical = Spacing.Medium, horizontal = Spacing.Small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Country Flag
                        Text(
                            text = country.flag,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(end = Spacing.Medium)
                        )

                        // Country Name and Code
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = country.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        }

                        // Calling Code
                        Text(
                            text = country.callingCode,
                            style = MaterialTheme.typography.titleMedium,
                            color = SoftPeriwinkleBlue
                        )
                    }
                }
            }
        }
    }
}
