package com.example.contactapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UsersAdapter.ClickListener {

    //Define recyclerview, userViewModel, userAdapter, floatingActionButton
    RecyclerView recyclerView;
    ViewModel userViewModel;
    UsersAdapter usersAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        //find views by id
        recyclerView = findViewById(R.id.recyclerview);
        floatingActionButton = findViewById(R.id.btnNewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        usersAdapter = new UsersAdapter(this);

        userViewModel.getAllUsers().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                if (users.size() > 0) ;
                usersAdapter.setData(users);
                recyclerView.setAdapter(usersAdapter);

            }
        });
        //onClick Method for floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });


    }

    //Method for Night and Day Mode
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_menu, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.Day_Mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.Night_Mode);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }


    //Method for addUser
    public void addUser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUserBtn);

        EditText edUsers = view.findViewById(R.id.edUser);
        TextView tvDetails = view.findViewById(R.id.tvDetails);

        // Method for onClick for insertUsers
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edUsers.getText() != null) {
                    String username = edUsers.getText().toString().trim();

                    Users users = new Users();

                    users.setUsername(username);


                    userViewModel.insertUser(users);

                    alertDialog.dismiss();

                }


            }
        });

        alertDialog.show();

    }

    //Method updateClicked
    @Override
    public void updateClicked(Users users) {
        updateUser(users);


    }

    //Method deleteClicked
    @Override
    public void deleteClicked(Users users) {

        userViewModel.deleteUser(users);

    }

    //Method for updateUser
    public void updateUser(Users users) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        //find view by id for btnAddUsers, for EditText EdUsers and tvDetails

        Button btnAddUser = view.findViewById(R.id.addUserBtn);
        EditText edUsers = view.findViewById(R.id.edUser);
        TextView tvDetails = view.findViewById(R.id.tvDetails);

        tvDetails.setText("Update Details");
        btnAddUser.setText("Update");

        // Method for onClick for updateUsers
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edUsers.getText() != null) {
                    String username = edUsers.getText().toString().trim();


                    users.setUsername(username);
                    userViewModel.updateUsers(users);
                    alertDialog.dismiss();

                }

            }
        });

        alertDialog.show();

    }

}
