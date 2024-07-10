package FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import phase1.MatchDetail;
import phase1.MatchDetailWrapper;
import phase1.User;
import phase1.MatchDetail;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class mainMenuController {
    private User currentuser = new User();

    @FXML  public void init(){
        System.out.println(currentuser.getUsername());
        startGameAnchor.setDisable(true);
        startGameAnchor.setVisible(false);
        gameHistoryAnchor.toBack();
        gameHistoryAnchor.setVisible(false);
        gameHistoryAnchor.setDisable(true);
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
    @FXML Button shopMenuButton;
    @FXML AnchorPane shopMenuAnchor;
    @FXML ImageView MenuAnchorClose;

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
    }
    @FXML public void closeShopMenu(ActionEvent event){
        shopMenuAnchor.toBack();
        shopMenuAnchor.setDisable(true);
        shopMenuAnchor.setVisible(false);
    }
}
