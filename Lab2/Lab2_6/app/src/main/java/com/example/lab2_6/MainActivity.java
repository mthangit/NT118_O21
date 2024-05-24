package com.example.lab2_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ArrayList<Hero> mHeros;
    private RecyclerView mRecyclerHero;
    private HeroAdapter mHeroAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerHero = findViewById(R.id.recycleHero);
        mHeros = new ArrayList<>();
        creatHeroList();
        mHeroAdapter = new HeroAdapter(this, mHeros);
        mRecyclerHero.setAdapter(mHeroAdapter);
        mRecyclerHero.setLayoutManager(new LinearLayoutManager(this));
    }
    private void creatHeroList() {
        mHeros.add(new Hero("Flash", R.drawable.flashjpg));
        mHeros.add(new Hero("Super Hero Team", R.drawable.sh));
        mHeros.add(new Hero("Wonder Woman", R.drawable.ww));
        mHeros.add(new Hero("Batman", R.drawable.matcha));
        mHeros.add(new Hero("Super", R.drawable.supermanjpg));
        mHeros.add(new Hero("Wonder Woman", R.drawable.ww));
    }
}