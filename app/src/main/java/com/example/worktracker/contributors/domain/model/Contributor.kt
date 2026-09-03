package com.example.worktracker.contributors.domain.model

data class Contributor(
    var id: Int,
    var name: String,
    var imageUrl: String?,
    var createdAt: String,
    var updatedAt: String
)
