package ke.co.appslab.jetpacknavcontroller.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ke.co.appslab.jetpacknavcontroller.models.AlbumItem
import ke.co.appslab.jetpacknavcontroller.models.Song

@Dao
interface SongsDao {

    @Query("SELECT * FROM song WHERE songId = :songId")
    suspend fun getSong(songId: String): Song

    @Insert
    suspend fun insertSong(song: Song)

    @Transaction
    suspend fun deleteShortSongs(): List<Song> {
        val songs = getSongsWithElapsedTimeLessThan(1000)
        deleteSongsWithId(songs.map { it.id })

        return songs
    }

    suspend fun deleteSongsWithId(map: List<Long>) {

    }

    suspend fun getSongsWithElapsedTimeLessThan(i: Int): List<Song> {
        return emptyList()

    }

    //search in room 2.0
    @Query(
        """
        SELECT *
        FROM song
        WHERE songName LIKE '%' || :query || '%'
        OR albumName LIKE '%' || :query || '%'
        OR artistName LIKE '%' || :query || '%'
    """
    )
    fun searchSong(query: String): List<Song>



    //New in Room
    @Query(
        """
        SELECT *
        FROM song
        WHERE Song MATCH :query
    """
    )
    fun searchSongNew(query: String): List<Song>

    //you can now use this view as if it was another table
    //shows a list of albums with aggregated information
    @Query(
        """
        SELECT * FROM AlbumItem
        ORDER BY numOfSongs DESC
    """
    )
    fun getAlbumItemsByNumOfSongs(): List<AlbumItem>

}