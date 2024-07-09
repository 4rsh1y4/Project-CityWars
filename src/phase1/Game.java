package phase1;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Game {
    private Player player1;
    int betCoin;
    private boolean mod = false;
    private Player player2;
    private User loser;
    private User winner;
    private boolean endGame=false;
    private boolean currentPlayer;
    Random random = new Random();
    int firstTurn;
    private int currentPlayerIndex;

    public Game(User user1, User user2) {
        this.player1 = new Player(user1);
        this.player2 = new Player(user2);
        this.currentPlayerIndex = 0;
    }

    public void startGame() {
        Random random = new Random();
        firstTurn = random.nextInt(2);
        currentPlayer= random.nextBoolean();
        for(int i = 0 ; i < 5 ; i++)
        {
            player1.addToHand(player1.cardChooser());
            player2.addToHand(player2.cardChooser());
        }
        if (currentPlayer) {
            System.out.println("Player " + player1.getUser().getNickname() + " starts the game.");
        } else {
            System.out.println("Player " + player2.getUser().getNickname() + " starts the game.");
        }
    }

    private void nextTurn(Player first, Player second) {
        Pattern selectPattern = Pattern.compile("-Select card number (\\d+) player (\\d+)");

        Pattern placePattern = Pattern.compile("-Placing card number (\\d+) in block (\\d+)");
        Scanner gameSc = new Scanner(System.in);
        Player playingPlayer;
        for(int i = 0; i < 8 ; i++) {


            if(i%2==0)
            {
                playingPlayer=first;
            }
            else {
                playingPlayer=second;
            }
            String command = gameSc.nextLine();
            Matcher selectMatcher = selectPattern.matcher(command);
            Matcher placeMatcher = placePattern.matcher(command);
            if (selectMatcher.matches()) {
                int cardNumber = Integer.parseInt(selectMatcher.group(1));
                int playerNumber = Integer.parseInt(selectMatcher.group(2));
                if (playerNumber == 2) {
                    player2.getHand().get(cardNumber - 1).toString();
                }
                if (playerNumber == 1) {
                    player1.getHand().get(cardNumber - 1).toString();
                }
                i--;
                continue;
            }
            else if (placeMatcher.matches()) {
                int cardNumber = Integer.parseInt(placeMatcher.group(1));
                int blockIndex = Integer.parseInt(placeMatcher.group(2));
                if (playingPlayer.putCard(playingPlayer.getHand().get(cardNumber - 1), blockIndex-1))
                {
                    playingPlayer.hand.remove(cardNumber - 1);
                    for(int j = blockIndex-1;j<playingPlayer.getHand().get(cardNumber - 1).getDuration()+blockIndex-1;j++)
                    {
                        if(player1.getBoard()[j]!=null) {
                            if (player2.getBoard()[j] != null) {
                                if (player2.getBoard()[j].getCardAttackDefence() > player1.getBoard()[j].getCardAttackDefence()) {
                                    player1.ruined[j]=true;
                                } else if (player2.getBoard()[j].getCardAttackDefence() < player1.getBoard()[j].getCardAttackDefence()) {
                                    player2.ruined[j]=true;
                                }
                                else
                                {
                                    player2.ruined[j]=true;
                                    player1.ruined[j]=true;
                                }
                            }
                        }
                    }

                }
                else
                {
                    i--;
                    continue;
                }
            }
            else {
                System.out.println("Invalid command");
                i--;
                continue;
            }

            if(i%2==0)
                first.round--;
            else {
                second.round--;
                first.addToHand(first.cardChooser());
                second.addToHand(first.cardChooser());
            }
            displayGame();
        }
    }
    private void timeLine()
    {
        for (int i = 0 ; i < 21 ; i++)
        {
            if(player1.getBoard()[i]!=null)
            {
                if(player2.getBoard()[i]!=null)
                {
                    if(player2.getBoard()[i].getCardAttackDefence()>player1.getBoard()[i].getCardAttackDefence())
                    {
                        player1.setHp(player1.getHp()-player2.getBoard()[i].getPlayerDamage());
                    }
                    else if(player2.getBoard()[i].getCardAttackDefence()<player1.getBoard()[i].getCardAttackDefence())
                    {
                        player2.setHp(player2.getHp()-player1.getBoard()[i].getPlayerDamage());
                    }
                }
                else
                {
                    player2.setHp(player2.getHp()-player1.getBoard()[i].getPlayerDamage());
                }

            }
            else if(player2.getBoard()[i]!=null)
            {
                player1.setHp(player1.getHp()-player2.getBoard()[i].getPlayerDamage());
            }
            if(player1.getHp()<=0)
            {
                player1.setHp(0);
                announceWinner(player2,player1);
                player1.getUser().checkUpgrade();
                player2.getUser().checkUpgrade();
            }
            if(player2.getHp()<=0)
            {
                player2.setHp(0);
                announceWinner(player1,player2);
                player1.getUser().checkUpgrade();
                player2.getUser().checkUpgrade();
            }
        }

    }
    public void playGame() {
        displayGame();
        startGame();
        while (!endGame) {
            if(currentPlayer)
                nextTurn(player1,player2);
            else
                nextTurn(player2,player1);
            timeLine();
            if(!endGame)
            {
                resetTurn();
            }
        }
    }
    private void resetTurn()
    {
        player1.round=4;
        player2.round=4;
        player1.hand.clear();
        player2.hand.clear();
        player1.firstBoard();
        player2.firstBoard();
        for(int i = 0 ; i < 5 ; i++)
        {
            player1.addToHand(player1.cardChooser());
            player2.addToHand(player2.cardChooser());
        }
    }
    private boolean isGameOver(Player loser) {
        return false;
    }
    private int calculateWinnerXP(Player winner, Player loser) {

        int scoreFactor = 50;
        int damageFactor = 30;
        return winner.getHp()*20 + loser.getUser().getHp()*50;
    }

    private int calculateWinnerCoins(Player winner, Player loser) {
        int scoreFactor = 20;
        int damageFactor = 15;
        return winner.getHp()*20;
    }

    public User getLoser() {
        return loser;
    }
    public User getWinner() {
        return winner;
    }
    private void announceWinner(Player winner, Player loser) {
        int winnerXP = calculateWinnerXP(winner, loser);
        int loserXP = 20;
        int winnerCoins = calculateWinnerCoins(winner, loser);
        this.winner=winner.getUser();
        this.loser=loser.getUser();
        winner.getUser().setXp(winner.getUser().getXp()+winnerXP);
        winner.getUser().setCoin(winner.getUser().getCoin()+winnerCoins);
        loser.getUser().setXp(loser.getUser().getXp()+loserXP);

        System.out.println(winner.getUser().getNickname() + " wins the game!");
        System.out.println(winner.getUser().getNickname() + " earned " + winnerXP + " XP and " + winnerCoins + " coins.");
        System.out.println(loser.getUser().getNickname() + " earned " + loserXP + " XP.");
        if(!mod)
        {
            this.winner.setCoin(this.winner.getCoin()+betCoin);
        }
        endGame=true;
    }

    public void processGame()
    {
        chooseMod();
        player2Login();
        charachterChoose();
        playGame();
    }
    private void player2Login()
    {

    }
    private void chooseMod()
    {
        Scanner modScanner  = new Scanner(System.in);
        while(true) {
            System.out.println("Please Choose the game mod:");
            System.out.println("1.PvP");
            System.out.println("2.Bet");
            String a = modScanner.nextLine();
            if (Integer.parseInt(a) == 1) {
                mod = true;
                break;
            } else if (Integer.parseInt(a) == 2) {
                mod = false;
                System.out.println("Enter the amount to bet:");
                int betAmount = Integer.parseInt(modScanner.nextLine());
                if (player1.getUser().getCoin() < betAmount || player2.getUser().getCoin() < betAmount) {
                    System.out.println("One of the players doesn't have enough coins to bet.");
                } else {
                    player1.getUser().setCoin(player1.getUser().getCoin() - betAmount);
                    player2.getUser().setCoin(player2.getUser().getCoin() - betAmount);
                    betCoin = betAmount * 2;
                    break;
                }
            }
        }
    }
    private void charachterChoose()
    {
        Scanner charachterScanner  = new Scanner(System.in);
        while(true) {
            System.out.println(player1.getUser().getNickname()+", Please Choose your character:");
            System.out.println("1.Feminist");
            System.out.println("2.Fascist");
            System.out.println("3.Communist");
            System.out.println("4.Nigger");
            String a = charachterScanner.nextLine();
            if(Integer.parseInt(a)>=1 && Integer.parseInt(a)<=4)
            {
                player1.setCharacter(new Charachter());
                player1.getCharacter().id=Integer.parseInt(a);
                break;
            }
        }
        while(true) {
            System.out.println(player2.getUser().getNickname()+", Please Choose your character:");
            System.out.println("1.Feminist");
            System.out.println("2.Fascist");

            System.out.println("3.Communist");
            System.out.println("4.Nigger");
            String a = charachterScanner.nextLine();
            if(Integer.parseInt(a)>=1 && Integer.parseInt(a)<=4)
            {
                player2.setCharacter(new Charachter());
                player2.getCharacter().id=Integer.parseInt(a);
                break;
            }

        }
    }
    public void displayGame() {
        System.out.println("Player 1 Board:");
        System.out.println(player1.boardToString());

        System.out.println("Player 2 Board:");
        System.out.println(player2.boardToString());

        System.out.println("Player 1 Hand:");
        for (Card card : player1.getHand()) {
            System.out.println(card.toString());
        }

        System.out.println("Player 2 Hand:");
        for (Card card : player2.getHand()) {
            System.out.println(card.toString());
        }

        System.out.println("Player 1 HP: " + player1.getHp());
        System.out.println("Player 1 Damage: " + player1.getDamage());
        System.out.println("Player 1 Rounds Left: " + player1.round);
        System.out.println("Player 1 Character: " + player1.getCharacter().toString());
        System.out.println("Player 2 HP: " + player2.getHp());
        System.out.println("Player 2 Damage: " + player2.getDamage());
        System.out.println("Player 2 Rounds Left: " + player2.round);
        System.out.println("Player 2 Character: " + player2.getCharacter().toString());
    }
}
