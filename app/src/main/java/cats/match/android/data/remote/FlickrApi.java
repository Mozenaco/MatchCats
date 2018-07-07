package cats.match.android.data.remote;

/**
 * Interface for call the api and get a json from FlickrApi using [Retrofit]
 *
 * @author Mateus Andrade
 * @since 06/07/18
 *
 */

public class FlickrApi {

    private static final String BASE_URL = "https://api.flickr.com/";

    public static Service getService() {
        return RetrofitClient.getClient(BASE_URL).create(Service.class);
    }
}