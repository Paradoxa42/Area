package conf;


import com.restfb.*;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.*;
import controllers.ApplicationController;

import java.util.List;


public class FacebookApi{
    private String accessToken;
    private String CheckingGroup = ""; //c'est le groupe que l'utilisateur a choisi lors de la création du module qui seras checker et recevras des modifs
    private String CheckingPage = "BraveFrontierRPG"; // c'est pareil que celle d'au dessus mais pour la page
    private Post LastPostPage = null;
    private Post LastPost = null;
    private Group LastGroup = null;
    private Page LastLikePage = null;
    private Post LastPostGroup = null;
    private Comment LastComment = null;
    private User LastFriend = null;
    private User me;

    public void init()
    {
        try {
            accessToken = "EAADPUjw0Ci4BAJSAZCBLfdFEwLw5VdSTZByHoRetN8BmqrhgiEw0mny9L1bXPkU6uIQXNAOmI83hQACR3enS5dG0PEFJp2vMWyYHvTaHjo4QVShiYJZAe5GB3tZAiDhSfzKJBqcQrTzZC3o85cLukXBGhO256MJwZD";
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            ScopeBuilder scopeBuilder = new ScopeBuilder();
            scopeBuilder.addPermission(UserDataPermissions.USER_ACTIONS_NEWS);
            scopeBuilder.addPermission(UserDataPermissions.USER_FRIENDS);
            scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);
            scopeBuilder.addPermission(UserDataPermissions.USER_LIKES);
            scopeBuilder.addPermission(UserDataPermissions.USER_POSTS);
            scopeBuilder.addPermission(UserDataPermissions.USER_PHOTOS);

            //loginDialogUrlString c'est l'url pour demander les permissions (il faudrait le faire apparaitre pour l'utilisateur)
            String loginDialogUrlString = fbClient.getLoginDialogUrl("227952104311342", "http://localhost:8080/", scopeBuilder);
            System.out.println(loginDialogUrlString);
            me = fbClient.fetchObject("1834609629898375", User.class);
            System.out.println("name = " + me.getName() + "  data = " + me);
        }
        catch (Exception ex)
        {
            System.out.println("Problem with accessToken");
            System.out.println(ex.getMessage());
        }
        getPost(); // recuperation des posts
        getGroup(); // recuperation des goupes que le compte est inscrit
        getHomePost(); //
        getLikesPage(); // recuperation des pages liked
        getGroupPost(); //
        getPagePost();
        getComment();
        getFriend();
        System.out.println("End");
        try
        {
            System.out.println("Post = " +LastPost.getStory());
            //System.out.println("Group = " +LastGroup.getName());
            System.out.println("Likes = " +LastLikePage.getName());
            //System.out.println("GroupPost = " +LastPostGroup.getMessage());
            System.out.println("PagePost = " +LastPostPage.getMessage());
            System.out.println("Friend = " + LastFriend);
            System.out.println("Comment = " +LastComment.getMessage());
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
    }

    //Les fonctions ci-dessous renvoient lorsqu'il y a un changement un String qui possède les infos de la classe sinon retourne null s'il n'y a pas de modifications

    public String getFriend()
    {
        System.out.println("Friend");
        try {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<User> friends = fbClient.fetchConnection(me.getId()+"/friends", User.class);
            List<User> FriendList = friends.getData();
            System.out.println("nom du Messager = " + FriendList.get(0).getName());
            System.out.println("fb.com/" + FriendList.get(0).getId());
            int counter = 0;
            for (List<User> groupPage : friends) {
                for (User aGroup : groupPage) {
                    counter++;
                }
            }
            System.out.println("number of result = " + counter);

            if (LastFriend == null)
            {
                LastFriend = FriendList.get(0);
                return "";
            }
            else if (!LastFriend.getId().equals(FriendList.get(0).getId()))
            {
                LastFriend = FriendList.get(0);
                return FriendList.get(0).toString();
            }

            return null;
        }
        catch (Exception ex)
        {
            System.err.println("Exeption = " + ex.getMessage());
            return (ex.getMessage());
        }
    }

    public String getComment()
    {
        System.out.println("Comment");
        try {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Comment> comments = fbClient.fetchConnection(me.getId()+"/comments", Comment.class);
            List<Comment> commentList = comments.getData();
            System.out.println("nom du Messager = " + commentList.get(0).getMessage());
            System.out.println("date du Messager = " + commentList.get(0).getCreatedTime());
            System.out.println("fb.com/" + commentList.get(0).getId());
            int counter = 0;
            for (List<Comment> groupPage : comments) {
                for (Comment aGroup : groupPage) {
                    counter++;
                }
            }
            System.out.println("number of result = " + counter + "   groupe = "+comments);
            if (LastComment == null)
            {
                LastComment = commentList.get(0);
                return "";
            }
            else if (!LastComment.getId().equals(commentList.get(0).getId()))
            {
                LastComment = commentList.get(0);
                return LastComment.toString();
            }

            return null;
        }
        catch (Exception ex)
        {
            System.err.println("Exeption = " + ex.getMessage());
            return (ex.getMessage());
        }
    }

    public String getGroup()
    {
        System.out.println("Group");
        try {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Group> groups = fbClient.fetchConnection(me.getId()+"/groups", Group.class);
            List<Group> groupList = groups.getData();
            System.out.println("nom du Messager = " + groupList.get(0).getName());
            System.out.println("fb.com/" + groupList.get(0).getId());

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
            if (LastGroup == null)
            {
                LastGroup = groupList.get(0);
                return "";
            }
            else if (!LastGroup.getId().equals(groupList.get(0).getId()))
            {
                LastGroup = groupList.get(0);
                return LastGroup.toString();
            }
            return null;
        }
         catch (Exception ex)
            {
                System.err.println(ex.getMessage());
                return ex.getMessage();
            }
    }

    public String getPagePost()
    {
        System.out.println("Page Post");
        try {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Page page = fbClient.fetchObject(CheckingPage,Page.class);
            Connection<Post> postFeed = fbClient.fetchConnection(page.getId()+"/feed", Post.class);
            List<Post> PagePost = postFeed.getData();
            System.out.println("nom du Messager = " + PagePost.get(0).getName());
            System.out.println("message = " + PagePost.get(0).getMessage());
            System.out.println("date = "+ PagePost.get(0).getCreatedTime());
            System.out.println("fb.com/" + PagePost.get(0).getId());
            if (LastPostPage == null)
            {
                LastPostPage = PagePost.get(0);
                return "";
            }
            else if (!LastPostPage.getId().equals(PagePost.get(0).getId()))
            {

                //Lorsqu'un Post est mis sur la page demander lors de la création du module
                LastPostPage = PagePost.get(0);
                return LastPostPage.toString();
            }
            return null;

        } catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String getGroupPost()
    {
        System.out.println("Group Post");
        if (CheckingGroup.length() != 0) {
            try {

                FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
                System.out.println("TEST");
                Group group = fbClient.fetchObject(CheckingGroup, Group.class);
                System.out.println("GROUP");
                Connection<Post> postFeed = fbClient.fetchConnection(group.getId() + "/feed", Post.class);

                List<Post> ListPost = postFeed.getData();
                int counter = 0;
                for (List<Post> groupPost : postFeed) {
                    for (Post aPost : groupPost) {
                        System.out.println("nom du Messager = " + aPost.getName());
                        System.out.println("message = " + aPost.getMessage());
                        counter++;
                    }
                }
                System.out.println("number of result = " + counter);
                if (LastPostGroup == null)
                {
                    LastPostGroup = ListPost.get(0);
                    return "";
                }
                else if (!LastPostGroup.getId().equals(ListPost.get(0).getId()))
                {
                    //lorsqu'il y a un nouveau post dans le group defini lors de la création du module
                    LastPostGroup = ListPost.get(0);
                    return LastPostGroup.toString();
                }
               return null;
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return  ex.getMessage();
            }
        }
        return null;
    }

    public String getHomePost()
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

            return null;
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String getLikesPage()
    {
        System.out.println("Likes Page");
        try
        {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Page> result = fbClient.fetchConnection(me.getId()+"/likes",Page.class);

            List<Page> groupPost = result.getData();
            System.out.println("nom du Messager = " + groupPost.get(0).getName());
            System.out.println("message = " + groupPost.get(0).getLikes());
            System.out.println("fb.com/" + groupPost.get(0).getId());
            int counter = 0;
            for (List<Page> page : result)
            {
                for (Page aPage :page)
                {
                    counter++;
                }
            }
            System.out.println("number of result = "+ counter + "    like nbr = "+ me);
            if (LastLikePage == null) {
                LastLikePage = groupPost.get(0);
                return "";
            }
            else if (!LastLikePage.getId().equals(groupPost.get(0).getId()))
            {
                //Si il y a une nouvelle page like par l'utilisateur
                LastLikePage = groupPost.get(0);
                return LastLikePage.toString();
            }

            return null;
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String getPost()
    {
        System.out.println("Post");
        try
        {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            Connection<Post> result = fbClient.fetchConnection(me.getId()+"/feed",Post.class);
            int counter = 0;

            List<Post> groupPost = result.getData();
            System.out.println("nom du Messager = " + groupPost.get(0).getName());
            System.out.println("message = " + groupPost.get(0).getMessage());
            System.out.println("date = "+ groupPost.get(0).getCreatedTime());
            System.out.println("fb.com/" + groupPost.get(0).getId());
            for (List<Post> page : result)
            {
                for (Post aPost :page)
                {
                    counter++;
                }
            }
            System.out.println("number of result = "+ counter);
            if (LastPost == null) {
                LastPost = groupPost.get(0);
                return "";
            }
            else if (LastPost.getCreatedTime().compareTo(groupPost.get(0).getCreatedTime()) != 0)
            {
                //Lorsque l'utilisateur post quelque chose
                LastPost = groupPost.get(0);
                return LastPost.toString();
            }
            return null;
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }


    //creation d'un post
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
