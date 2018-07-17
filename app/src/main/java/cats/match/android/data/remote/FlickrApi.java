package cats.match.android.data.remote;

/**
 * FlickrApi class that provide the service. This service is used for get urls of valid images from
 * Flickr servers.
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class FlickrApi {

    private static final String BASE_URL = "https://api.flickr.com/";

    public static FlickrService getService() {
        return RetrofitClient.getClient(BASE_URL).create(FlickrService.class);
    }
}