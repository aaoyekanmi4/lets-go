package learn.letsgo.Models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int groupId;
    private int appUserId;
    private String name;

    private List<Contact> contacts = new ArrayList<>();

    public Group(){

    }

    public Group(int appUserId, String name) {
        this.appUserId = appUserId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", appUserId=" + appUserId +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
