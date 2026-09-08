package com.example.worktracker.home.presentation.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ShareWorkItemIdViewModel: ViewModel() {
    var workItemId by mutableStateOf<Int?>(null)
}