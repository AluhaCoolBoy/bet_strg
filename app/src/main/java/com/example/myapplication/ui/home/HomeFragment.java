package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BetAdapter;
import com.example.myapplication.Betitem;
import com.example.myapplication.Info;
import com.example.myapplication.R;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final ArrayList<Betitem> betitems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new BetAdapter(betitems, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        betitems.add(new Betitem(R.drawable.prostavki_bg, "1X on home outsiders","0","0"));
        betitems.add(new Betitem(R.drawable.prostavki_bg, "All-in on odds","1","0"));
        betitems.add(new Betitem(R.drawable.prostavki_bg, "The 1-3-2-6 system","2","0"));
        betitems.add(new Betitem(R.drawable.prostavki_bg, "The Fibonacci betting","3","0"));
        betitems.add(new Betitem(R.drawable.prostavki_bg, "The Kelly formula","4","0"));
        betitems.add(new Betitem(R.drawable.prostavki_bg, "Dutching in sports betting","5","0"));

        return root;
    }

}