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
public interface DaoBest {

    @Query("SELECT * FROM best")
    Single<List<BestStories>> getAll();

    @Query("SELECT * FROM best WHERE id = :id")
    Single<BestStories> getById(long id);

    @Query("SELECT COUNT(*) FROM best")
    Single<Integer> getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BestStories b);

    @Update
    void update(BestStories b);

    @Delete
    void delete(List<BestStories> list);

    @Query("DELETE FROM best")
    void deleteAll();
}
