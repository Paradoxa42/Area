package conf;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import org.omg.CORBA.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class DropboxApi {
    private static final String ACCESS_TOKEN = "nlGxULDPZxAAAAAAAAAAE8Vftr1eO2alcCFzFJJt0ZgCb7uD4BuDk8GYWz6bi-iU";
    private DbxClientV2 client;

    public void init()
    {
        DbxRequestConfig config;
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
        try {
            File file;
            file = new File("C:\\Users\\demers_j\\Desktop\\test\\test.txt");
            System.out.println("Path : " + file.getAbsolutePath() + "   space = " + file.getTotalSpace());
            System.out.println();
            // Upload "test.txt" to Dropbox
            try (InputStream in = new FileInputStream(file)) {
                FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                        .uploadAndFinish(in);
                System.out.println("metadata = " + metadata.toString());
                SaveFile("12332133267", "Hello i like you ");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public void SaveFile(String fileName, String save)
    {
        try {
            File tempFile = File.createTempFile(fileName, ".txt");
            System.out.format("Canonical filename: %s\n", tempFile.getCanonicalFile());
            InputStream in = new FileInputStream(tempFile);
            FileMetadata metadata = client.files().uploadBuilder( "/"+fileName+".txt")
                        .uploadAndFinish(in);
        }
        catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    
}
