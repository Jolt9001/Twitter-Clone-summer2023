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
    private int followinguid;

    public Follow(int id, int followedbyuid, int followinguid) {
        this.id = id;
        this.followedbyuid = followedbyuid;
        this.followinguid = followinguid;
    }

    public int getId() {
        return id;
    }

    public int getFollowedbyuid() {
        return followedbyuid;
    }

    public int getFollowinguid() {
        return followinguid;
    }
}
