package com.example.worktracker.contributors.data.remote.mappers

import com.example.worktracker.contributors.data.remote.dto.AddContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.AddContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.ContributorDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsBodyDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsResponseDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorResponseDto
import com.example.worktracker.contributors.domain.model.AddContributorBody
import com.example.worktracker.contributors.domain.model.AddContributorResponse
import com.example.worktracker.contributors.domain.model.Contributor
import com.example.worktracker.contributors.domain.model.DeleteContributorBody
import com.example.worktracker.contributors.domain.model.DeleteContributorResponse
import com.example.worktracker.contributors.domain.model.GetContributorsBody
import com.example.worktracker.contributors.domain.model.GetContributorsResponse
import com.example.worktracker.contributors.domain.model.UpdateContributorBody
import com.example.worktracker.contributors.domain.model.UpdateContributorResponse

fun ContributorDto.toDomain(): Contributor
{
    return Contributor(
        id= id,
        name= name,
        imageUrl= imageUrl,
        createdAt= createdAt,
        updatedAt= updatedAt
    )
}

fun GetContributorsBodyDto.toDomain(): GetContributorsBody
{
    return GetContributorsBody(
        action= action,
        page= page,
        pageSize= pageSize
    )
}

fun GetContributorsBody.toDto(): GetContributorsBodyDto
{
    return GetContributorsBodyDto(
        action= action,
        page= page,
        pageSize= pageSize
    )
}

fun GetContributorsResponseDto.toDomain(): GetContributorsResponse
{
    return GetContributorsResponse(
        success= success,
        page= page,
        pageSize= pageSize,
        totalCount= totalCount,
        contributors= contributors.map { it.toDomain() }
    )
}

fun AddContributorBodyDto.toDomain(): AddContributorBody
{
    return AddContributorBody(
        action= action,
        name= name,
        imageUrl= imageUrl
    )
}

fun AddContributorBody.toDto(): AddContributorBodyDto
{
    return AddContributorBodyDto(
        action= action,
        name= name,
        imageUrl= imageUrl
    )
}

fun AddContributorResponseDto.toDomain(): AddContributorResponse
{
    return AddContributorResponse(
        success=  success,
        id= id,
        message= message
    )
}

fun UpdateContributorBodyDto.toDomain(): UpdateContributorBody
{
    return UpdateContributorBody(
        action= action,
        id= id,
        newName= newName,
        imageUrl= imageUrl
    )
}

fun UpdateContributorBody.toDto(): UpdateContributorBodyDto
{
    return UpdateContributorBodyDto(
        action= action,
        id= id,
        newName= newName,
        imageUrl= imageUrl
    )
}

fun UpdateContributorResponseDto.toDomain(): UpdateContributorResponse
{
    return UpdateContributorResponse(
        success=  success,
        id= id,
        message= message
    )
}

fun DeleteContributorBodyDto.toDomain(): DeleteContributorBody
{
    return DeleteContributorBody(
        action= action,
        id= id
    )
}

fun DeleteContributorBody.toDto(): DeleteContributorBodyDto
{
    return DeleteContributorBodyDto(
        action= action,
        id= id
    )
}

fun DeleteContributorResponseDto.toDomain(): DeleteContributorResponse
{
    return DeleteContributorResponse(
        success=  success,
        id= id,
        message= message
    )
}