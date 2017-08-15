package com.example.greasowang.missiona;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class RankListActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.my_tintColor));
        getSupportActionBar().setTitle("Jacker's Rank 潔客排行榜");
        readData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.revote:
                reVote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void saveData(){
        ArrayList<CleanerInfo> cleanerInfoArray = new ArrayList<CleanerInfo>();
        CleanerInfo cleanerA = new CleanerInfo();
        CleanerInfo cleanerB = new CleanerInfo();
        CleanerInfo cleanerC = new CleanerInfo();
        CleanerInfo cleanerD = new CleanerInfo();
        CleanerInfo cleanerE = new CleanerInfo();
        CleanerInfo cleanerF = new CleanerInfo();
        CleanerInfo cleanerG = new CleanerInfo();
        CleanerInfo cleanerH = new CleanerInfo();

        cleanerB.init("習大大","https://image.ibb.co/eXKdKv/2.jpg",33,false);
        cleanerC.init("川老普","https://image.ibb.co/ekPHCF/p7403741a556455019.jpg",30,false);
        cleanerD.init("普大丁","https://image.ibb.co/dpEvzv/image.jpg",45,false);
        cleanerE.init("金小恩","https://image.ibb.co/eUvPsF/b_unLa4s.jpg",2,false);
        cleanerA.init("蔡小英","https://image.ibb.co/iLhhev/610dc034jw1fasakfvqe1j20u00mhgn2.jpg",20,false);
        cleanerF.init("小燕子","https://image.ibb.co/hTJ6Pv/12208610_978392848900651_215237829551329623_n.jpg",66,false);
        cleanerG.init("紫小葳","https://image.ibb.co/d52GPv/640_cc171f62a0db0f80e88879c829e9c531.jpg",30,false);
        cleanerH.init("爾匹康","http://img.appledaily.com.tw/images/ReNews/20150922/640_d4790ed9f5c1a53ea45afa85bb906281.jpg",8,false);


        cleanerInfoArray.add(cleanerA);
        cleanerInfoArray.add(cleanerB);
        cleanerInfoArray.add(cleanerC);
        cleanerInfoArray.add(cleanerD);
        cleanerInfoArray.add(cleanerE);
        cleanerInfoArray.add(cleanerF);
        cleanerInfoArray.add(cleanerG);
        cleanerInfoArray.add(cleanerH);

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String outputArray = gson.toJson(cleanerInfoArray);
        sharedPreferences.edit()
                .clear()
                .putString("cleanerInfo",outputArray.toString())
                .commit();
        readData();
    }
    void readData() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String input = sharedPreferences.getString("cleanerInfo", "沒這東西喔");
        if (input.equals("沒這東西喔")){
            saveData();
        }
        sortData();
        Gson gson = new Gson();
//        reVote();
        ArrayList<CleanerInfo> cleanerInfoList = gson.fromJson(input, new TypeToken<ArrayList<CleanerInfo>>(){}.getType());

        mAdapter = new MyAdapter(cleanerInfoList);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    void sortData(){
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String input = sharedPreferences.getString("cleanerInfo", "沒這東西喔");
        ArrayList<CleanerInfo> cleanerInfoList = gson.fromJson(input, new TypeToken<ArrayList<CleanerInfo>>(){}.getType());
        Collections.sort(cleanerInfoList, new Comparator<CleanerInfo>(){
            @Override
            public int compare(CleanerInfo o1, CleanerInfo o2) {
                return o2.votes - o1.votes;
            }
        });
        String outputArray = gson.toJson(cleanerInfoList);
        sharedPreferences.edit()
                .clear()
                .putString("cleanerInfo",outputArray.toString())
                .commit();
    }
    void vote(List<CleanerInfo> data, int position){
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        int newVotes = data.get(position).votes + 1;
        data.get(position).votes = newVotes;
        String outputArray = gson.toJson(data);
        sharedPreferences.edit()
                .clear()
                .putString("cleanerInfo",outputArray.toString())
                .commit();
    }
    void reVote(){
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String input = sharedPreferences.getString("cleanerInfo", "沒這東西喔");
        ArrayList<CleanerInfo> cleanerInfoList = gson.fromJson(input, new TypeToken<ArrayList<CleanerInfo>>(){}.getType());
        for(int i=0;i<cleanerInfoList.size();i++){
            cleanerInfoList.get(i).voted = false;
        }
        String outputArray = gson.toJson(cleanerInfoList);
        sharedPreferences.edit()
                .clear()
                .putString("cleanerInfo",outputArray.toString())
                .commit();
        recreate();
    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<CleanerInfo> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ImageView mImageView;
            public Button mVoteButton;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
                mImageView = (ImageView) v.findViewById(R.id.info_img);
                mVoteButton = (Button) v.findViewById(R.id.button);
            }
        }

        public MyAdapter(List<CleanerInfo> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemlayout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if(position == 0){
                holder.mTextView.setTextColor(Color.RED);
            } else if(position == 1){
                holder.mTextView.setTextColor(Color.BLUE);
            } else if (position == 2){
                holder.mTextView.setTextColor(Color.GREEN);
            } else {
                holder.mTextView.setTextColor(Color.BLACK);
            }
            holder.mTextView.setText((position+1)+"_"+mData.get(position).name+" , "+mData.get(position).votes+" 票 ");
            String InternetUrl = mData.get(position).photo;

            String ps = Integer.toString(position);
            Log.v("position","position:"+InternetUrl);
            Picasso.with(RankListActivity.this)
                    .load(InternetUrl)
                    .fit()
                    .centerCrop()
                    .into(holder.mImageView);
//            Ion.with(RankListActivity.this).load(InternetUrl).intoImageView(holder.mImageView);

            final Boolean isVote = mData.get(position).voted;

            if (isVote == false){
                holder.mVoteButton.setVisibility(View.VISIBLE);
            }else{
                holder.mVoteButton.setVisibility(View.INVISIBLE);
            }
            holder.mVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isVote == false){
                        v.setVisibility(View.INVISIBLE);
                        mData.get(position).voted = true;
                        vote(mData,position);
                        holder.mTextView.setText((position+1)+"_"+mData.get(position).name+" , "+mData.get(position).votes+" 票 ");
                        recreate();
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(RankListActivity.this, CleanerDetail.class);
                    intent.putExtra("photo",mData.get(position).photo);
                    intent.putExtra("name",mData.get(position).name);
                    intent.putExtra("votes",Integer.toString(mData.get(position).votes));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}


