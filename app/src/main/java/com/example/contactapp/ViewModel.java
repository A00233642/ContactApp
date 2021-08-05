package com.example.contactapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private UsersRepository usersRepository;
    private LiveData<List<Users>> usersList;


    public ViewModel(@NonNull Application application) {
        super(application);

        usersRepository = new UsersRepository(application);
        usersList = usersRepository.getAllUsers();
    }

    public LiveData<List<Users>> getAllUsers(){
        return  usersRepository.getAllUsers();
    }

    public void insertUser(Users users){
        usersRepository.insertUser(users);
    }

    public void updateUsers(Users users){
        usersRepository.updateUsers(users);
    }

    public void deleteUser(Users users){
        usersRepository.deleteUsers(users);

    }

}

