package cats.match.android.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class Photos responsible for store data of the serialized object from the service
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class Photos {

    @SerializedName("photo")
    List<Photo> photo;

    public List<Photo> getPhotoList() {
        return photo;

    }

    public void setResponse(List<Photo> response) {
        this.photo = response;
    }
}
