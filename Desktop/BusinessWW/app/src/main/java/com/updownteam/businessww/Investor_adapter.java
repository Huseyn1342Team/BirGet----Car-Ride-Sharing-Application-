package com.updownteam.businessww;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Investor_adapter extends RecyclerView.Adapter<Investor_adapter.ViewHolder> {

    private List<FriendRequestModel> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    Investor_adapter(Context context, List<FriendRequestModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.friend_settings_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FriendRequestModel model= mData.get(position);
        final String MyUid = model.getUid();//FriendRequest
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ProfilString = dataSnapshot.child("profil").getValue().toString();
                String NameString =dataSnapshot.child("name").getValue().toString();
                holder.NameS = dataSnapshot.child("name").getValue().toString();
                String GenderString  = dataSnapshot.child("gender").getValue().toString();
                holder.Name.setText(NameString);
                Picasso.get().load(ProfilString).into(holder.ProfilImage);
                String phonetext = dataSnapshot.child("phone").getValue().toString();
                holder.Gender.setText(phonetext);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ProfilImage;
        TextView Name;
        TextView Gender;
        Button Ignore;
        Button Agree;
        String NameS;
        ViewHolder(View itemView) {
            super(itemView);
            ProfilImage = (CircleImageView)itemView.findViewById(R.id.profilimage);
            Name = (TextView)itemView.findViewById(R.id.name);
            Gender = (TextView)itemView.findViewById(R.id.gender);
            Ignore = (Button)itemView.findViewById(R.id.ignore);
            Agree = (Button)itemView.findViewById(R.id.agree);
        }
    }

    // convenience method for getting data at click position
    FriendRequestModel getItem(int id) {
        return mData.get(id);
    }

}