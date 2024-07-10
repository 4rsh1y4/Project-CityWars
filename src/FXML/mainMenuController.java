package FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import phase1.*;
import phase1.MatchDetail;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class mainMenuController {
    private User currentuser = new User();
    private MediaPlayer mediaPlayer;



    @FXML  public void init(){
        Media media = new Media(new File("C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/3song.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        volumeSlider = new Slider(0, 1, 0.5);

        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());

        trackSelector = new ComboBox<>();
        trackSelector.getItems().addAll("Track 1", "Track 2", "Track 3");
        trackSelector.getSelectionModel().selectFirst();
        trackSelector.setOnAction(this::changeTrack);

        VBox settingsMenu = new VBox(10);
        settingsMenu.setPadding(new Insets(10));
        settingsMenu.setAlignment(Pos.CENTER);
        settingsMenu.getChildren().addAll(new Label("Volume:"), volumeSlider, new Label("Track:"), trackSelector);
        changeTrack(null);


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

    }

    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }
    public User getCurrentuser() {
        return currentuser;
    }
//StartGame
    @FXML Button startGameButton;
    @FXML AnchorPane startGameAnchor;
    @FXML Button game1,game2;
    @FXML ImageView GameAnchorClose;
//GameHistory
    @FXML Button gameHistoryButton;
    @FXML AnchorPane gameHistoryAnchor;
    @FXML ImageView HistoryAnchorClose;
    @FXML Label wherepage;
    @FXML private TableView<MatchDetailWrapper> gameHistoryTable;
    @FXML private TableColumn<MatchDetail, String> dateColumn;
    @FXML private TableColumn<MatchDetail, String> timeColumn;
    @FXML private TableColumn<MatchDetail, String> statusColumn;
    @FXML private TableColumn<MatchDetail, String> enemyNameColumn;
    @FXML private TableColumn<MatchDetail, Integer> enemyLevelColumn;
    @FXML private TableColumn<MatchDetail, Integer> xpChangeColumn;
    @FXML private TableColumn<MatchDetail, Integer> coinChangeColumn;
    @FXML private ChoiceBox<String> sortChoiceBox;
    @FXML private ChoiceBox<String> orderChoiceBox;
    @FXML private TextField pageTextField;
    private List<MatchDetail> matchDetails;
    private ObservableList<MatchDetail> displayedMatches;
    private int currentPage = 1;
    private final int pageSize = 3;
//ShopManu
    @FXML private Button shopMenuButton;
    @FXML private AnchorPane shopMenuAnchor;
    @FXML private ImageView MenuAnchorClose;
    @FXML private HBox cardContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private HBox cardContainer2;
    @FXML private ScrollPane scrollPane2;
//SettingMenu
    @FXML private Slider volumeSlider;
    @FXML private ComboBox<String> trackSelector;
    @FXML private Button settingMenuButton;
    @FXML private AnchorPane settingMenuAnchor;
    @FXML private ImageView settingMenuClose;

    //startGame
    public void startGame(){
        startGameAnchor.toFront();
        startGameAnchor.setDisable(false);
        startGameAnchor.setVisible(true);

    }
    public void closeGame(){
        startGameAnchor.setDisable(true);
        startGameAnchor.setVisible(false);
    }

    //GameHistory
    public void startGameHistory(ActionEvent event){
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
    public void closeGameHistory(MouseEvent event){
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
    public List<MatchDetail> getMatchDetailsFromSource(){
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
    @FXML public void updateDisplayedMatches() {
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, matchDetails.size());
        wherepage.setText("Page " + currentPage + " of " + ((matchDetails.size() + pageSize - 1) / pageSize));

        List<MatchDetailWrapper> currentPageItems = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageItems.add(new MatchDetailWrapper(matchDetails.get(i)));
        }

        ObservableList<MatchDetailWrapper> observableList = FXCollections.observableArrayList(currentPageItems);
        gameHistoryTable.setItems(observableList);    }

    @FXML private void handlePreviousPage() {
        wherepage.setText(String.valueOf(currentPage));

        if (currentPage > 1) {
            currentPage--;
            updateDisplayedMatches();
        } else {
            System.out.println("You are on the first page");
        }
    }
    @FXML private void handleNextPage() {
        wherepage.setText(String.valueOf(currentPage));
        if (currentPage * pageSize < matchDetails.size()) {
            currentPage++;
            updateDisplayedMatches();
        } else {
            System.out.println("You are on the last page.");
        }
    }

    @FXML private void handleSort() {
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
    @FXML private void goToPage() {
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
    @FXML private int getTotalPages() {
        return (int) Math.ceil((double) matchDetails.size() / pageSize);
    }

    //ShopMenu
    @FXML public void startShopMenu(ActionEvent event){
        shopMenuAnchor.toFront();
        shopMenuAnchor.setDisable(false);
        shopMenuAnchor.setVisible(true);
        if(currentuser!=null){
            displayUserCards(currentuser);
        }

    }
    @FXML public void closeShopMenu(MouseEvent event){
        shopMenuAnchor.toBack();
        shopMenuAnchor.setDisable(true);
        shopMenuAnchor.setVisible(false);
    }
    @FXML private void displayUserCards(User user) {
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
        System.out.println("Card clicked: " + card.getName() + "with level: "+card.getLevel() +" att/def: "+card.getCardAttackDefence()+" plaDam: "+card.getPlayerDamage());
        currentuser.setCoin(currentuser.getCoin()-card.getUpgradeCost());
        card.upgradeCard();
//        System.out.println("Card clicked: " + card.getName() + "with level: "+card.getLevel()+" att/def: "+card.getCardAttackDefence()+" plaDam: "+card.getPlayerDamage());
        currentuser.upgradeCardList();
        displayUserCards(currentuser);
    }
    public void handlenCardClick(Card card) {
        System.out.println("Card clicked: " + card.getName() + "with level: "+card.getLevel() +" att/def: "+card.getCardAttackDefence()+" plaDam: "+card.getPlayerDamage());
        currentuser.setCoin(currentuser.getCoin()-card.getUpgradeCost());
        if (!currentuser.getCards().contains(card)) {
            currentuser.getCards().add(card);
        }
        displayUserCards(currentuser);
        currentuser.upgradeCardList();
    }

    //SettingMenu
    public void startSettinMenu(){
        settingMenuAnchor.toFront();
        settingMenuAnchor.setVisible(true);
        settingMenuAnchor.setDisable(false);

    }
    public void setSound(DragEvent event){
        System.out.println(volumeSlider.valueProperty());
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());
    }
    @FXML public void changeTrack(ActionEvent event) {
        String selectedTrack = trackSelector.getValue();
        System.out.println(selectedTrack);
        String trackpath = "";

        if (selectedTrack != null) {
            switch (selectedTrack) {
                case "Track 1":
                    trackpath = "C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/1song.mp3";
                    break;
                case "Track 2":
                    trackpath = "C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/2song.mp3";
                    break;
                case "Track 3":
                    trackpath = "C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/resources/3song.mp3";
                    break;
            }

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            Media media = new Media(new File(trackpath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());
            mediaPlayer.play();
        }
    }    public void closeSettingMenu(MouseEvent event){
        settingMenuAnchor.toBack();
        settingMenuAnchor.setVisible(false);
        settingMenuAnchor.setDisable(true);
    }

}
