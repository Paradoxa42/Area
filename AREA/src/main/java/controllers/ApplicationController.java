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

import com.sun.javafx.binding.BidirectionalBinding;
import ninja.Context;
import conf.EpitechApi;
import conf.FacebookApi;
import ninja.Result;
import ninja.Results;
import conf.react;

import com.google.inject.Singleton;
import ninja.params.PathParam;


@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }
    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }

    public Result facebookApi() {
        SimplePojo simplePojo = new SimplePojo();
        FacebookApi facebookApi = new FacebookApi();
        facebookApi.init();
        simplePojo.content = "Connection = " + "     notification = ";
        return Results.json().render(simplePojo);
    }

    public Result epitechApi()
    {
        SimplePojo simplePojo = new SimplePojo();
        EpitechApi epi = new EpitechApi("joseph.demersseman@epitech.eu","=bdl2SL^");
        epi.init();
        simplePojo.content = "Connection = " + epi.getConnection()+ "     notification = "+ epi.getNotifications();
        return Results.json().render(simplePojo);
    }

    public Result front()
    {
        return Results.html().template("views/ApplicationController/front.ftl.html");
    }

    public Result settings(Context context, react form)
    {
        return Results.html().template("views/ApplicationController/Settings.ftl.html").render(form);
    }

    public Result postsettingUpdate(Context context, react form)
    {
        context.getParameter("notification");
        System.out.println(context.toString());
        System.out.println(String.format("notification : %b, post_status_f : %b, like_page : %b, add_playlist %b, add_video : %b", context.getParameter("notification"), form.post_status_fb, form.like_page, form.add_playlist, form.add_video));
        return Results.redirect("/settings");
    }

    public static class SimplePojo {

        public String content;
        
    }
}
