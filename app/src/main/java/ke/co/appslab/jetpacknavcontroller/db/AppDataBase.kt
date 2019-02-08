package ke.co.appslab.jetpacknavcontroller.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ke.co.appslab.jetpacknavcontroller.models.Work

@Database(entities = [Work::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    companion object {

        private const val DATABASE_NAME = "jetpack.db"

        fun create(context: Context): AppDataBase =
            Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}