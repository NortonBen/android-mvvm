package com.norton.mvvmbase.repository.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.norton.mvvmbase.repository.local.entity.UserEntity;

import java.util.List;

/**
 * Created by norton on 27/03/2018.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<UserEntity> getAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<UserEntity> loadAllByIds(int[] userIds);
    
    @Insert
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}