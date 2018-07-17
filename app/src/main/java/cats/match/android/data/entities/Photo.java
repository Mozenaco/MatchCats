package cats.match.android.data.entities;

/**
 * Class Photo responsible for store data of the serialized object from the service
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
import com.google.gson.annotations.SerializedName;

public class Photo {

    public Photo(String id, String secret, String server, String farm) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
    }
    @SerializedName("id")
    private String id;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private String farm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getUrlString(){

        return "https://farm" + this.farm + ".staticflickr.com/" + this.server + "/" +
                this.id + "_" + this.secret + "_q.jpg";
    }
}