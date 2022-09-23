package com.julio.artbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.julio.artbook.repo.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var dao: ArtDao

    @Before
    fun setup() {
        hiltRule.inject()
        //database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ArtDatabase::class.java).allowMainThreadQueries().build()
        dao = database.artDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArtTest() = runTest {
        val art = Art("Mona Lisa", "Da Vinci", 1800, "test.com", 1)
        dao.insertArt(art)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(art)

    }


    @Test
    fun deleteArtTest() = runTest {
        val art = Art("Mona Lisa", "Da Vinci", 1800, "test.com", 1)
        dao.insertArt(art)
        dao.deleteArt(art)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(art)

    }

}