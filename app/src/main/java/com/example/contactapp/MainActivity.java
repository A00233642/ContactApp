package com.example.contactapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewModel userViewModel;
    UsersAdapter usersAdapter;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        recyclerView = findViewById(R.id.recyclerview);
        floatingActionButton = findViewById(R.id.btnNewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));
        recyclerView.setHasFixedSize(true);

        usersAdapter = new UsersAdapter();

        userViewModel.getAllUsers().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
               if (users.size()>0);
               usersAdapter.setData(users);
               recyclerView.setAdapter(usersAdapter);

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });


    }

    public void addUser(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUserBtn);
        EditText edUsers = view.findViewById(R.id.edUser);
        TextView tvDetails = view .findViewById(R.id.tvDetails);

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
}