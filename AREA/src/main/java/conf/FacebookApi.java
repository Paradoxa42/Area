package conf;


import com.restfb.*;
import com.restfb.types.*;
import javafx.geometry.Pos;

import java.util.List;


public class FacebookApi {
    private String accessToken;
    private String CheckingGroup = "";
    private String CheckingPage = "BraveFrontierRPG";
    private Post LastPostPage;
    private Post LastPost;
    private Group LastGroup;
    private User me;

    public void init()
    {
        try {
            accessToken = "EAADPUjw0Ci4BAJSAZCBLfdFEwLw5VdSTZByHoRetN8BmqrhgiEw0mny9L1bXPkU6uIQXNAOmI83hQACR3enS5dG0PEFJp2vMWyYHvTaHjo4QVShiYJZAe5GB3tZAiDhSfzKJBqcQrTzZC3o85cLukXBGhO256MJwZD";
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            me = fbClient.fetchObject("1834609629898375", User.class);
            System.out.println("name = " + me.getName() + "  data = "+ me);
        }
        catch (Exception ex)
        {
            System.out.println("Problem with accessToken");
            System.out.println(ex.getMessage());
        }
        getPost();
        getGroup();
        getHomePost();
        getLikesPage();
        getGroupPost();
        getPagePost();
        System.out.println("End");
    }

    private void getGroup()
    {
        System.out.println("Group");
        try {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Group> groups = fbClient.fetchConnection(me.getId()+"/groups", Group.class);
            int counter = 0;
            for (List<Group> groupPage : groups) {
                for (Group aGroup : groupPage) {
                    System.out.println("nom du Groupe = " + aGroup.getName());
                    System.out.println("nom du chef du Groupe = " + aGroup.getName());
                    System.out.println("fb.com/" + aGroup.getId());
                    counter++;
                }
            }
            System.out.println("number of result = " + counter + "   groupe = "+groups);
        }
         catch (Exception ex)
            {
                System.err.println(ex.getMessage());
            }
    }

    private void getPagePost()
    {
        System.out.println("Page Post");
        try {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Page page = fbClient.fetchObject(CheckingPage,Page.class);
            Connection<Post> postFeed = fbClient.fetchConnection(page.getId()+"/feed", Post.class);
            List<Post> groupPost = postFeed.getData();
            System.out.println("nom du Messager = " + groupPost.get(0).getName());
            System.out.println("message = " + groupPost.get(0).getMessage());
            System.out.println("date = "+ groupPost.get(0).getCreatedTime());
            System.out.println("fb.com/" + groupPost.get(0).getId());
        } catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private void getGroupPost()
    {
        System.out.println("Group Post");
        if (CheckingGroup.length() != 0) {
            try {

                FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
                System.out.println("TEST");
                Group group = fbClient.fetchObject(CheckingGroup, Group.class);
                System.out.println("GROUP");
                Connection<Post> postFeed = fbClient.fetchConnection(group.getId() + "/feed", Post.class);
                int counter = 0;
                for (List<Post> groupPost : postFeed) {
                    for (Post aPost : groupPost) {
                        System.out.println("nom du Messager = " + aPost.getName());
                        System.out.println("message = " + aPost.getMessage());
                        counter++;
                    }
                }
                System.out.println("number of result = " + counter);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void getHomePost()
    {

        System.out.println("Home");
        try
        {
            FacebookClient  fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Post> result = fbClient.fetchConnection(me.getId()+"/home",Post.class);
            int counter = 0;
            for (List<Post> page : result)
            {
                for (Post aPost :page)
                {
                    System.out.println("home poster = " + aPost.getMessage());
                    System.out.println("fb.com/"+aPost.getId());
                    counter++;
                }
            }
            System.out.println("number of result = "+ counter);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private void getLikesPage()
    {
        System.out.println("Likes Page");
        try
        {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            System.out.println("Likes test");
            Connection<Page> result = fbClient.fetchConnection(me.getId()+"/likes",Page.class);
            System.out.println("Like work");
//            List<Likes.LikeItem> like = me.getLikes().getData();
            int counter = 0;
            for (List<Page> page : result)
            {
                for (Page aPage :page)
                {
                    System.out.println("page name = " + aPage.getName());
                    System.out.println("page description = " + aPage.getDescription());
                    System.out.println("fb.com/"+aPage.getId());
                    counter++;
                }
            }
            System.out.println("number of result = "+ counter + "    like nbr = "+ me);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private void getPost()
    {
        System.out.println("Post");
        try
        {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Post> result = fbClient.fetchConnection(me.getId()+"/feed",Post.class);
            int counter = 0;
            for (List<Post> page : result)
            {
                for (Post aPost :page)
                {
                    System.out.println("message poster = " + aPost.getMessage());
                    System.out.println("fb.com/"+aPost.getId());
                    counter++;
                }
            }
            System.out.println("number of result = "+ counter);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }


    //Post
    public void PostMessage()
    {
        try {
            FacebookClient fbclient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            FacebookType message = fbclient.publish(me.getId()+"/feed", FacebookType.class, Parameter.with("message", "TEST"));
            System.out.println(message.getId());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

}
