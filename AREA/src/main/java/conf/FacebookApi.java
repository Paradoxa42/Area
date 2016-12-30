package conf;


import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

/**
 * Created by demers_j on 30/12/2016.
 */
public class FacebookApi {
    public void init()
    {
        System.out.println("teeeeeest    facebook api");
        String accessToken ="EAACEdEose0cBAJ1ZCnCZBXcsVKlExwtyddZAFOjNkf8tviaKySviuDc01KL7mvWKExMZCx44BaZBPSfyKrHTvvnh7ETCv5B3qmUjzMa5bYxdqVLmJGwGZCzku40sGoKLnsDhX79Pqa1aIwdPR48uvlR6emxLgrLkRXIQKuNXZBM3AZDZD";
        FacebookClient  fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        User me = fbClient.fetchObject("me", User.class);
        System.out.println("name = "+me.getName());
    }
}
