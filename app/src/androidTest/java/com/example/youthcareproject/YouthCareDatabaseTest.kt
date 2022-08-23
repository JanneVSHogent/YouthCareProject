package com.example.youthcareproject

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.youthcareproject.database.Post
import com.example.youthcareproject.database.PostDao
import com.example.youthcareproject.database.YouthCareDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class YouthCareDatabaseTest {

    private lateinit var postDao: PostDao
    private lateinit var db: YouthCareDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, YouthCareDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        postDao = db.postDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPost(){
        val post = Post()
        postDao.insert(post)

        val posts = postDao.getAllPosts().value;
        assertNotNull(posts)
        if (posts != null) {
            assertEquals(posts[0], post)
        }
    }
}