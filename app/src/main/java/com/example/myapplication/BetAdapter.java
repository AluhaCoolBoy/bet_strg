package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class BetAdapter extends RecyclerView.Adapter<BetAdapter.ViewHolder> {
    private final ArrayList<Betitem> betitems;
    private final Context context;
    private FavDB favDB;

    public BetAdapter(ArrayList<Betitem> betItems, Context context) {
        this.betitems = betItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull BetAdapter.ViewHolder holder, int position) {
        final Betitem betitem = betitems.get(position);
        readCursorData(betitem, holder);
        holder.imageView.setImageResource(betitem.getImageResourse());
        holder.titleTextView.setText(betitem.getTitle());

    }



    @Override
    public int getItemCount() {
        return betitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        Button favBtn;
        Button butten;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             imageView = itemView.findViewById(R.id.imageView);
             titleTextView = itemView.findViewById(R.id.titleTextView);
             favBtn = itemView.findViewById(R.id.favBtn);
             butten = itemView.findViewById(R.id.button);

             //add to fav btn
             favBtn.setOnClickListener(view -> {
                 int position = getAdapterPosition();
                 Betitem betitem = betitems.get(position);
                 if (betitem.getFavStatus().equals("0")){
                     betitem.setFavStatus("1");
                     favDB.insertIntoTheDatabase(betitem.getTitle(),betitem.getImageResourse(), betitem.getKey_id(),betitem.getFavStatus());
                     favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                 } else{
                     betitem.setFavStatus("0");
                     favDB.remove_fav(betitem.getKey_id());
                     favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                 }
             });
             itemView.findViewById(R.id.button).setOnClickListener(v -> {
                 int position = getAdapterPosition();
                 Betitem betitem = betitems.get(position);
                 Intent intent = new Intent(context, DashboardFragment.class);
                 context.startActivity(intent);
             });
         }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Betitem betitem , ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(betitem.getKey_id());
        try (SQLiteDatabase db = favDB.getReadableDatabase()) {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndexOrThrow(FavDB.FAVORITE_STATUS));
                betitem.setFavStatus(item_fav_status);
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                }

            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
        }

    }

}
