package com.example.dogsimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MainViewModel viewModel;
    private ImageView imageViewDog;
    private Button buttonLoadImage;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getIsInternetConnection().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean internetConnection) {
                if(!internetConnection){
                    Toast.makeText(MainActivity.this,
                                    R.string.error_internet_connecton,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if(loading){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getDogImageMutableLiveData().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(imageViewDog);
            }
        });
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadDogImage();
            }
        });
    }

    private void initView() {
        imageViewDog = findViewById(R.id.imageViewDog);
        buttonLoadImage = findViewById(R.id.buttonLoadImage);
        progressBar = findViewById(R.id.progressBar);

    }

}