package com.norton.mvvmbase.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.norton.mvvmbase.model.Setting;

/**
 * Created by norton on 08/04/2018.
 */

@Entity(tableName = "settings")
public class SettingEntity implements Setting {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "option")
    private String option;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
