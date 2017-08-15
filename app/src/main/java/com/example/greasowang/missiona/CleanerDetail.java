package com.example.greasowang.missiona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CleanerDetail extends AppCompatActivity {
    public ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_detail);
        getSupportActionBar().hide();
        String photo = getIntent().getStringExtra("photo");
        String name = getIntent().getStringExtra("name");
        String votes = getIntent().getStringExtra("votes");

        mImageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(CleanerDetail.this)
                .load(photo)
                .fit()
                .centerCrop()
                .into(mImageView);
        TextView nameTextView = (TextView)findViewById(R.id.nameTextView);
        TextView voteTextView = (TextView)findViewById(R.id.voteTextView);
        nameTextView.setText(name);
        voteTextView.setText(votes+" 票");
    }
}
