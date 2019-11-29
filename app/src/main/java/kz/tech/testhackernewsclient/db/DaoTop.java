package kz.tech.testhackernewsclient.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DaoTop {

    @Query("SELECT * FROM top")
    Single<List<TopStories>> getAll();

    @Query("SELECT * FROM top WHERE id = :id")
    Single<TopStories> getById(long id);

    @Query("SELECT COUNT(*) FROM top")
    Single<Integer> getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TopStories t);

    @Update
    void update(TopStories t);

    @Delete
    void delete(List<TopStories> list);

    @Query("DELETE FROM top")
    void deleteAll();
}
