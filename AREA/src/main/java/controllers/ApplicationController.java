/**
 * Copyright (C) 2013 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import com.restfb.types.Page;
import com.sun.javafx.binding.BidirectionalBinding;
import conf.*;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Singleton
public class ApplicationController {
    private List<ModuleEntity> Modules = new List<ModuleEntity>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<ModuleEntity> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(ModuleEntity moduleEntity) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends ModuleEntity> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends ModuleEntity> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public ModuleEntity get(int index) {
            return null;
        }

        @Override
        public ModuleEntity set(int index, ModuleEntity element) {
            return null;
        }

        @Override
        public void add(int index, ModuleEntity element) {

        }

        @Override
        public ModuleEntity remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<ModuleEntity> listIterator() {
            return null;
        }

        @Override
        public ListIterator<ModuleEntity> listIterator(int index) {
            return null;
        }

        @Override
        public List<ModuleEntity> subList(int fromIndex, int toIndex) {
            return null;
        }
    };


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

    public Result epitechApi() {
        SimplePojo simplePojo = new SimplePojo();
        EpitechApi epi = new EpitechApi("joseph.demersseman@epitech.eu", "");
        epi.init();
        simplePojo.content = "Connection = " + epi.getConnection() + "     notification = " + epi.getNotifications();
        return Results.json().render(simplePojo);
    }

    public Result front() {
        return Results.html().template("views/ApplicationController/front.ftl.html");
    }

    public Result settings(Context context, react form) {
        List<Item> reactions = new List<Item>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Item> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Item item) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Item> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Item> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Item get(int index) {
                return null;
            }

            @Override
            public Item set(int index, Item element) {
                return null;
            }

            @Override
            public void add(int index, Item element) {

            }

            @Override
            public Item remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Item> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Item> listIterator(int index) {
                return null;
            }

            @Override
            public List<Item> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        reactions.add(new Item(400));
        Modules.add(new ModuleEntity(new Item(305), reactions));
        DropboxApi dropbox = new DropboxApi();
        dropbox.init();
        return Results.html().template("views/ApplicationController/Settings.ftl.html").render(form);
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

    public void update() {
        String result;
        for (ModuleEntity module : Modules) {
            result = null;
            if (module.getAction().getId()>= 300 && module.getAction().getId()< 400) {
                FacebookApi facebookApi= (FacebookApi) module.getAction().getAction();
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
                }
                catch (Exception ex)
                {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
