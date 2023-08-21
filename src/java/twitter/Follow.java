/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package twitter;

/**
 *
 * @author Owner
 */
public class Follow {
    private int id;
    private int followedbyuid;
    private int followeduid;

    public Follow(int id, int followedbyuid, int followeduid) {
        this.id = id;
        this.followedbyuid = followedbyuid;
        this.followeduid = followeduid;
    }

    public int getId() {
        return id;
    }

    public int getFollowedbyuid() {
        return followedbyuid;
    }

    public int getFolloweduid() {
        return followeduid;
    }

    public void setFollowedbyuid(int followedbyuid) {
        this.followedbyuid = followedbyuid;
    }
}
