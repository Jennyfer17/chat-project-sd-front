package chatemgrupo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatApplication extends Application {

    private BorderPane mainLayout;
    private VBox chatListContainer;
    private VBox messageContainer;
    private TextField messageInput;
    private Label currentChatLabel;
    private ObservableList<ChatItem> chats;

    @Override
    public void start(Stage primaryStage) {
        initializeData();
        createMainInterface();
        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setTitle("ChatApp - Mensagens Instantâneas");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private void initializeData() {
        chats = FXCollections.observableArrayList();
        chats.addAll(
                new ChatItem("João Silva", "Olá, como você está?", "14:30", true),
                new ChatItem("Grupo Trabalho", "Maria: Vamos marcar a reunião", "13:45", false),
                new ChatItem("Ana Costa", "Obrigada pela ajuda!", "12:20", true),
                new ChatItem("Família", "Papai: Jantar às 19h", "11:30", false)
        );
    }

    private void createMainInterface() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");
        VBox leftSidebar = createLeftSidebar();
        VBox chatArea = createChatArea();
        VBox rightSidebar = createRightSidebar();
        mainLayout.setLeft(leftSidebar);
        mainLayout.setCenter(chatArea);
        mainLayout.setRight(rightSidebar);
    }

    private VBox createLeftSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(320);
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");
        sidebar.setPadding(new Insets(20));
        HBox userProfile = createUserProfileHeader();
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar conversas...");
        searchField.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #e0e0e0; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 10 15; -fx-font-size: 14px;");
        HBox actionButtons = new HBox(10);
        Button newChatBtn = new Button("Nova Conversa");
        Button newGroupBtn = new Button("Novo Grupo");
        newChatBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 8 16; -fx-font-size: 12px; -fx-font-weight: bold;");
        newGroupBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 8 16; -fx-font-size: 12px; -fx-font-weight: bold;");
        newChatBtn.setOnAction(e -> showNewChatDialog());
        newGroupBtn.setOnAction(e -> showNewGroupDialog());
        actionButtons.getChildren().addAll(newChatBtn, newGroupBtn);
        chatListContainer = new VBox(5);
        ScrollPane chatScrollPane = new ScrollPane(chatListContainer);
        chatScrollPane.setFitToWidth(true);
        chatScrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        updateChatList();
        sidebar.getChildren().addAll(userProfile, searchField, actionButtons, chatScrollPane);
        VBox.setVgrow(chatScrollPane, Priority.ALWAYS);
        return sidebar;
    }

    private HBox createUserProfileHeader() {
        HBox userProfile = new HBox(15);
        userProfile.setAlignment(Pos.CENTER_LEFT);
        userProfile.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 10;");
        userProfile.setPadding(new Insets(15));
        Circle avatar = new Circle(25);
        avatar.setFill(Color.WHITE);
        VBox userInfo = new VBox(2);
        Label userName = new Label("Seu Nome");
        userName.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label userStatus = new Label("Online");
        userStatus.setStyle("-fx-text-fill: #e8f5e8; -fx-font-size: 12px;");
        userInfo.getChildren().addAll(userName, userStatus);
        Button settingsBtn = new Button("⚙");
        settingsBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-color: white; -fx-border-radius: 15; -fx-background-radius: 15; -fx-min-width: 30; -fx-min-height: 30;");
        settingsBtn.setOnAction(e -> showSettingsDialog());
        userProfile.getChildren().addAll(avatar, userInfo, settingsBtn);
        HBox.setHgrow(userInfo, Priority.ALWAYS);
        return userProfile;
    }

    private VBox createChatArea() {
        VBox chatArea = new VBox();
        chatArea.setStyle("-fx-background-color: #ffffff;");
        HBox chatHeader = createChatHeader();
        messageContainer = new VBox(10);
        messageContainer.setPadding(new Insets(20));
        messageContainer.setStyle("-fx-background-color: #f9f9f9;");
        ScrollPane messageScrollPane = new ScrollPane(messageContainer);
        messageScrollPane.setFitToWidth(true);
        messageScrollPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: transparent;");
        HBox messageInputArea = createMessageInputArea();
        chatArea.getChildren().addAll(chatHeader, messageScrollPane, messageInputArea);
        VBox.setVgrow(messageScrollPane, Priority.ALWAYS);
        return chatArea;
    }

    private HBox createChatHeader() {
        HBox chatHeader = new HBox(15);
        chatHeader.setAlignment(Pos.CENTER_LEFT);
        chatHeader.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        chatHeader.setPadding(new Insets(15, 20, 15, 20));
        Circle contactAvatar = new Circle(20);
        contactAvatar.setFill(Color.web("#2196F3"));
        VBox contactInfo = new VBox(2);
        currentChatLabel = new Label("Selecione uma conversa");
        currentChatLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #333333;");
        Label contactStatus = new Label("Última vez online há 2 min");
        contactStatus.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        contactInfo.getChildren().addAll(currentChatLabel, contactStatus);
        HBox chatActions = new HBox(10);
        Button callBtn = new Button("📞");
        Button videoBtn = new Button("📹");
        Button moreBtn = new Button("⋮");
        String actionButtonStyle = "-fx-background-color: transparent; -fx-font-size: 18px; -fx-text-fill: #666666; -fx-border-color: transparent; -fx-background-radius: 20; -fx-min-width: 35; -fx-min-height: 35;";
        callBtn.setStyle(actionButtonStyle);
        videoBtn.setStyle(actionButtonStyle);
        moreBtn.setStyle(actionButtonStyle);
        chatActions.getChildren().addAll(callBtn, videoBtn, moreBtn);
        chatHeader.getChildren().addAll(contactAvatar, contactInfo, chatActions);
        HBox.setHgrow(contactInfo, Priority.ALWAYS);
        return chatHeader;
    }

    private HBox createMessageInputArea() {
        HBox inputArea = new HBox(10);
        inputArea.setAlignment(Pos.CENTER);
        inputArea.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1 0 0 0;");
        inputArea.setPadding(new Insets(15, 20, 15, 20));
        Button attachBtn = new Button("📎");
        attachBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 20px; -fx-text-fill: #666666; -fx-border-color: transparent; -fx-background-radius: 20; -fx-min-width: 40; -fx-min-height: 40;");
        messageInput = new TextField();
        messageInput.setPromptText("Digite sua mensagem...");
        messageInput.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #e0e0e0; -fx-border-radius: 25; -fx-background-radius: 25; -fx-padding: 12 20; -fx-font-size: 14px;");
        HBox.setHgrow(messageInput, Priority.ALWAYS);
        Button emojiBtn = new Button("😊");
        emojiBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 20px; -fx-text-fill: #666666; -fx-border-color: transparent; -fx-background-radius: 20; -fx-min-width: 40; -fx-min-height: 40;");
        Button sendBtn = new Button("➤");
        sendBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px; -fx-border-color: transparent; -fx-background-radius: 25; -fx-min-width: 50; -fx-min-height: 50;");
        sendBtn.setOnAction(e -> sendMessage());
        messageInput.setOnAction(e -> sendMessage());
        inputArea.getChildren().addAll(attachBtn, messageInput, emojiBtn, sendBtn);
        return inputArea;
    }

    private VBox createRightSidebar() {
        VBox rightSidebar = new VBox(20);
        rightSidebar.setPrefWidth(300);
        rightSidebar.setStyle("-fx-background-color: #fafafa; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 0 1;");
        rightSidebar.setPadding(new Insets(20));
        VBox contactInfo = new VBox(15);
        contactInfo.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");
        Circle largeAvatar = new Circle(50);
        largeAvatar.setFill(Color.web("#2196F3"));
        Label contactName = new Label("João Silva");
        contactName.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #333333;");
        Label contactPhone = new Label("+55 11 99999-9999");
        contactPhone.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        Label contactEmail = new Label("joao@email.com");
        contactEmail.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        contactInfo.getChildren().addAll(largeAvatar, contactName, contactPhone, contactEmail);
        contactInfo.setAlignment(Pos.CENTER);
        VBox chatOptions = new VBox(10);
        chatOptions.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");
        Label optionsTitle = new Label("Opções do Chat");
        optionsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #333333;");
        Button muteBtn = new Button("🔇 Silenciar");
        Button blockBtn = new Button("🚫 Bloquear");
        Button deleteBtn = new Button("🗑 Excluir Chat");
        muteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 10 15;");
        blockBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 10 15;");
        deleteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #f44336; -fx-font-size: 14px; -fx-padding: 10 15;");
        chatOptions.getChildren().addAll(optionsTitle, muteBtn, blockBtn, deleteBtn);
        rightSidebar.getChildren().addAll(contactInfo, new Separator(), chatOptions);
        return rightSidebar;
    }

    private void updateChatList() {
        chatListContainer.getChildren().clear();
        for (ChatItem chat : chats) {
            HBox chatItem = createChatListItem(chat);
            chatListContainer.getChildren().add(chatItem);
        }
    }

    private HBox createChatListItem(ChatItem chat) {
        HBox chatItem = new HBox(15);
        chatItem.setAlignment(Pos.CENTER_LEFT);
        chatItem.setStyle("-fx-background-color: transparent; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0; -fx-cursor: hand;");
        chatItem.setPadding(new Insets(12, 15, 12, 15));
        chatItem.setOnMouseEntered(e -> chatItem.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0; -fx-cursor: hand;"));
        chatItem.setOnMouseExited(e -> chatItem.setStyle("-fx-background-color: transparent; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0; -fx-cursor: hand;"));
        Circle avatar = new Circle(25);
        avatar.setFill(chat.isIndividual() ? Color.web("#4CAF50") : Color.web("#FF9800"));
        VBox chatInfo = new VBox(3);
        Label chatName = new Label(chat.getName());
        chatName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333333;");
        Label lastMessage = new Label(chat.getLastMessage());
        lastMessage.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        chatInfo.getChildren().addAll(chatName, lastMessage);
        HBox.setHgrow(chatInfo, Priority.ALWAYS);
        VBox sideInfo = new VBox(5);
        sideInfo.setAlignment(Pos.TOP_RIGHT);
        Label timeLabel = new Label(chat.getTime());
        timeLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #999999;");
        Circle unreadBadge = new Circle(8);
        unreadBadge.setFill(Color.web("#4CAF50"));
        unreadBadge.setVisible(Math.random() > 0.7);
        sideInfo.getChildren().addAll(timeLabel, unreadBadge);
        chatItem.getChildren().addAll(avatar, chatInfo, sideInfo);
        chatItem.setOnMouseClicked(e -> selectChat(chat));
        return chatItem;
    }

    private void selectChat(ChatItem chat) {
        currentChatLabel.setText(chat.getName());
        loadMessagesForChat(chat);
    }

    private void loadMessagesForChat(ChatItem chat) {
        messageContainer.getChildren().clear();
        addMessageToContainer("Olá! Como você está?", false, "14:25");
        addMessageToContainer("Oi! Estou bem, obrigado! E você?", true, "14:26");
        addMessageToContainer("Também estou bem! Vamos marcar aquele encontro?", false, "14:27");
        addMessageToContainer("Claro! Que tal amanhã às 15h?", true, "14:28");
    }

    private void addMessageToContainer(String text, boolean isOwn, String time) {
        HBox messageBox = new HBox();
        messageBox.setAlignment(isOwn ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        messageBox.setPadding(new Insets(5));
        VBox messageBubble = new VBox(5);
        messageBubble.setMaxWidth(400);
        if (isOwn) {
            messageBubble.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 18 18 5 18; -fx-padding: 12 16;");
        } else {
            messageBubble.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 18 18 18 5; -fx-padding: 12 16; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 18 18 18 5;");
        }
        Label messageText = new Label(text);
        messageText.setStyle("-fx-font-size: 14px; -fx-wrap-text: true; -fx-text-fill: " + (isOwn ? "white" : "#333333") + ";");
        messageText.setWrapText(true);
        Label messageTime = new Label(time);
        messageTime.setStyle("-fx-font-size: 10px; -fx-text-fill: " + (isOwn ? "#cccccc" : "#999999") + ";");
        messageBubble.getChildren().addAll(messageText, messageTime);
        messageBox.getChildren().add(messageBubble);
        messageContainer.getChildren().add(messageBox);
    }

    private void sendMessage() {
        String text = messageInput.getText().trim();
        if (!text.isEmpty()) {
            addMessageToContainer(text, true, "Agora");
            messageInput.clear();
        }
    }

    private void showNewChatDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nova Conversa");
        alert.setHeaderText("Criar Nova Conversa");
        alert.setContentText("Funcionalidade para criar nova conversa será implementada aqui!");
        alert.showAndWait();
    }

    private void showNewGroupDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Novo Grupo");
        alert.setHeaderText("Criar Novo Grupo");
        alert.setContentText("Funcionalidade para criar novo grupo será implementada aqui!");
        alert.showAndWait();
    }

    private void showSettingsDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Configurações");
        alert.setHeaderText("Configurações do Usuário");
        alert.setContentText("Configurações personalizadas serão implementadas aqui!");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class ChatItem {

        private final String name;
        private final String lastMessage;
        private final String time;
        private final boolean isIndividual;

        public ChatItem(String name, String lastMessage, String time, boolean isIndividual) {
            this.name = name;
            this.lastMessage = lastMessage;
            this.time = time;
            this.isIndividual = isIndividual;
        }

        public String getName() {
            return name;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public String getTime() {
            return time;
        }

        public boolean isIndividual() {
            return isIndividual;
        }
    }
}
