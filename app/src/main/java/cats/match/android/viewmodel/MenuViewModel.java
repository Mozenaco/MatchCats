package cats.match.android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import java.util.List;
import cats.match.android.data.entities.Photo;
import cats.match.android.data.entities.ResponseEntity;
import cats.match.android.data.remote.FlickrApi;
import retrofit2.Call;
import retrofit2.Callback;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<List<Photo>> photos = new MutableLiveData<List<Photo>>();

    public void getPhotos() {

        FlickrApi.getService().getPhotos().enqueue(new Callback<ResponseEntity>() {

            @Override
            public void onResponse(Call<ResponseEntity> call, retrofit2.Response<ResponseEntity> response) {
                Log.d("Success", "Success");
                photos.postValue(response.body().getPhotos().getPhotoList());
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {

                Log.d("Error", "error");

            }
        });
    }

    public MutableLiveData<List<Photo>> getObservablePhotos() {
        return photos;
    }
}