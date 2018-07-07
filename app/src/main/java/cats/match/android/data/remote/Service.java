package cats.match.android.data.remote;

import cats.match.android.data.entities.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.GET;


public interface Service {

    @GET("services/rest/?method=flickr.photos.search" +
            "&api_key=cfd19d113a84160003a9ba60a6ef88c0" +
            "&tags=cute+kitten" +
            "&text=cute+kitten" +
            "&sort=relevance" +
            "&per_page=8" +
            "&format=json" +
            "&nojsoncallback=1")
    Call<ResponseEntity> getPhotos();
}
