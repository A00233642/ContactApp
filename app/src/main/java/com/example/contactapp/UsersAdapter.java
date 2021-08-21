package com.example.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVH> {

    private List<Users> usersList;
    private Context context;
    private ClickListener clickListener;

    public UsersAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(List<Users> usersList) {
        this.usersList = usersList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UsersAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UsersAdapterVH(
                LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UsersAdapterVH holder, int position) {


        Users users = usersList.get(position);
        String username = users.getUsername();
        holder.tvName.setText(username);

        holder.imageOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopUp(view, users);

            }
        });
    }

    public void showPopUp(View view, Users users) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();
                switch (id){
                    case R.id.itUpdate:


                        clickListener.updateClicked(users);


                        break;

                    case R.id.itDelete:

                        clickListener.deleteClicked(users);

                        break;
                }

                return false;
            }
        });

        popupMenu.show();

    }


    public  interface ClickListener{
        void updateClicked(Users users);
        void deleteClicked(Users users);

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UsersAdapterVH extends RecyclerView.ViewHolder {
        ImageView imageOptions;
        TextView tvName;
        public UsersAdapterVH(@NonNull  View itemView) {
            super(itemView);
            imageOptions = itemView.findViewById(R.id.imageOptions);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
