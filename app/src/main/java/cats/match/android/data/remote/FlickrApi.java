package cats.match.android.data.remote;

/**
 *
 * @author Mateus Andrade
 * @since 06/07/18
 *
 */

public class FlickrApi {

    private static final String BASE_URL = "https://api.flickr.com/";

    public static FlickrService getService() {
        return RetrofitClient.getClient(BASE_URL).create(FlickrService.class);
    }
}