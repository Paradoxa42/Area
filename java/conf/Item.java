package conf;

/**
 * Created by demers_j on 08/01/2017.
 */
public class Item {
    private int id;
    private Object action;

    public Item(int id) {
        this.id = id;
        if (id >= 300 && id < 400) {
            this.action = new FacebookApi();
        }
        else
        {
            this.action = new DropboxApi();
        }
    }

    public int getId() {
        return id;
    }

    public Object getAction() {
        return action;
    }
}
