package chatemgrupo;

public class ChatItem {

    private String name;
    private String lastMessage;
    private String time;
    private boolean isIndividual;
    private boolean isOnline;
    private int unreadCount;

    public ChatItem(String name, String lastMessage, String time, boolean isIndividual) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.time = time;
        this.isIndividual = isIndividual;
        this.isOnline = Math.random() > 0.5; // Aleatório para demo
        this.unreadCount = (int) (Math.random() * 5);
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIndividual() {
        return isIndividual;
    }

    public void setIndividual(boolean individual) {
        isIndividual = individual;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
