package com.norton.mvvmbase.repository.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.model.Category;
import com.norton.mvvmbase.repository.local.dao.CartDao;
import com.norton.mvvmbase.repository.local.dao.CategoryDao;
import com.norton.mvvmbase.repository.local.dao.ProductDao;
import com.norton.mvvmbase.repository.local.dao.SettingDao;
import com.norton.mvvmbase.repository.local.dao.UserDao;
import com.norton.mvvmbase.repository.local.entity.CartEntity;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.repository.local.entity.SettingEntity;
import com.norton.mvvmbase.repository.local.entity.UserEntity;

import java.util.List;

/**
 * Created by norton on 27/03/2018.
 */

@Database(entities = {
        UserEntity.class,
        SettingEntity.class,
        CategoryEntity.class,
        ProductEntity.class,
        CartEntity.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "foodfast-db";


    public abstract UserDao userDao();

    public abstract SettingDao settingDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract CartDao cartDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }


    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                addDelay();
                                // Generate the data for pre-population
                                AppDatabase database = AppDatabase.getInstance(appContext, executors);
                                List<UserEntity> users = DataSeeder.usersSeeder();

                                insertData(database, users );
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }






    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    private static void insertData(final AppDatabase database, final List<UserEntity> users) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                //database.userDao().insertAll(users);
            }
        });
    }


    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}