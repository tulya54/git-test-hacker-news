package kz.tech.testhackernewsclient.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DaoNew {

    @Query("SELECT * FROM new")
    Single<List<NewStories>> getAll();

    @Query("SELECT * FROM new WHERE id = :id")
    Single<NewStories> getById(long id);

    @Query("SELECT COUNT(*) FROM new")
    Single<Integer> getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewStories n);

    @Update
    void update(NewStories n);

    @Delete
    void delete(List<NewStories> list);

    @Query("DELETE FROM new")
    void deleteAll();

}
