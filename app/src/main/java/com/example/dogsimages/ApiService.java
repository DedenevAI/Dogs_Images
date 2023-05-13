package com.example.dogsimages;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("breeds/image/random")
    Single<DogImage> loadDogImage();
}
