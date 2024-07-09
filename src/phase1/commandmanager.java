package phase1;
import phase1.Captcha;
import phase1.DatabaseHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class commandmanager {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean signedIn = false ,signedIn2 = false;
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARS = "-+_!@#$%^&*.,?";
    private static double timeStart = 0;
    private static boolean incorrect = false;
    private static int incorrectCount = 0;
    private static String[] Questions = {"What is your father's name ?",
            "What is your favourite color ?"
            , "What was the name of your first pet?"};
    public static User currentuser,secondUser;
    private static final int pagesize = 4;
    private static int currentPage = 1;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public static void start() {

        while (!signedIn) {
            System.out.println("1- Signup");
            System.out.println("2- Login");
            System.out.println("3- Forgot my password");
            String inn = scanner.nextLine();
            try {
                int in = Integer.parseInt(inn);
                if (in == 1) {
                    signUp();
                } else if (in == 2) {
                    logIn();
                } else if (in == 3) {
                    forgotPass();
                } else {
                    System.out.println("Invalid");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
            }
        }


    }

    public static void signUp() {
        final String format = "user create -u <username> -p <password> <password confirmation> -email <email> -n <nickname> ";
        System.out.println("input should have this format:");
        System.out.println(format);
        String in = scanner.nextLine();
        String[] insplit = in.split("\\s+");
        int lenSplit = insplit.length;
        if (lenSplit > 2) {
            if (insplit[0].equals("user") && insplit[1].equals("create")) {
                if (lenSplit == 11) {
                    String username = insplit[3], password = insplit[5], passwordR = insplit[6], email = insplit[8], nickname = insplit[10];
                    if (!username.matches("^[a-zA-Z0-9_]*$")) {
                        System.out.println("Invalid username format");
                    } else {
                        if (DatabaseHelper.ExistUsername(username)) {
                            System.out.println("Username not available");
                        } else {
                            if (!(passwordR.equals(password))) {
                                System.out.println("Your passwords don't match!");
                            } else {
                                String reg8char = ".{8,}$";
                                String regChars = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).*$";
                                if (!password.matches(reg8char) && password.matches(regChars)) {
                                    System.out.println("Your password should be atleast 8 characters");
                                } else if (!password.matches(regChars) && password.matches(reg8char)) {
                                    System.out.println("Your password should contain lower and upper and special chars");
                                } else if (!password.matches(regChars) && !password.matches(reg8char)) {
                                    System.out.println("Your password is trash");
                                } else {
                                    if (!email.matches("^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.(com)$")) {
                                        System.out.println("Invalid email format");
                                    } else {
                                        if (!DatabaseHelper.ExistsNickname(nickname)) {
                                            System.out.print("Please choose a security question : \n" +
                                                    ". 1-What is your father's name ? \n" +
                                                    ". 2-What is your favourite color ? \n" +
                                                    ". 3-What was the name of your first pet? ");

                                            int questionNumber = scanner.nextInt();
                                            scanner.nextLine();
                                            if (!(questionNumber > 3 || questionNumber < 1)) {

                                                String questionAnswer = scanner.nextLine();
                                                boolean captachpassed = false;
                                                System.out.println("Alright now answer this captcha to finish signing up!");
                                                System.out.println();
                                                while (!captachpassed) {
                                                    Random random = new Random();
                                                    int n = random.nextInt(3) + 5;
                                                    String captcha = Captcha.generateCaptchaNumber(n);
                                                    Captcha.displayCaptcha(captcha);
                                                    String userInput = scanner.nextLine();
                                                    if (!userInput.equals(captcha)) {
                                                        System.out.println("wrong answer, Try again");
                                                        System.out.println();
                                                    } else {
                                                        captachpassed = true;
                                                    }
                                                }
                                                System.out.println("correct!  Signing up...");
                                                insertNewUserToDb(email, password, username, nickname, questionAnswer, questionNumber);
                                            } else {
                                                System.out.println("Invalid choice");
                                                start();
                                            }
                                        } else {
                                            System.out.println("Nick name is not available");
                                            start();
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else if (lenSplit == 10) {
                    if (insplit[5].equals("random")) {
                        String username = insplit[3], email = insplit[7], nickname = insplit[9];
                        if (!username.matches("^[a-zA-Z0-9_]*$")) {
                            System.out.println("Invalid username format");
                        } else {
                            if (DatabaseHelper.ExistUsername(username)) {
                                System.out.println("Username not available");
                            } else {
                                if (!email.matches("^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.(com)$")) {
                                    System.out.println("Invalid email format");
                                } else {
                                    if (!DatabaseHelper.ExistsNickname(nickname)) {
                                        System.out.print("Please choose a security question : \n" +
                                                ". 1-What is your father's name ? \n" +
                                                ". 2-What is your favourite color ? \n" +
                                                ". 3-What was the name of your first pet? ");

                                        int questionNumber = scanner.nextInt();
                                        scanner.nextLine();
                                        if (!(questionNumber > 3 || questionNumber < 1)) {
                                            String questionAnswer = scanner.nextLine();
                                            questionAnswer = questionAnswer.toLowerCase();
                                            boolean captachpassed = false;
                                            System.out.println("Alright now answer this captcha to finish signing up!");
                                            System.out.println();
                                            while (!captachpassed) {
                                                Random random = new Random();
                                                int n = random.nextInt(3) + 5;
                                                String captcha = Captcha.generateCaptchaNumber(n);
                                                Captcha.displayCaptcha(captcha);
                                                String userInput = scanner.nextLine();
                                                if (!userInput.equals(captcha)) {
                                                    System.out.println("wrong answer, Try again");
                                                    System.out.println();
                                                } else {
                                                    captachpassed = true;
                                                }
                                            }
                                            System.out.println("Correct!  Signing up...");
                                            Random random1 = new Random();
                                            String password = generateRandomPassword(random1.nextInt(3) + 8);
                                            insertNewUserToDb(email, password, username, nickname, questionAnswer, questionNumber);

                                        } else {
                                            System.out.println("Invalid choice");
                                            start();
                                        }
                                    } else {
                                        System.out.println("Nick name isn't availabe");
                                        start();
                                    }
                                }
                            }


                        }

                    } else {
                        System.out.println("Invalid");
                    }


                } else {
                    System.out.println("Invalid");

                }
            } else {
                System.out.println("Invalid");
            }
        } else {
            System.out.println("Invalid");
        }
    }
    public static void logIn() {
        if (!incorrect) {
            final String format = "user login -u <username> -p <password> ";
            System.out.println("Input like this format:");
            System.out.println(format);
            String in = scanner.nextLine();
            String[] insplit = in.split("\\s+");
            int sizesplit = insplit.length;
            if (sizesplit != 6) {
                System.out.println("Invalid");
            } else {
                String username = insplit[3], password = insplit[5];
                if (!DatabaseHelper.ExistUsername(username)) {
                    System.out.println("Username doesn't exist");
                } else {
                    if (!DatabaseHelper.checkUser(username, password)) {
                        System.out.println("Password and Username don't match!");
                        timeStart = System.nanoTime();
                        incorrectCount++;
                        incorrect = true;
                    } else {
                        System.out.println("Logged in");
                        incorrectCount = 0;
                        signedIn = true;

                        currentuser = User.getUser(username);
                        mainmenu();
                    }
                }
            }
        } else {
            double currenttime = System.nanoTime();
            double elapsedtime = (currenttime - timeStart) / 1_000_000_000;
            int seconds = (int) (5 * incorrectCount - elapsedtime);
            if (seconds > 0) {
                System.out.println("Try again in (" + seconds + ") seconds.");
            } else {
                incorrect = false;
                logIn();
            }
        }

    }
    public static void forgotPass() {
        final String format = "Forgot my password -u <username>";
        System.out.println("Input like this format:");
        System.out.println(format);
        String in = scanner.nextLine();
        String[] insplit = in.split("\\s+");
        int size = insplit.length;
        if (size != 5) {
            System.out.println("Invalid");
        } else {
            String username = insplit[4];
            if (!DatabaseHelper.ExistUsername(username)) {
                System.out.println("Username not found.");
            } else {
                User selecteduser = User.getUser(username);
                int questionint = selecteduser.getQuestionNumber();
                System.out.println(Questions[questionint]);
                String ans = scanner.nextLine();
                ans = ans.trim();
                ans = ans.toLowerCase();
                if (!ans.equals(selecteduser.getQuestionAnswer())) {
                    System.out.println("wrong Answer");
                } else {
                    //change pass
                    System.out.print("Correct!  ");
                    System.out.println("Enter your new password");
                    String password = scanner.nextLine();
                    password = password.trim();
                    //validate pass
                    String reg8char = ".{8,}$";
                    String regChars = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).*$";
                    if (!password.matches(reg8char) && password.matches(regChars)) {
                        System.out.println("Your password should be atleast 8 characters");
                    } else if (!password.matches(regChars) && password.matches(reg8char)) {
                        System.out.println("Your password should contain lower and upper and special chars");
                    } else if (!password.matches(regChars) && !password.matches(reg8char)) {
                        System.out.println("Your password is trash");
                    } else {
                        System.out.println("Enter your new password again");
                        String passwordR = scanner.nextLine();
                        passwordR = passwordR.trim();
                        if (!passwordR.equals(password)) {
                            System.out.println("Not the same password");
                        } else {
                            selecteduser.setPassword(password);
                            DatabaseHelper.changePassword(username, password);
                            System.out.println("Your password was changed!");
                        }

                    }
                }
            }
        }

    }

    public static void mainmenu() {

        System.out.println("1.Start a game");
        System.out.println("2.Show your cards");
        System.out.println("3.Match history");
        System.out.println("4.Shop");
        System.out.println("5.Profile Menu");
        System.out.println("6.log out");
        String inn = scanner.nextLine();
        try {
            int in = Integer.parseInt(inn);
            if (in == 1) {
                startGame();
            } else if (in == 2) {
                showcards();
            } else if (in == 3) {
                matchHistory();
            } else if (in == 4) {
                shopMenu();
            } else if (in == 5) {
                profileMenu();
            } else if (in == 6) {
                signedIn = false;
                currentuser = null;
                start();
            } else {
                System.out.println("Invalid");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
            mainmenu();
        }
    } //::

    public static void profileMenu() {
        System.out.println("/show information//profile change: -e<>/-n<>/-u<>/password -o<> -n<>");
        String in = scanner.nextLine();
        String[] inSplit = in.split("\\s+");
        if (in.equals("back")) {
            mainmenu();
        } else if (in.equals("log out")) {
            signedIn = false;
            currentuser = null;
            start();
        } else if (in.equals("show information")) {
            System.out.println("Your username: " + currentuser.getUsername());
            System.out.println("Your nickname: " + currentuser.getNickname());
            System.out.println("Your email: " + currentuser.getEmail());
            System.out.println("Your password: " + currentuser.getPassword());
            System.out.println("Your Question: " + Questions[currentuser.getQuestionNumber()]);
            System.out.println("Your QuestionAnswer: " + currentuser.getQuestionAnswer());
            System.out.println("Your Level: " + currentuser.getLevel());
            System.out.println("Your Xp: " + currentuser.getXp());
            System.out.println("Your Hp: " + currentuser.getHp());
            System.out.println("Your Coins: " + currentuser.getCoin());
            System.out.println("Your cards: ");
            for (Card c : currentuser.getCards()) {
                System.out.println(c.toStringCarePackage());
            }
            profileMenu();
            //profile changes
        }
        if ((inSplit[0].equals("profile") && inSplit[1].equals("change"))) {
            if (inSplit.length == 4) {
                //change username from profile
                if (inSplit[2].equals("-u")) {
                    String username = inSplit[3];
                    if (DatabaseHelper.ExistUsername(username)) {
                        System.out.println("Username not available!");
                        profileMenu();
                    } else {
                        DatabaseHelper.changeUsername(currentuser.getUsername(), username);
                        currentuser.setUsername(username);
                        System.out.println("Your username is now: " + currentuser.getUsername());
                        profileMenu();
                    }
                    //change nickname from profile
                } else if (inSplit[2].equals("-n")) {
                    String nickname = inSplit[3];
                    if (DatabaseHelper.ExistsNickname(nickname)) {
                        System.out.println("Nickname not available");
                        profileMenu();
                    } else {
                        currentuser.setNickname(nickname);
                        DatabaseHelper.changeNickname(currentuser.getUsername(), nickname);
                        System.out.println("Your nickname is now: " + currentuser.getNickname());
                        profileMenu();
                    }
                    //change email from profile
                } else if (inSplit[2].equals("-e")) {
                    String email = inSplit[3];
                    if (!email.matches("^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.(com)$")) {
                        System.out.println("Invalid email format");
                        profileMenu();
                    }
                    currentuser.setEmail(email);
                    DatabaseHelper.changeEmail(currentuser.getUsername(), email);
                    System.out.println("Your email is now: " + currentuser.getEmail());
                    profileMenu();

                }
                //change pass from profile
            } else if (inSplit.length == 7 && inSplit[2].equals("password") && inSplit[3].equals("-o") && inSplit[5].equals("-n")) {

                String opass = inSplit[4];
                String npass = inSplit[6];

                if (!DatabaseHelper.checkUser(currentuser.getUsername(), opass)) {
                    System.out.println("Current password is incorrect!");
                    profileMenu();
                } else {
                    if (opass.equals(npass)) {
                        System.out.println("Please enter a new password!");
                        profileMenu();
                    } else {
                        String reg8char = ".{8,}$";
                        String regChars = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?]).*$";
                        if (!npass.matches(reg8char) && npass.matches(regChars)) {
                            System.out.println("Your password should be atleast 8 characters");
                        } else if (!npass.matches(regChars) && npass.matches(reg8char)) {
                            System.out.println("Your password should contain lower and upper and special chars");
                        } else if (!npass.matches(regChars) && !npass.matches(reg8char)) {
                            System.out.println("Your password is trash");
                        } else {
                            System.out.println("Enter your new pass again: ");
                            String nnpass = scanner.nextLine();
                            nnpass = nnpass.trim();
                            if (!nnpass.equals(npass)) {
                                System.out.println("Your passwords should match!");
                                profileMenu();
                            } else {
                                System.out.println("complete this captcha");
                                Random random = new Random();
                                int n = random.nextInt(3) + 5;
                                String captcha = Captcha.generateCaptchaNumber(n);
                                Captcha.displayCaptcha(captcha);
                                String userInput = scanner.nextLine();
                                if (!userInput.equals(captcha)) {
                                    System.out.println("wrong answer, Start again");
                                } else {
                                    System.out.println("Correct!  Changing your password...");
                                    currentuser.setPassword(npass);
                                    DatabaseHelper.changePassword(currentuser.getUsername(), npass);
                                    profileMenu();
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Invalid");
                profileMenu();
            }
        }
    }
    public static void startGame() {
        if (currentuser.getCards().size() == 0) {
            Set<Integer> randoms = new HashSet<>();
            while (currentuser.getCards().size() < 15) {
                Random random = new Random();
                Integer ran = random.nextInt(25);
                if (!randoms.contains(ran)) {
                    Card adder = Card.cards.get(ran);
                    currentuser.getCards().add(adder);
                    randoms.add(ran);
                }
            }
            if (!DatabaseHelper.changeUserCards(currentuser.getUsername(), currentuser.setCardsToString())) {
                System.out.println("Failed to input to database");
            } else {
                System.out.println("welcome "+currentuser.getUsername()+"! You've been rewarded this peck of cards:");
                String Name = "       NAME:";
                String atd = "A/D";
                String pd = "PD";
                String du = "Dur";
                String level = "level";
                String typee = "type";
                System.out.printf("%-20s %-5s %-4s %-5s %-7s %s %n", Name, atd, pd, du, level, typee);
                System.out.println("---------------------------------------------------------------");

                for (Card card : currentuser.getCards()) {
                    String type = "";
                    if (card.getType().equals("b")) {
                        type = "spell";
                    } else if (card.getType().equals("a")) {
                        type = "damage";
                    } else {
                        type = "heal";
                    }
                    System.out.printf("%-20s %-5s %-5s %-5s %-6s %s  %n", card.getName(), card.getCardAttackDefence(), card.getPlayerDamage(), card.getDuration(), card.getLevel(), type);

                }
                System.out.println();
                startGame();
            }
    } else {
        if (!signedIn2) {
            logInSecond();
        }
            if (secondUser.getCards().size() == 0) {
                Set<Integer> randoms = new HashSet<>();
                while (secondUser.getCards().size() < 15) {
                    Random random = new Random();
                    Integer ran = random.nextInt(25);
                    if (!randoms.contains(ran)) {
                        Card adder = Card.cards.get(ran);
                        secondUser.getCards().add(adder);
                        randoms.add(ran);
                    }
                }
                if (!DatabaseHelper.changeUserCards(secondUser.getUsername(), secondUser.setCardsToString())) {
                    System.out.println("Failed to input to database");
                } else {
                    System.out.println("welcome "+secondUser.getUsername()+"! You've been rewarded a peck of cards:");
                    String Name = "       NAME:";
                    String atd = "A/D";
                    String pd = "PD";
                    String du = "Dur";
                    String level = "level";
                    String typee = "type";
                    System.out.printf("%-20s %-5s %-4s %-5s %-7s %s %n", Name, atd, pd, du, level, typee);
                    System.out.println("---------------------------------------------------------------");

                    for (Card card : currentuser.getCards()) {
                        String type = "";
                        if (card.getType().equals("b")) {
                            type = "spell";
                        } else if (card.getType().equals("a")) {
                            type = "damage";
                        } else {
                            type = "heal";
                        }
                        System.out.printf("%-20s %-5s %-5s %-5s %-6s %s  %n", card.getName(), card.getCardAttackDefence(), card.getPlayerDamage(), card.getDuration(), card.getLevel(), type);

                    }
                    System.out.println();
                    startGame();
                }
            }else {
                System.out.println("Select the mode you want to play:");
                System.out.println("1. Two person mode:");
                System.out.println("2. Betting mode");

                String inn = scanner.nextLine();
                try {
                    int in = Integer.parseInt(inn);
                    if (in == 1) {
                        Multiplay();
                    } else if (in == 2) {
                        Bett();
                    } else if (in == 12) {
                        startGame();
                    } else {
                        System.out.println("Invalid");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, please enter a number.");
                }
            }
        }
    }
    public static void showcards() {
        System.out.println("Here are your cards:");
        System.out.println();
        String Name = "       NAME:";
        String atd = "A/D";
        String pd = "PD";
        String du = "Dur";
        String level = "level";
        String typee = "type";
        System.out.printf("%-20s %-5s %-4s %-5s %-7s %s %n", Name, atd, pd, du, level, typee);
        System.out.println("---------------------------------------------------------------");

        for (Card card : currentuser.getCards()) {
            String type = "";
            if (card.getType().equals("b")) {
                type = "spell";
            } else if (card.getType().equals("a")) {
                type = "damage";
            } else {
                type = "heal";
            }
            System.out.printf("%-20s %-5s %-5s %-5s %-6s %s  %n", card.getName(), card.getCardAttackDefence(), card.getPlayerDamage(), card.getDuration(), card.getLevel(), type);

        }
        System.out.println();
        mainmenu();
    }
    public static void matchHistory() {
        String GamesHistory = currentuser.getGameHistory();
        String[] History = GamesHistory.split(";");

        List<MatchDetail> matchDetails = new ArrayList<>();
        for (String game : History) {
            String[] dets = game.split(",");

            if (dets[0].length() < 6 || dets[1].length() < 4) {
                System.out.println("db problem!");
            } else {
                String date = "20" + dets[0].substring(4, 6) + "-" + dets[0].substring(2, 4) + "-" + dets[0].substring(0, 2);
                String time = dets[1].substring(0, 2) + ":" + dets[1].substring(2, 4);
                LocalDateTime dateTime = LocalDateTime.parse(date + " " + time, dateTimeFormatter);

                matchDetails.add(new MatchDetail(dateTime, dets[2], dets[3], Integer.parseInt(dets[4]), Integer.parseInt(dets[5]), Integer.parseInt(dets[6])));
            }
        }
        matchDetails.sort(Comparator.comparing(MatchDetail::getDateTime));
        displayPage(currentPage, matchDetails);
        System.out.println();
        while (true) {
            System.out.println("\nCommands: sort, next, prev, page <number>, back");
            String command = scanner.next();
            command = command.toLowerCase();
            if (command.equals("sort")) {
                System.out.println("Sort by: 1. Enemy Name   2. Enemy Level   3.Data&Time");
                int sortOption = scanner.nextInt();
                System.out.println("Order: 1.Ascending    2.Descending");
                int sortOrder = scanner.nextInt();
                Comparator<MatchDetail> comparator;
                switch (sortOption) {
                    case 1:
                        comparator = Comparator.comparing(MatchDetail::getEnemyName);
                        break;
                    case 2:
                        comparator = Comparator.comparingInt(MatchDetail::getEnemyLevel);
                        break;
                    case 3:
                        comparator = Comparator.comparing(MatchDetail::getDateTime);
                        break;
                    default:
                        System.out.println("Invalid option. Sorting by Enemy Name.");
                        comparator = Comparator.comparing(MatchDetail::getEnemyName);
                        break;
                }
                if (sortOrder == 2) {
                    comparator = comparator.reversed();
                }
                matchDetails.sort(comparator);

                currentPage = 1;
                displayPage(currentPage,matchDetails);

            } else if (command.equals("next")) {
                if (currentPage * pagesize < matchDetails.size()) {
                    currentPage++;
                    displayPage(currentPage, matchDetails);
                } else {
                    System.out.println("You are on the last page.");
                }
            } else if (command.equals("previous")) {
                if (currentPage > 1) {
                    currentPage--;
                    displayPage(currentPage, matchDetails);
                } else {
                    System.out.println("You are on the first page");
                }
            } else if (command.startsWith("page")) {
                int pageNum = scanner.nextInt();
                if (pageNum > 0 && (pageNum - 1) * pagesize < matchDetails.size()) {
                    currentPage = pageNum;
                    displayPage(currentPage, matchDetails);
                } else {
                    System.out.println("Invalid page number.");
                }
            } else if (command.equals("back")) {
                currentPage = 1;
                break;
            } else {
                System.out.println("Invalid");
            }
        }
        scanner.nextLine();
        mainmenu();
    }
    public static void logInSecond(){
        if (!incorrect) {
            final String format = "user login -u <username> -p <password> ";
            System.out.println("Player 2 needs to log in");
            System.out.println(format);
            String in = scanner.nextLine();
            String[] insplit = in.split("\\s+");
            int sizesplit = insplit.length;
            if (sizesplit != 6) {
                if(!insplit[0].equals("back"))
                    System.out.println("Invalid");
                else{
                    mainmenu();
                }
            } else {
                String username = insplit[3], password = insplit[5];
                if (!DatabaseHelper.ExistUsername(username)) {
                    System.out.println("Username doesn't exist");
                } else {
                    if (!DatabaseHelper.checkUser(username, password)) {
                        System.out.println("Password and Username don't match!");
                        timeStart = System.nanoTime();
                        incorrectCount++;
                        incorrect = true;
                    }if(username.equals(currentuser.getUsername())){
                        System.out.println("Can't use an account twice\n");
                        logInSecond();
                    }else {
                        System.out.println("Logged in");
                        incorrectCount = 0;
                        signedIn2 = true;
                        secondUser = User.getUser(username);
                    }
                }
            }
        } else {
            double currenttime = System.nanoTime();
            double elapsedtime = (currenttime - timeStart) / 1_000_000_000;
            int seconds = (int) (5 * incorrectCount - elapsedtime);
            if (seconds > 0) {
                System.out.println("Try again in (" + seconds + ") seconds.");
            } else {
                incorrect = false;
                logInSecond();
            }
        }
    }
    public static void shopMenu(){
        System.out.println("You'r at shop:");
        while(true){
            System.out.println("Choose an option:");
            System.out.println("1. Show all cards not owned");
            System.out.println("2. Show upgradeable cards");
            System.out.println("3. back");
            System.out.println("Your coins: "+currentuser.getCoin());
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    selectAndUnlockCards();
                    break;
                case 2:
                    selectAndUpgradeCard();
                    break;
                case 3:
                    mainmenu();
                    break;
                default:
                    System.out.println("Invalid");
            }
        }


    }
    //::

    private static List<Card> showUpgradeableCards() {
        List<Card> upgradeableCards = new ArrayList<>();
        for(Card c : Card.cards){
            if(currentuser.getLevel()>= c.getUpgradeLevel()){
                upgradeableCards.add(c);
            }
        }
        int i = 1;
        System.out.printf("%-20s %-15s %-10s %-10s %-10s %-10s %-10s%n", "Name", "Att/Def", "Duration", "PlaDam", "UpgLvl", "UpgCost", "Type");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Card c : upgradeableCards) {
            String type = "";
            if(c.getType().equals("a")){
                type = "damage";
            }else if(c.getType().equals("c")){
                type = "healing";
            }else{
                type = "spells";
            }
            System.out.printf("%-5d %-20s %-15s %-10s %-10s %-10s %-10s %-10s%n",
                    i++ , c.getName(), c.getCardAttackDefence(), c.getDuration(), c.getPlayerDamage(),
                    c.getUpgradeLevel(), c.getUpgradeCost(), type);
        }
        return upgradeableCards;
    }

    private static void selectAndUpgradeCard(){
        List<Card> upgradeableCards = showUpgradeableCards();
        System.out.println("\nEnter the number of the card you want to upgrade:");
        int cardNumber = scanner.nextInt();
        Card selectedCard = upgradeableCards.get(cardNumber - 1);
        System.out.printf("You selected %s. Upgrade cost: %d coins. Confirm upgrade? (yes/no): ", selectedCard.getName(), selectedCard.getUpgradeCost());
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("yes")) {
            if (currentuser.getCoin() >= selectedCard.getUpgradeCost()) {
                currentuser.setCoin(currentuser.getCoin() - selectedCard.getUpgradeCost());
                String code = selectedCard.getCode();
                selectedCard.upgradeCard();

                int index = -1;
                for(int i=0;i<currentuser.getCards().size();i++){
                    if(currentuser.getCards().get(i).getType().equals(selectedCard.getType())){
                        index = 1;
                        break;
                    }
                }
                if(index!=-1){
                    currentuser.getCards().set(index,selectedCard);
                }

                String newstring = currentuser.setCardsToString();
                System.out.println(newstring);

                DatabaseHelper.changeUserCards(currentuser.getUsername(),newstring);
                DatabaseHelper.changeCoin(currentuser.getUsername(),currentuser.getCoin());
                System.out.println("Card upgraded successfully!");
            } else {
                System.out.println("You do not have enough coins to upgrade this card.");
            }
        } else {
            System.out.println("Upgrade cancelled.");
        }

    }


    private static List<Card> showCardsNotOwned() {
        List<Card> notOwnedCards = new ArrayList<>();
        for(Card c : Card.cards){
            if(!currentuser.getCards().contains(c)){
                notOwnedCards.add(c);
            }
        }
        int index = 1;
        System.out.printf("%-20s %-15s %-10s %-10s %-10s %-10s %-10s%n", "Name", "Attack/Defense", "Duration", "PlaDam", "UpgLvl", "UpgCost", "Type");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Card c : notOwnedCards) {
            System.out.printf("%-5d %-20s %-15s %-10s %-10s %-10s %-10s %-10s%n",
                    index++,c.getName(), c.getCardAttackDefence(), c.getDuration(), c.getPlayerDamage(),
                    c.getUpgradeLevel(), c.getUpgradeCost(), c.getType());
        }
        return notOwnedCards;
    }

    private static void selectAndUnlockCards() {
        List<Card> upgradeableCards = showCardsNotOwned();
        System.out.println("\nEnter the number of the card you want to upgrade:");
        int cardNumber = scanner.nextInt();
        Card selectedCard = upgradeableCards.get(cardNumber - 1);
        System.out.printf("You selected %s. Unlock cost: %d coins. Confirm unlock? (yes/no): ", selectedCard.getName(), selectedCard.getUpgradeCost());
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("yes")) {
            if (currentuser.getCoin() >= selectedCard.getUpgradeCost()) {
                currentuser.setCoin(currentuser.getCoin() - selectedCard.getUpgradeCost());
                String code = selectedCard.getCode();
                currentuser.addCard(selectedCard);
                String newstring = currentuser.setCardsToString();
                System.out.println(newstring);

                DatabaseHelper.changeUserCards(currentuser.getUsername(), newstring);
                DatabaseHelper.changeCoin(currentuser.getUsername(), currentuser.getCoin());
                System.out.println("Card added successfully!");
            } else {
                System.out.println("You do not have enough coins to unlock this card.");
            }
        } else {
            System.out.println("Unlock cancelled.");
        }
    }
    public static void Multiplay() {
            System.out.println(currentuser.getUsername()+" is now playing with " +secondUser.getUsername());
            Game game = new Game(currentuser,secondUser);
            game.playGame();

    }

    public static void Bett() {
        System.out.println("Not Ready");
        startGame();
    }

    //helpers
    public static void insertNewUserToDb(String email, String password, String username, String nickname, String questionAnswer, int questionNumber) {
        DatabaseHelper.insertUser(email, password, username, nickname, questionAnswer, questionNumber, 1, 100, 0, 250, "z1", "");
        DatabaseHelper.loadUsersFromDatabase();
    }
    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));
        for (int i = 3; i < length; i++) {
            char randomChar = LOWERCASE.charAt(random.nextInt(LOWERCASE.length()));
            password.append(randomChar);
        }
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(length);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomIndex));
            password.setCharAt(randomIndex, temp);
        }
        return password.toString();
    }
    public static String createGameHistory(String date, String hour, String state, String opname, String oplevel, String xpgain, String coingain) {
        String retur = "";
        retur = date + "," + hour + "," + state + "," + opname + "," + oplevel + "," + xpgain + "," + coingain + "-";
        return retur;
    }
    private static void displayPage(int page, List<MatchDetail> matchDetails) {
        int start = (page - 1) * pagesize;
        int end = Math.min(start + pagesize, matchDetails.size());
        System.out.println("Here are your matches:");
        System.out.println();
        System.out.printf("%-10s %-7s %-6s %-10s %-10s %-7s %-7s%n", "Date", "Time", "Status", "Enemy", "EnemyLvl", "+/-XP", "+/-Coin");
        System.out.println("-----------------------------------------------------------------");

        for (int i = start; i < end; i++) {
            MatchDetail match = matchDetails.get(i);
            System.out.printf("%-10s %-7s %-6s %-10s %-10d %-7d %-7d%n",
                    match.getDateTime().toLocalDate(), match.getDateTime().toLocalTime(), match.getStatus(), match.getEnemyName(),
                    match.getEnemyLevel(), match.getXpChange(), match.getCoinChange());
        }
        System.out.println("Page " + currentPage + " of " + ((matchDetails.size() + pagesize - 1) / pagesize));
    }
}