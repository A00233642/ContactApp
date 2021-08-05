package com.example.contactapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {

    @Insert
    void insertUser(Users users);

    @Update
    Void updateUsers(Users users);

    @Delete
    Void deleteUsers(Users users);

    @Query("SELECT * from users ")
    LiveData<List<Users>> getAllUsers();
}
