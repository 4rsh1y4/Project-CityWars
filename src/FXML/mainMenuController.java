package FXML;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase1.*;
import phase1.MatchDetail;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;


public class mainMenuController {

    private User currentuser = new User();
    private User seconduser;
    private MediaPlayer mediaPlayer;
    private String captch ;
    private boolean captchadisplayed = false;

    private int failedAttempts = 0;
    private Timeline countdownTimeline;
    private boolean isBanned = false;
    private int countdownSeconds;


    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private AnchorPane mainAnchor;
    @FXML private Label report;
    @FXML private HBox cardContainerStarter;
    @FXML private ScrollPane scrollPaneStart;

    public void setSecondUser(User user){
        this.seconduser = user;

    }

    @FXML
    public void init() {
        Media media = new Media(new File("C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/3song.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        updateProfile();
        mainAnchor.setStyle("-fx-background-color: #ffe595");

        if(currentuser.getCards().size()==0){
            starterPackAnchor.toFront();
            starterPackAnchor.setDisable(false);
            starterPackAnchor.setVisible(true);
            Set<Integer> randoms = new HashSet<>();
            while (currentuser.getCards().size() < 15) {
                Random random = new Random();
                Integer ran = random.nextInt(21);
                if (!randoms.contains(ran)) {
                    Card adder = Card.cards.get(ran);
                    currentuser.getCards().add(adder);
                    randoms.add(ran);
                }
            }
            if (!DatabaseHelper.changeUserCards(currentuser.getUsername(), currentuser.setCardsToString())) {
                System.out.println("Failed to input to database");
            }
            cardContainerStarter.getChildren().clear();
            for (Card card : currentuser.getCards()) {
                StackPane cardPane = new StackPane();
                ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(card.url)));
                cardImageView.setFitHeight(153);
                cardImageView.setFitWidth(100);
                cardImageView.setStyle("-fx-background-radius: 20");

                Label CostLabel = new Label(String.valueOf(card.getUpgradeCost()));
                CostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                CostLabel.setTextFill(Color.BLACK);
                CostLabel.setStyle("-fx-background-color: gold; -fx-alignment: center; -fx-background-radius: 20;");
                CostLabel.setPrefSize(50, 10);
                StackPane.setAlignment(CostLabel, Pos.BOTTOM_CENTER);
                StackPane.setMargin(CostLabel, new javafx.geometry.Insets(0, 0, 0, 0));
                cardPane.getChildren().addAll(cardImageView, CostLabel);

                    if (card.getType().equals("a")) {
                        Label attackDefenseLabel = new Label(String.valueOf(card.getCardAttackDefence()));
                        attackDefenseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        attackDefenseLabel.setTextFill(Color.RED);
                        attackDefenseLabel.setStyle("-fx-background-color: yellow; -fx-alignment: center; -fx-background-radius: 20;");
                        attackDefenseLabel.setPrefSize(30, 30);
                        StackPane.setAlignment(attackDefenseLabel, Pos.TOP_LEFT);
                        StackPane.setMargin(attackDefenseLabel, new javafx.geometry.Insets(0, 0, 0, 0));

                        Label playerDamageLabel = new Label(String.valueOf(card.getPlayerDamage()));
                        playerDamageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        playerDamageLabel.setTextFill(Color.RED);
                        playerDamageLabel.setStyle("-fx-background-color: green; -fx-alignment: center; -fx-background-radius: 20;");
                        playerDamageLabel.setPrefSize(30, 30);
                        StackPane.setAlignment(playerDamageLabel, Pos.TOP_RIGHT);
                        StackPane.setMargin(playerDamageLabel, new javafx.geometry.Insets(0, 0, 0, 0));

                        cardPane.getChildren().addAll(attackDefenseLabel, playerDamageLabel);
                    }
                    cardContainerStarter.getChildren().add(cardPane);

            }





        }starterPackAnchor.setDisable(true);
        starterPackAnchor.setVisible(false);
        starterPackAnchor.toBack();

        startGameAnchor.toBack();
        startGameAnchor.setDisable(true);
        startGameAnchor.setVisible(false);
        gameHistoryAnchor.toBack();
        gameHistoryAnchor.setVisible(false);
        gameHistoryAnchor.setDisable(true);
        shopMenuAnchor.toBack();
        shopMenuAnchor.setVisible(false);
        shopMenuAnchor.setDisable(true);
        settingMenuAnchor.toBack();
        settingMenuAnchor.setVisible(false);
        settingMenuAnchor.setDisable(true);
        profileMenuAnchor.toBack();
        profileMenuAnchor.setDisable(true);
        profileMenuAnchor.setVisible(false);
        secondLoginAnchor.toBack();
        secondLoginAnchor.setDisable(true);
        secondLoginAnchor.setVisible(false);

    }


    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }

    public User getCurrentuser() {
        return currentuser;
    }


    @FXML private Label xpLabel,coinLabel,hpLabel,levelLabel;

    //StartGame
    @FXML
    Button startGameButton;
    @FXML
    AnchorPane startGameAnchor;
    @FXML
    Button game1, game2;
    @FXML
    ImageView GameAnchorClose;
    //GameHistory
    @FXML
    Button gameHistoryButton;
    @FXML
    AnchorPane gameHistoryAnchor;
    @FXML
    ImageView HistoryAnchorClose;
    @FXML
    Label wherepage;
    @FXML
    private TableView<MatchDetailWrapper> gameHistoryTable;
    @FXML
    private TableColumn<MatchDetail, String> dateColumn;
    @FXML
    private TableColumn<MatchDetail, String> timeColumn;
    @FXML
    private TableColumn<MatchDetail, String> statusColumn;
    @FXML
    private TableColumn<MatchDetail, String> enemyNameColumn;
    @FXML
    private TableColumn<MatchDetail, Integer> enemyLevelColumn;
    @FXML
    private TableColumn<MatchDetail, Integer> xpChangeColumn;
    @FXML
    private TableColumn<MatchDetail, Integer> coinChangeColumn;
    @FXML
    private ChoiceBox<String> sortChoiceBox;
    @FXML
    private ChoiceBox<String> orderChoiceBox;
    @FXML
    private TextField pageTextField;
    private List<MatchDetail> matchDetails;
    private ObservableList<MatchDetail> displayedMatches;
    private int currentPage = 1;
    private final int pageSize = 3;
    //ShopManu
    @FXML
    private Button shopMenuButton;
    @FXML
    private AnchorPane shopMenuAnchor;
    @FXML
    private ImageView MenuAnchorClose;
    @FXML
    private HBox cardContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox cardContainer2;
    @FXML
    private ScrollPane scrollPane2;
    //SettingMenu
    @FXML
    private Button settingMenuButton;
    @FXML
    private AnchorPane settingMenuAnchor;
    @FXML
    private ImageView settingMenuClose;
    @FXML
    public Slider volumeSlider;
    @FXML
    public SplitMenuButton musicSelect;
    //logout
    @FXML
    private Button logOutButton;
    //profileMenu
    @FXML
    private AnchorPane profileMenuAnchor;
    @FXML
    private ImageView profileMenuClose;
    @FXML
    private AnchorPane promptAnchor;
    @FXML
    private ImageView promptClose;
    @FXML
    private TextField changeUsername;
    @FXML
    private TextField changeEmail;
    @FXML
    private TextField changeNickname;
    @FXML
    private Label promptLabel,captcha;
    @FXML
    private PasswordField passfield1;
    @FXML
    private PasswordField passfield2;
    @FXML private TextField captchaText;

    @FXML private AnchorPane secondLoginAnchor;
    @FXML private Button login;
    @FXML private TextField textField;
    @FXML private PasswordField passwordField;
    @FXML private Label prompt;

    @FXML private AnchorPane starterPackAnchor;
    @FXML private ImageView promptClose1;

    public void closestarter(){
        starterPackAnchor.toBack();
        starterPackAnchor.setDisable(true);
        starterPackAnchor.setVisible(false);
    }
    //startGame
    public void startGame(ActionEvent event) throws IOException {
        if(seconduser==null){
            secondLoginAnchor.toFront();
            secondLoginAnchor.setDisable(false);
            secondLoginAnchor.setVisible(true);
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/characterSelection.fxml"));
            root = loader.load();
            characterSelectionController csc = loader.getController();
            csc.setUser1(currentuser);
            csc.setUser2(seconduser);
            csc.setMediaPlayer(mediaPlayer);
            stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML private Slider RRGB,GRGB,BRGB;

    public void updateProfile(){
        xpLabel.setText(" Xp:  "+ currentuser.getXp());
        hpLabel.setText(" Hp:  "+ currentuser.getHp());
        levelLabel.setText(" level:  "+ currentuser.getLevel());
        coinLabel.setText(" Coin:  "+ currentuser.getCoin());
    }
    public void Login(){
        if (isBanned) {
            return;
        }
        String username = textField.getText();
        String password = passwordField.getText();
        if(!DatabaseHelper.checkUser(username,password)){
            failedAttempts++;
            login.setStyle("-fx-background-color: red");
            prompt.setText("Incorrect username or password.");
            passwordField.setText("");
            passwordField.setPromptText("BANNED");
            textField.setText("");
            textField.setPromptText("BANNED");
            isBanned = true;
            int banDuration = failedAttempts * 5;
            startCountdown(banDuration);
        }else{
            if(!DatabaseHelper.ExistUsername(username)){
                prompt.setText("Username doesn't Exist!");
                textField.setText("");
                passwordField.setText("");
            }else if(username.equals(currentuser.getUsername())){
                prompt.setText("Can't use a user twice!");
                textField.setText("");
                passwordField.setText("");
            }else {
                seconduser = User.getUser(username);
                failedAttempts = 0;
                closeLogin();
            }
        }
    }
    @FXML public void setTheme() {
        int R = (int) RRGB.getValue();
        int G = (int) GRGB.getValue();
        int B = (int) BRGB.getValue();
        String RR = Integer.toHexString(R);
        if(RR.length()==1) RR="0"+RR;
        String GG = Integer.toHexString(G);
        if(GG.length()==1) GG="0"+GG;
        String BB = Integer.toHexString(B);
        if(BB.length()==1) BB="0"+BB;
        String color = "#" +RR+GG+BB;
        report.setText("RGB: " + color);
        mainAnchor.setStyle("-fx-background-color: "+color);
    }
    public void closeLogin(){
        secondLoginAnchor.toBack();
        secondLoginAnchor.setDisable(true);
        secondLoginAnchor.setVisible(false);
    }
    private void startCountdown(int seconds) {
        countdownSeconds = seconds;
        countdownTimeline = new Timeline();
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);

        countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            prompt.setText("Please wait " + (countdownSeconds-1) + " seconds before trying again.");
            countdownSeconds--;

            if (countdownSeconds <= 0) {
                countdownTimeline.stop();
                isBanned = false;
                prompt.setText("");
                login.setStyle("-fx-background-color:  #76ff03;");
                textField.setPromptText("Username:");
                passwordField.setPromptText("Password:");
            }
        }));

        countdownTimeline.playFromStart();

    }

    public void closeGame() {
        startGameAnchor.setDisable(true);
        startGameAnchor.setVisible(false);
    }


    //GameHistory
    public void startGameHistory(ActionEvent event) {
        gameHistoryAnchor.toFront();
        gameHistoryAnchor.setVisible(true);
        gameHistoryAnchor.setDisable(false);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        enemyNameColumn.setCellValueFactory(new PropertyValueFactory<>("enemyName"));
        enemyLevelColumn.setCellValueFactory(new PropertyValueFactory<>("enemyLevel"));
        xpChangeColumn.setCellValueFactory(new PropertyValueFactory<>("xpChange"));
        coinChangeColumn.setCellValueFactory(new PropertyValueFactory<>("coinChange"));
        loadMatchDetails();
        updateDisplayedMatches();
    }

    public void closeGameHistory(MouseEvent event) {
        gameHistoryAnchor.toBack();
        gameHistoryAnchor.setVisible(false);
        gameHistoryAnchor.setDisable(true);
    }

    public void loadMatchDetails() {
        gameHistoryTable.getItems().clear();
        List<MatchDetail> matchDetailsList = getMatchDetailsFromSource();
        for (MatchDetail matchDetail : matchDetailsList) {
            gameHistoryTable.getItems().add(new MatchDetailWrapper(matchDetail));
        }
    }

    public List<MatchDetail> getMatchDetailsFromSource() {
        String gamesHistory = currentuser.getGameHistory();
        String[] history = gamesHistory.split(";");
        List<MatchDetail> matchDetailss = new ArrayList<>();

        for (String game : history) {
            String[] dets = game.split(",");
            if (dets[0].length() < 6 || dets[1].length() < 4) {
                System.out.println("db problem!");
            } else {
                String date = "20" + dets[0].substring(4, 6) + "-" + dets[0].substring(2, 4) + "-" + dets[0].substring(0, 2);
                String time = dets[1].substring(0, 2) + ":" + dets[1].substring(2, 4);
                LocalDateTime dateTime = LocalDateTime.parse(date + "T" + time);

                matchDetailss.add(new MatchDetail(dateTime, dets[2], dets[3], Integer.parseInt(dets[4]), Integer.parseInt(dets[5]), Integer.parseInt(dets[6])));
            }
        }
        matchDetailss.sort(Comparator.comparing(MatchDetail::getDateTime));
        matchDetails = matchDetailss;
        return matchDetailss;

    }

    @FXML
    public void updateDisplayedMatches() {
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, matchDetails.size());
        wherepage.setText("Page " + currentPage + " of " + ((matchDetails.size() + pageSize - 1) / pageSize));

        List<MatchDetailWrapper> currentPageItems = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageItems.add(new MatchDetailWrapper(matchDetails.get(i)));
        }

        ObservableList<MatchDetailWrapper> observableList = FXCollections.observableArrayList(currentPageItems);
        gameHistoryTable.setItems(observableList);
    }

    @FXML
    private void handlePreviousPage() {

        if (currentPage > 1) {
            wherepage.setText(String.valueOf(currentPage));
            pageTextField.setText(String.valueOf(currentPage));
            currentPage--;
            updateDisplayedMatches();
        } else {
            System.out.println("You are on the first page");
        }
    }

    @FXML
    private void handleNextPage() {
        if (currentPage * pageSize < matchDetails.size()) {
            wherepage.setText(String.valueOf(currentPage));
            pageTextField.setText(String.valueOf(currentPage));
            currentPage++;
            updateDisplayedMatches();
        } else {
            System.out.println("You are on the last page.");
        }
    }

    @FXML
    private void handleSort() {
        String sortOption = sortChoiceBox.getValue();
        String sortOrder = orderChoiceBox.getValue();

        Comparator<MatchDetail> comparator;
        switch (sortOption) {
            case "Enemy Name":
                comparator = Comparator.comparing(MatchDetail::getEnemyName);
                break;
            case "Enemy Level":
                comparator = Comparator.comparingInt(MatchDetail::getEnemyLevel);
                break;
            case "Date Time":
                comparator = Comparator.comparing(MatchDetail::getDateTime);
                break;
            default:
                comparator = Comparator.comparing(MatchDetail::getEnemyName);
                break;
        }

        if ("Descending".equals(sortOrder)) {
            comparator = comparator.reversed();
        }

        matchDetails.sort(comparator);
        currentPage = 1;
        pageTextField.setText("");
        pageTextField.setPromptText("go to page:");
        updateDisplayedMatches();
    }

    @FXML
    private void goToPage() {
        try {
            int pageNumber = Integer.parseInt(pageTextField.getText());
            if (pageNumber >= 1 && pageNumber <= getTotalPages()) {
                currentPage = pageNumber;
                updateDisplayedMatches();
            } else {
                System.out.println("Invalid page number");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid page number");
        }
    }

    @FXML
    private int getTotalPages() {
        return (int) Math.ceil((double) matchDetails.size() / pageSize);
    }

    //ShopMenu
    @FXML
    public void startShopMenu(ActionEvent event) {
        shopMenuAnchor.toFront();
        shopMenuAnchor.setDisable(false);
        shopMenuAnchor.setVisible(true);
        if (currentuser != null) {
            displayUserCards(currentuser);
        }

    }

    @FXML
    public void closeShopMenu(MouseEvent event) {
        shopMenuAnchor.toBack();
        shopMenuAnchor.setDisable(true);
        shopMenuAnchor.setVisible(false);
    }

    @FXML
    private void displayUserCards(User user) {
        cardContainer.getChildren().clear();
        cardContainer2.getChildren().clear();

        for (Card card : Card.cards) {
            StackPane cardPane = new StackPane();
            ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(card.url)));
            cardImageView.setFitHeight(153);
            cardImageView.setFitWidth(100);
            cardImageView.setStyle("-fx-background-radius: 20");

            Label CostLabel = new Label(String.valueOf(card.getUpgradeCost()));
            CostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            CostLabel.setTextFill(Color.BLACK);
            CostLabel.setStyle("-fx-background-color: gold; -fx-alignment: center; -fx-background-radius: 20;");
            CostLabel.setPrefSize(50, 10);
            StackPane.setAlignment(CostLabel, Pos.BOTTOM_CENTER);
            StackPane.setMargin(CostLabel, new javafx.geometry.Insets(0, 0, 0, 0));

            cardPane.getChildren().addAll(cardImageView, CostLabel);

            if (user.getCards().contains(card)) {
                if (card.getType().equals("a")) {
                    Label attackDefenseLabel = new Label(String.valueOf(card.getCardAttackDefence()));
                    attackDefenseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    attackDefenseLabel.setTextFill(Color.RED);
                    attackDefenseLabel.setStyle("-fx-background-color: yellow; -fx-alignment: center; -fx-background-radius: 20;");
                    attackDefenseLabel.setPrefSize(30, 30);
                    StackPane.setAlignment(attackDefenseLabel, Pos.TOP_LEFT);
                    StackPane.setMargin(attackDefenseLabel, new javafx.geometry.Insets(0, 0, 0, 0));

                    Label playerDamageLabel = new Label(String.valueOf(card.getPlayerDamage()));
                    playerDamageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    playerDamageLabel.setTextFill(Color.RED);
                    playerDamageLabel.setStyle("-fx-background-color: green; -fx-alignment: center; -fx-background-radius: 20;");
                    playerDamageLabel.setPrefSize(30, 30);
                    StackPane.setAlignment(playerDamageLabel, Pos.TOP_RIGHT);
                    StackPane.setMargin(playerDamageLabel, new javafx.geometry.Insets(0, 0, 0, 0));

                    cardPane.getChildren().addAll(attackDefenseLabel, playerDamageLabel);
                }

                cardImageView.setOnMouseClicked(event -> handleUnCardClick(card));

                if (card.getUpgradeLevel() > user.getLevel() || card.getUpgradeCost() > user.getCoin()) {
                    cardPane.setDisable(true);
                    cardImageView.setOpacity(0.5);
                }

                cardContainer.getChildren().add(cardPane);
            } else {
                if (card.getType().equals("a")) {
                    Label attackDefenseLabel = new Label(String.valueOf(card.getCardAttackDefence()));
                    attackDefenseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    attackDefenseLabel.setTextFill(Color.RED);
                    attackDefenseLabel.setStyle("-fx-background-color: yellow; -fx-alignment: center; -fx-background-radius: 20;");
                    attackDefenseLabel.setPrefSize(30, 30);
                    StackPane.setAlignment(attackDefenseLabel, Pos.TOP_LEFT);
                    StackPane.setMargin(attackDefenseLabel, new javafx.geometry.Insets(0, 0, 0, 0));

                    Label playerDamageLabel = new Label(String.valueOf(card.getPlayerDamage()));
                    playerDamageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    playerDamageLabel.setTextFill(Color.RED);
                    playerDamageLabel.setStyle("-fx-background-color: green; -fx-alignment: center; -fx-background-radius: 20;");
                    playerDamageLabel.setPrefSize(30, 30);
                    StackPane.setAlignment(playerDamageLabel, Pos.TOP_RIGHT);
                    StackPane.setMargin(playerDamageLabel, new javafx.geometry.Insets(0, 0, 0, 0));

                    cardPane.getChildren().addAll(attackDefenseLabel, playerDamageLabel);
                }

                cardImageView.setOnMouseClicked(event -> handlenCardClick(card));
                cardContainer2.getChildren().add(cardPane);
            }
        }
    }

    public void handleUnCardClick(Card card) {
        System.out.println("Card clicked: " + card.getName() + "with level: " + card.getLevel() + " att/def: " + card.getCardAttackDefence() + " plaDam: " + card.getPlayerDamage());
        currentuser.setCoin(currentuser.getCoin() - card.getUpgradeCost());
        card.upgradeCard();
//        System.out.println("Card clicked: " + card.getName() + "with level: "+card.getLevel()+" att/def: "+card.getCardAttackDefence()+" plaDam: "+card.getPlayerDamage());
        currentuser.upgradeCardList();
        displayUserCards(currentuser);
    }

    public void handlenCardClick(Card card) {
        System.out.println("Card clicked: " + card.getName() + "with level: " + card.getLevel() + " att/def: " + card.getCardAttackDefence() + " plaDam: " + card.getPlayerDamage());
        currentuser.setCoin(currentuser.getCoin() - card.getUpgradeCost());
        if (!currentuser.getCards().contains(card)) {
            currentuser.getCards().add(card);
        }
        displayUserCards(currentuser);
        currentuser.upgradeCardList();
    }


    //SettingMenu
    public void startSettinMenu() {
        settingMenuAnchor.toFront();
        settingMenuAnchor.setVisible(true);
        settingMenuAnchor.setDisable(false);

    }

    public void setSound() {
        double volume = volumeSlider.getValue() / (80+0.2*volumeSlider.getValue());
//        System.out.println(volume);
        mediaPlayer.setVolume(volume);
    }

    public void changeMusic(ActionEvent event) {
        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String selectedTrack = selectedMenuItem.getText();
        musicSelect.setText(selectedTrack);
        String trackpath = "";

        if (selectedTrack != null) {
            switch (selectedTrack) {
                case "Music 1":
//                    System.out.println(1);
                    trackpath = "C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/1song.mp3";
                    break;
                case "Music 2":
//                    System.out.println(2);
                    trackpath = "C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/2song.mp3";
                    break;
                case "Music 3":
//                    System.out.println(3);
                    trackpath = "C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/3song.mp3";
                    break;
            }

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            Media media = new Media(new File(trackpath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volumeSlider.getValue());
            mediaPlayer.play();
        }
    }

    public void closeSettingMenu(MouseEvent event) {
        settingMenuAnchor.toBack();
        settingMenuAnchor.setVisible(false);
        settingMenuAnchor.setDisable(true);
    }


    //logout
    public void logOut(ActionEvent event) throws IOException {
        currentuser = null;
        mediaPlayer.stop();

        root = FXMLLoader.load(getClass().getResource("/FXML/logIn.fxml"));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //profile Menu

    public void startProfileMenu() {
        promptAnchor.toBack();
        profileMenuAnchor.toFront();
        profileMenuAnchor.setDisable(false);
        profileMenuAnchor.setVisible(true);
        promptAnchor.setVisible(false);
        promptAnchor.setDisable(true);
        changeEmail.setText(currentuser.getEmail());
        changeNickname.setText(currentuser.getNickname());
        changeUsername.setText(currentuser.getUsername());
    }

    public void closeProfileMenu() {
        profileMenuAnchor.toBack();
        profileMenuAnchor.setDisable(true);
        profileMenuAnchor.setVisible(false);
    }

    public void closePromptAnchor() {
        promptLabel.setStyle("");
        startProfileMenu();
    }

    public void startPromptAnchor1() {
        promptAnchor.toFront();
        promptAnchor.setDisable(false);
        promptAnchor.setVisible(true);
        String username = changeUsername.getText();
        if (!username.matches("^[a-zA-Z0-9_]*$") || username.equals("")) {
            promptLabel.setText("Username can have only " + "\n" + "letters and number and underscores!");
            promptLabel.setStyle("-fx-background-color: red");

        } else if (DatabaseHelper.ExistUsername(username)) {
            promptLabel.setText(username + " is already taken!");
            promptLabel.setStyle("-fx-background-color: red");
        } else {
            promptLabel.setText("Your username changed" + "\n" + "to" + username);
            DatabaseHelper.changeUsername(currentuser.getUsername(), username);
            currentuser.setUsername(username);
        }
    }

    public void startPromptAnchor2() {
        promptAnchor.toFront();
        promptAnchor.setDisable(false);
        promptAnchor.setVisible(true);
        String email = changeEmail.getText();
        if (!email.matches("^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.(com)$") || email.equals("")) {
            promptLabel.setText("Invalid email format!");
            promptLabel.setStyle("-fx-background-color: red");
        } else {
            promptLabel.setText("Your email changed to" + "\n" + email);
            DatabaseHelper.changeEmail(currentuser.getUsername(), email);
            currentuser.setEmail(email);
        }

    }

    public void startPromptAnchor3() {
        promptAnchor.toFront();
        promptAnchor.setDisable(false);
        promptAnchor.setVisible(true);
        String nickname = changeNickname.getText();
        if (DatabaseHelper.ExistsNickname(nickname) || nickname.equals("")) {
            promptLabel.setStyle("-fx-background-color: red");
            promptLabel.setText(nickname + " is already taken!");
        } else {
            promptLabel.setText("Your nickname change to" + "\n" + nickname);
            DatabaseHelper.changeNickname(currentuser.getUsername(), nickname);
            currentuser.setNickname(nickname);
        }
    }

    public void changePass() {
        String pass1 = passfield1.getText();
        String pass2 = passfield2.getText();
        promptAnchor.toFront();
        promptAnchor.setDisable(false);
        promptAnchor.setVisible(true);
        if (!captchadisplayed) {
            if (!(pass1.equals("") || pass2.equals(""))) {
                if (!pass1.equals(pass2)) {
                    promptLabel.setText("Your password doesn't match!");
                    promptLabel.setStyle("-fx-background-color: red");
                } else {
                    String reg8char = ".{8,}$";
                    String regChars = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).*$";
                    if (!pass1.matches(reg8char) && pass1.matches(regChars)) {
                        promptLabel.setText("Your password should be min 8 chars");
                        promptLabel.setStyle("-fx-background-color: red");
                    } else if (!pass1.matches(regChars) && pass1.matches(reg8char)) {
                        promptLabel.setText("Your password should contain lower"+"\n"+" and upper and special chars");
                        promptLabel.setStyle("-fx-background-color: red");
                    } else if (!pass1.matches(regChars) && !pass1.matches(reg8char)) {
                        promptLabel.setText("Your password is trash");
                        promptLabel.setStyle("-fx-background-color: red");
                    } else {
                        Random random = new Random();
                        int n = random.nextInt(3) + 5;
                        String cap = Captcha.generateCaptchaNumber(n);
                        this.captch = cap;
                        captcha.setText(Captcha.getCaptchaDisplay(cap));
                        captchadisplayed = true;
                        closePromptAnchor();
                    }
                }
            }
        }else{
            captcha.toFront();
            String in = captchaText.getText();
            if (in.equals(captch)) {
                promptLabel.setText("Your password was changed!");
                promptLabel.setStyle("");
                captcha.toBack();
                captcha.setText("");
                captchaText.setText("");
                captchadisplayed=false;
                passfield1.setText("");
                passfield2.setText("");
                DatabaseHelper.changePassword(currentuser.getUsername(),pass1);
            } else {
                promptLabel.setText("Captcha failed!");
                promptLabel.setStyle("-fx-background-color: red");
                Random random = new Random();
                int n = random.nextInt(3) + 5;
                String cap = Captcha.generateCaptchaNumber(n);
                this.captch = cap;
                captcha.setText(Captcha.getCaptchaDisplay(cap));
            }

        }
    }
}
