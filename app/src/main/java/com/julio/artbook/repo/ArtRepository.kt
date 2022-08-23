package com.julio.artbook.repo

import androidx.lifecycle.LiveData
import com.julio.artbook.api.RetrofitAPI
import com.julio.artbook.model.ImageResponse
import com.julio.artbook.roomdb.Art
import com.julio.artbook.roomdb.ArtDao
import com.julio.artbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor (
    private val artDao : ArtDao,
    private val retrofitApi : RetrofitAPI
) : IArtRepository {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
                } else {
                    Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }


}