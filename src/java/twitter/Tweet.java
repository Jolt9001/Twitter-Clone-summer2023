
package twitter;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 *
 * @author Owner
 */
public class Tweet {
    private int id;
    private String text;
    private Timestamp timestamp;
    private int user_id;
    private Blob attachment;
    private int likes;

    public Tweet(int id, String text, Timestamp timestamp, int user_id, Blob attachment) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.attachment = attachment;
        this.likes = 0;
    }
    
    
    public Tweet(int id, String text, Timestamp timestamp, int user_id) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.likes = 0;
        this.attachment = null;
    }

    public Blob getAttachment() {
        return attachment;
    }

    public int getLikes() {
        return likes;
    }
    
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getUser_id() {
        return user_id;
    }
    
}
