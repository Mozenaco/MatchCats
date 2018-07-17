package cats.match.android.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Class ResponseEntity responsible for store data of the serialized object from the service
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class ResponseEntity {

    @SerializedName("photos")
    Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

}