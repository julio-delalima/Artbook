package com.julio.artbook.repo

import androidx.lifecycle.LiveData
import com.julio.artbook.model.ImageResponse
import com.julio.artbook.roomdb.Art
import com.julio.artbook.util.Resource

interface IArtRepository {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>

}