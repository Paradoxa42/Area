package conf;
/*
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.InputStream;


public class DropboxApi {
    private static final String ACCESS_TOKEN = "nlGxULDPZxAAAAAAAAAAE8Vftr1eO2alcCFzFJJt0ZgCb7uD4BuDk8GYWz6bi-iU";

    public void init()
    {
        DbxRequestConfig config;
        DbxClientV2 client;
        try {
            // Create Dropbox client
            config = new DbxRequestConfig("dropbox/Area");
            client = new DbxClientV2(config, ACCESS_TOKEN);
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());
            ListFolderResult result = client.files().listFolder("");
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    System.out.println(metadata.getPathLower());
                }

                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }
        }
        catch (DbxException ex)
        {
            System.err.println(ex.getMessage());
            return;
        }

        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream("test.txt")) {
            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                    .uploadAndFinish(in);
            System.out.println("metadata = " + metadata.toString());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }
}
*/