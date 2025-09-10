package chatemgrupo;

import java.util.*;

public class ChatManager {

    private List<ChatItem> chats = new ArrayList<>();
    private Set<String> blockedUsers = new HashSet<>();

    public List<ChatItem> getChats() {
        return chats;
    }

    public void addChat(ChatItem chat) {
        chats.add(chat);
    }

    public void removeChat(ChatItem chat) {
        chats.remove(chat);
    }

    public void blockUser(String name) {
        blockedUsers.add(name);
    }

    public boolean isBlocked(String name) {
        return blockedUsers.contains(name);
    }

    public void unblockUser(String name) {
        blockedUsers.remove(name);
    }
}
