package com.fgp.model;

public class Comment {

    private String fromId;
    private int id;
    private boolean isRead;
    private long sendTime;
    private String content;
    private String targetId;
    private User fromUser;
    private User targetUser;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "fromId='" + fromId + '\'' +
                ", id=" + id +
                ", isRead=" + isRead +
                ", sendTime=" + sendTime +
                ", content='" + content + '\'' +
                ", targetId='" + targetId + '\'' +
                ", fromUser=" + fromUser +
                ", targetUser=" + targetUser +
                '}';
    }
}
