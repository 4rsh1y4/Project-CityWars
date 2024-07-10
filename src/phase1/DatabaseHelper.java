package phase1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class DatabaseHelper {
    private static final String DATABASE_URL = "jdbc:sqlite:C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/database/mydb.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static boolean ExistUsername(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertUser(String email, String password, String username, String nickname, String questionAnswer, int questionNumber,int level,int hp,int xp,int coin,String Card,String history) {
        String query = "INSERT INTO users (email, password, username, nickname, questionAnswer,questionNumber,level,hp,xp,coin,Card,GameHistory) VALUES (?, ?, ?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, nickname);
            pstmt.setString(5,questionAnswer);
            pstmt.setInt(6,questionNumber);
            pstmt.setInt(7,level);
            pstmt.setInt(8,hp);
            pstmt.setInt(9,xp);
            pstmt.setInt(10,coin);
            pstmt.setString(11,Card);
            pstmt.setString(12,history);
            int rowsaffected = pstmt.executeUpdate();
            if (rowsaffected>0) {
                System.out.println("User inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//not usefull check from User.userList
    public static boolean checkQuestionAnswer(String username, String inputAnswer) {
        String query = "SELECT questionAnswer FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedAnswer = rs.getString("questionAnswer");
                return storedAnswer.equals(inputAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changePassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void loadUsersFromDatabase() {
        String query = "SELECT username, nickname, password, email, questionNumber, questionAnswer, level, xp, hp, coin, Card, GameHistory FROM users";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String username = rs.getString("username");
                String nickname = rs.getString("nickname");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int questionNumber = rs.getInt("questionNumber");
                String questionAnswer = rs.getString("questionAnswer");
                int level = rs.getInt("level");
                int xp = rs.getInt("xp");
                int hp = rs.getInt("hp");
                int coin = rs.getInt("coin");
                String cardString = rs.getString("Card");
                String GameHistory = rs.getString("GameHistory");

                new User(username, nickname, password, email, questionNumber, questionAnswer, level, xp, hp, coin, cardString, GameHistory);
            }
            System.out.println("Users loaded successfully from the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean changeUsername(String oldUsername, String newUsername) {
        if(!newUsername.equals("")) {
            String query = "UPDATE users SET username = ? WHERE username = ?";
            try (Connection conn = DatabaseHelper.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newUsername);
                pstmt.setString(2, oldUsername);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean changeNickname(String username, String newNickname) {
        String query = "UPDATE users SET nickname = ? WHERE username = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newNickname);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean ExistsNickname(String nickname) {
        String query = "SELECT COUNT(*) FROM users WHERE nickname = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nickname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean changeEmail(String username, String newEmail) {
        String query = "UPDATE users SET email = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean appendGameHistory(String username, String newHistory) {
        String query = "UPDATE users SET gameHistory = gameHistory || ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newHistory);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> getGameHistory(String username) {
        String query = "SELECT gameHistory FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String gameHistory = rs.getString("gameHistory");
                if (gameHistory != null && !gameHistory.isEmpty()) {
                    return Arrays.asList(gameHistory.split("-"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void loadCardsFromDatabase() {
        String query = "SELECT code,name,AttDef,PlaDam,duration,upgradeLevel,upgradeCost,character,type  FROM cards";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                int cardDefence = rs.getInt("AttDef");
                int playerDamage = rs.getInt("PlaDam");
                int duration = rs.getInt("duration");
                int upgradeLevel = rs.getInt("upgradeLevel");
                int upgradeCost = rs.getInt("upgradeCost");
                int character = rs.getInt("character");
                String type = rs.getString("type");
                new Card(code,name,cardDefence,playerDamage,duration,upgradeLevel,upgradeCost,character,type);
            }
            System.out.println("Cards loaded successfully from the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean changeUserCards(String username, String newCardString) {
        String query = "UPDATE users SET Card = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newCardString);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changeCoin(String username, int coin) {
        String query = "UPDATE users SET coin = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, coin);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void insertCard(String name, int cardDefense, int playerDamage, int duration, int upgradeLevel, int upgradeCost, int character, String type) {
        String query = "INSERT INTO cards (name, AttDef, PlaDam, duration, upgradeLevel, upgradeCost, character, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, cardDefense);
            pstmt.setInt(3, playerDamage);
            pstmt.setInt(4, duration);
            pstmt.setInt(5, upgradeLevel);
            pstmt.setInt(6, upgradeCost);
            pstmt.setInt(7, character);
            pstmt.setString(8, type);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Card inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean existCardName(String name) {
        String query = "SELECT COUNT(*) FROM cards WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deleteCard(String name) {
        String query = "DELETE FROM cards WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void updateCard(String name, int cardDefense, int playerDamage, int duration,
                                  int upgradeLevel, int upgradeCost, int character, String type) {
        String query = "UPDATE cards SET AttDef = ?, PlaDam = ?, duration = ?, " +
                "upgradeLevel = ?, upgradeCost = ?, character = ?, type = ? WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cardDefense);
            pstmt.setInt(2, playerDamage);
            pstmt.setInt(3, duration);
            pstmt.setInt(4, upgradeLevel);
            pstmt.setInt(5, upgradeCost);
            pstmt.setInt(6, character);
            pstmt.setString(7, type);
            pstmt.setString(8, name);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Card updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
