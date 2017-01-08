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

import java.util.List;
import java.util.Map;

import com.google.inject.Provider;
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
import ninja.session.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ApplicationController {

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

    public Result front(Session session)
    {
        return Results.html().template("views/ApplicationController/front.ftl.html");
    }

    public Result Settings(Session session)
    {
        session.put("ID_CLIENT", "100");
        return Results.html().template("views/ApplicationController/Settings.ftl.html");
    }

    public Result postSettings(Session session, Context context)
    {
        EntityManager entityManager = entitiyManagerProvider.get();
        Query q = entityManager.createNativeQuery("SELECT * FROM ActionReaction");
        //WHERE p.id_client LIKE " + session.get("ID_CLIENT") + " and p.id_action = " + context.getParameter("idAction")
        List<ActionReaction> action = q.getResultList();
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
}
