package cats.match.android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import cats.match.android.data.entities.Photos;
import cats.match.android.data.entities.ResponseEntity;
import cats.match.android.data.remote.FlickrApi;
import cats.match.android.data.remote.Service;
import retrofit2.Call;
import retrofit2.Callback;

public class LoadingViewModel extends ViewModel {

    private MutableLiveData<Photos> photos = new MutableLiveData<Photos>();

    public void getPhotos() {

        FlickrApi.getService().getPhotos().enqueue(new Callback<ResponseEntity>() {

            @Override
            public void onResponse(Call<ResponseEntity> call, retrofit2.Response<ResponseEntity> response) {
                Log.d("Success", "Success");
                downloadAll(response.body().getPhotos());
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {

                Log.d("Error", "error");

            }
        });

    }

    public void downloadAll(Photos photos){

    }
}