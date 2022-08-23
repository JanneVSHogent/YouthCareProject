package com.example.youthcareproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Post::class, Account::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class YouthCareDatabase: RoomDatabase() {

    abstract val postDao: PostDao
    abstract val accountDao: AccountDao

    companion object{
        @Volatile
        private var INSTANCE: YouthCareDatabase? = null;

        fun getInstance(context: Context) : YouthCareDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        YouthCareDatabase::class.java,
                        "youth_care_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(CallBack())
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }

                return instance;
            }
        }
    }

    internal class CallBack : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.beginTransaction()
            db.execSQL(
                "INSERT INTO Account('email','role','firstName', 'lastName') VALUES(?,?,?, ?)",
                arrayOf<Any>("janne.vanschepdael@hotmail.com", "0", "Janne", "Van Schepdael")
            )
            db.endTransaction()
        }
    }

}