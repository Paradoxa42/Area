/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.*;

import com.google.inject.Provider;
import conf.*;
import models.ActionReaction;
import models.Article;
import models.User;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.ArticleDao;
import dao.SetupDao;
import ninja.params.PathParam;
import ninja.session.Session;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ApplicationController {
    private ArrayList<ModuleEntity> ListModules;

    @Inject
    Provider<EntityManager> entitiyManagerProvider;

    @Inject
    ArticleDao articleDao;

    @Inject
    SetupDao setupDao;

    public ApplicationController() {

    }

    /**
     * Method to put initial data in the db...
     *
     * @return
     */
    public Result setup() {

        setupDao.setup();

        return Results.ok();

    }

    public Result facebookApi() {
        SimplePojo simplePojo = new SimplePojo();
        FacebookApi facebookApi = new FacebookApi();
        facebookApi.init();
        simplePojo.content = "Connection = " + "     notification = ";
        return Results.json().render(simplePojo);
    }

    public Result epitechApi() {
        SimplePojo simplePojo = new SimplePojo();
        EpitechApi epi = new EpitechApi("joseph.demersseman@epitech.eu", "");
        epi.init();
        simplePojo.content = "Connection = " + epi.getConnection() + "     notification = " + epi.getNotifications();
        return Results.json().render(simplePojo);
    }

    public Result front(Session session) {
        return Results.html().template("views/ApplicationController/front.ftl.html");
    }

    public Result Settings(Session session) {
        session.put("ID_CLIENT", "100");
        return Results.html().template("views/ApplicationController/Settings.ftl.html");
    }

    public Result postSettings(Session session, Context context) {
        return Results.noContent();
    }

    public Result index() {

        Article frontPost = articleDao.getFirstArticleForFrontPage();

        List<Article> olderPosts = articleDao.getOlderArticlesForFrontPage();

        Map<String, Object> map = Maps.newHashMap();
        map.put("frontArticle", frontPost);
        map.put("olderArticles", olderPosts);

        return Results.html().render("frontArticle", frontPost)
                .render("olderArticles", olderPosts);
    }

    public Result settings(Context context, react form) {
        return Results.html().template("views/ApplicationController/Settings.ftl.html").render(form);
    }

    public void settingsParams(Session session, @PathParam("IdFacebook") int IdFacebook) {
        session.put("ID_FACEBOOK", String.valueOf(IdFacebook) );
    }

    public Result postsettingUpdate(Context context, react form) {
        context.getParameter("notification");
        System.out.println(context.toString());
        System.out.println(String.format("notification : %b, post_status_f : %b, like_page : %b, add_playlist %b, add_video : %b", context.getParameter("notification"), form.post_status_fb, form.like_page, form.add_playlist, form.add_video));
        return Results.redirect("/settings");
    }

    public static class SimplePojo {

        public String content;

    }

    public Result update() {
        System.out.println("modules = " + ListModules);
        if (ListModules == null) {
            ListModules = new ArrayList<ModuleEntity>();
            ArrayList<Item> reactions = new ArrayList<Item>();
            reactions.add(new Item(400));
            ListModules.add(new ModuleEntity(new Item(305), reactions));
            System.out.println(ListModules.toString());
        }
        for (ModuleEntity module : ListModules) {
            String result = null;
            if (module.getAction().getId() >= 300 && module.getAction().getId() < 400) {
                FacebookApi facebookApi = (FacebookApi) module.getAction().getAction();
                if (module.getAction().getId() == 305)
                    result = facebookApi.getPagePost();
            }
            if (result != null && result.length() > 0) {
                try {
                    JSONObject obj = new JSONObject(result);
                    for (Item item : module.getReaction()) {
                        if (module.getAction().getId() >= 300 && module.getAction().getId() < 400) {
                            FacebookApi facebookApi = (FacebookApi) item.getAction();
                            System.out.println(result);
                            if (module.getAction().getId() == 300)
                                facebookApi.PostMessage(obj.getString("id"), obj.getString("message"));
                        } else {
                            DropboxApi dropboxApi = (DropboxApi) item.getAction();
                            dropboxApi.SaveFile(obj.getString("id"), obj.getString("message"));
                        }
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return Results.redirect("/");
    }
}