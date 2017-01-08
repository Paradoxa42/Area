package conf;


import com.restfb.*;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.*;
import controllers.ApplicationController;

import java.util.List;


public class FacebookApi {
    private String accessToken;
    private String CheckingGroup = ""; //c'est le groupe que l'utilisateur a choisi lors de la création du module qui seras checker et recevras des modifs
    private String CheckingPage = "BraveFrontierRPG"; // c'est pareil que celle d'au dessus mais pour la page
    private User LastFriend = null;
    private FacebookClient fbClient;

    private Group group = null;
    private Page page = null;
    private Comment comment = null;
    private Post post = null;

    public void init() {
        try {
            accessToken = "EAADPUjw0Ci4BAJSAZCBLfdFEwLw5VdSTZByHoRetN8BmqrhgiEw0mny9L1bXPkU6uIQXNAOmI83hQACR3enS5dG0PEFJp2vMWyYHvTaHjo4QVShiYJZAe5GB3tZAiDhSfzKJBqcQrTzZC3o85cLukXBGhO256MJwZD";
            fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            ScopeBuilder scopeBuilder = new ScopeBuilder();
            scopeBuilder.addPermission(UserDataPermissions.USER_ACTIONS_NEWS);
            scopeBuilder.addPermission(UserDataPermissions.USER_FRIENDS);
            scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);
            scopeBuilder.addPermission(UserDataPermissions.USER_LIKES);
            scopeBuilder.addPermission(UserDataPermissions.USER_POSTS);
            scopeBuilder.addPermission(UserDataPermissions.USER_PHOTOS);

            //loginDialogUrlString c'est l'url pour demander les permissions (il faudrait le faire apparaitre pour l'utilisateur)
            String loginDialogUrlString = fbClient.getLoginDialogUrl("me", "http://localhost:8080/", scopeBuilder);
            System.out.println(loginDialogUrlString);
        } catch (Exception ex) {
            System.out.println("Problem with accessToken");
            System.out.println(ex.getMessage());
        }
    }

    //Les fonctions ci-dessous renvoient lorsqu'il y a un changement un String qui possède les infos de la classe sinon retourne null s'il n'y a pas de modifications

    public String getFriend() {
        System.out.println("Friend");
        try {
            Connection<User> friends = fbClient.fetchConnection("me/friends", User.class);
            List<User> FriendList = friends.getData();
            System.out.println("nom du Messager = " + FriendList.get(0).getName());
            System.out.println("number of result d'amis= " + friends.getTotalCount() + "  friends =  " + friends.toString());

            if (LastFriend == null) {
                LastFriend = FriendList.get(0);
                return "";
            } else if (!LastFriend.getId().equals(FriendList.get(0).getId())) {
                LastFriend = FriendList.get(0);
                return FriendList.get(0).toString();
            }
            return null;
        } catch (Exception ex) {
            System.err.println("Exeption = " + ex.getMessage());
            return (ex.getMessage());
        }
    }

    public String getComment() {
        System.out.println("Comment");
        try {
            Connection<Comment> comments = fbClient.fetchConnection("me/comments", Comment.class);
            List<Comment> commentList = comments.getData();
            System.out.println("Comment = " + commentList.get(0).toString());
            int counter = 0;
            for (List<Comment> groupPage : comments) {
                for (Comment aGroup : groupPage) {
                    counter++;
                }
            }
            System.out.println("number of result = " + counter + "   comment = " + comments);
            if (comment == null) {
                comment = commentList.get(0);
                return "";
            } else if (!comment.getId().equals(commentList.get(0).getId())) {
                comment = commentList.get(0);
                return comment.toString();
            }

            return null;
        } catch (Exception ex) {
            System.err.println("Exeption = " + ex.getMessage());
            return (ex.getMessage());
        }
    }

    public String getGroup() {
        System.out.println("Group");
        try {
            Connection<Group> groups = fbClient.fetchConnection("me/groups", Group.class);
            List<Group> groupList = groups.getData();
            System.out.println("nom du Messager = " + groupList.get(0).getName());

            int counter = 0;
            for (List<Group> groupPage : groups) {
                for (Group aGroup : groupPage) {
                    counter++;
                }
            }
            System.out.println("number of result = " + counter + "   group = " + groups);
            if (group == null) {
                group = groupList.get(0);
                return "";
            } else if (!group.getId().equals(groupList.get(0).getId())) {
                group = groupList.get(0);
                return groupList.get(0).toString();
            }
            return null;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String getPagePost() {
        System.out.println("Page Post");
        try {
            Page page = fbClient.fetchObject(CheckingPage, Page.class);
            Connection<Post> postFeed = fbClient.fetchConnection(page.getId() + "/feed", Post.class);
            List<Post> PagePost = postFeed.getData();
            System.out.println("nom du Messager = " + PagePost.get(0).getName());
            System.out.println("message = " + PagePost.get(0).getMessage());
            System.out.println("date = " + PagePost.get(0).getCreatedTime());
            if (post == null) {
                post = PagePost.get(0);
                return "";
            } else if (!post.getId().equals(PagePost.get(0).getId())) {
                post = PagePost.get(0);
                return post.toString();
            }
            return null;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String getGroupPost() {
        System.out.println("Group Post");
        if (CheckingGroup.length() != 0) {
            try {
                Group group = fbClient.fetchObject(CheckingGroup, Group.class);
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
                if (post == null) {
                    post = ListPost.get(0);
                    return "";
                } else if (!post.getId().equals(ListPost.get(0).getId())) {
                    //lorsqu'il y a un nouveau post dans le group defini lors de la création du module
                    post = ListPost.get(0);
                    return post.toString();
                }
                return null;
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return ex.getMessage();
            }
        }
        return null;
    }


    public String getLikesPage() {
        try {
            Connection<Page> result = fbClient.fetchConnection("me/likes", Page.class);

            List<Page> PageList = result.getData();
            int counter = 0;
            for (List<Page> page : result) {
                for (Page aPage : page) {
                    counter++;
                }
            }
            if (page == null) {
                page = PageList.get(0);
                return "";
            } else if (!page.getId().equals(PageList.get(0).getId())) {
                page = PageList.get(0);
                return page.toString();
            }

            return null;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String getPost() {
        System.out.println("Post");
        try {
            Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
            int counter = 0;

            List<Post> groupPost = result.getData();
            for (List<Post> page : result) {
                for (Post aPost : page) {
                    counter++;
                }
            }
            System.out.println("number of result = " + counter);
            if (post == null) {
                post = groupPost.get(0);
                return "";
            } else if (post.getCreatedTime().compareTo(groupPost.get(0).getCreatedTime()) != 0) {
                //Lorsque l'utilisateur post quelque chose
                post = groupPost.get(0);
                return post.toString();
            }
            return null;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    //creation d'un post
    public void PostMessage(String idToSend, String message) {
        try {
            FacebookType messageResult = fbClient.publish(idToSend + "/feed", FacebookType.class, Parameter.with("message", message));
            System.out.println(messageResult.toString());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


}
