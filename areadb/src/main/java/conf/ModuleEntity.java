package conf;

import java.util.ArrayList;
import java.util.List;

public class ModuleEntity {
    private Item action;
    private ArrayList<Item> reactionList;

    public ModuleEntity(Item action, ArrayList<Item> reactionList) {
        this.action = action;
        this.reactionList = reactionList;

    }

    public Item getAction() {
        return action;
    }

    public void setAction(Item action) {
        this.action = action;
    }

    public List<Item> getReaction() {
        return reactionList;
    }

   public void addReaction(Item reaction)
   {
       reactionList.add(reaction);
   }
}
