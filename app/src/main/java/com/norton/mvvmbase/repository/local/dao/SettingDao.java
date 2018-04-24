package com.norton.mvvmbase.repository.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.norton.mvvmbase.repository.local.entity.SettingEntity;
import com.norton.mvvmbase.repository.local.entity.UserEntity;

import java.util.List;

/**
 * Created by norton on 08/04/2018.
 */

@Dao
public interface SettingDao {
    @Query("SELECT * FROM settings WHERE name=:name")
    List<SettingEntity> get(String name);

    @Query("SELECT * FROM settings WHERE name='token'")
    LiveData< List<SettingEntity>> token();

    @Insert
    void insert(SettingEntity settingEntity);

    @Update
    void update(SettingEntity settingEntity);

    @Delete
    void delete(SettingEntity settingEntity);
}
