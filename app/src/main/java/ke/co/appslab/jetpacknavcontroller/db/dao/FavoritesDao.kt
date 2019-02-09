package ke.co.appslab.jetpacknavcontroller.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ke.co.appslab.jetpacknavcontroller.models.Work

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM Work")
    fun getFavorites(): DataSource.Factory<Int, Work>

    @Query("SELECT * FROM Work WHERE id = :id ")
    fun getFavorite(id: String): LiveData<Work>

    @Query("SELECT count(*) FROM Work")
    fun getFavoriteCount(): LiveData<Int>

    @Insert(onConflict = REPLACE)
    fun addFavorite(work: Work)

    @Delete
    fun removeFavorite(work: Work)
}