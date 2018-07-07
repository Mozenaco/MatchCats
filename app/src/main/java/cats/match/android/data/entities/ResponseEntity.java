package cats.match.android.data.entities;

import com.google.gson.annotations.SerializedName;

/*
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 *
 */

public class ResponseEntity {

    @SerializedName("photos")
    Photos photos;

    @SerializedName("perpage")
    int perpage;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }
}