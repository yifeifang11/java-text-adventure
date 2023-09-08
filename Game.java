import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.*;
import java.awt.GridLayout;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Game {

    JFrame window;
    Container con;
    JPanel titleNamePanel, spacePromptPanel, mainPanel, triggerPanel1, choicePanel, triggerPanel2;
    JLabel titleNameLabel, spacePromptLabel;
    JButton choice1, choice2;
    JTextArea mainTextArea;

    Font titleFont, spaceFont, textFont;
    boolean titleOpen, gameOver, hasKnife, hasBone;

    String titleString;
    String promptString;
    String tryAgain = "Try Again...";
    String mainText, choice1Text, choice2Text;
    int titleCount = 0;
    int promptCount = 0;
    int deathTimerCount = 0;
    int deathTimerCount2 = 0;
    int displayTextCount = 0;
    int pauseBetweenSceneCount = 0;

    Action CreateOpeningScene;
    ChoiceHandler choiceHandler;

    String sceneString;

    public static void main (String[] args) {
        
        new Game();
    }

    public Game() {

        //Set up for window
        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setVisible(true);
        con = window.getContentPane();

        

        //set up for title panel
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 200);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("");
        titleNameLabel.setForeground(Color.white);
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("arcadefont.ttf")).deriveFont(30f);
        } catch (IOException|FontFormatException e) {}


        titleNameLabel.setFont(titleFont);
        titleNamePanel.add(titleNameLabel);


        //set up for the space prompt panel
        spacePromptPanel = new JPanel();
        spacePromptPanel.setBounds(100, 400, 600, 100);
        spacePromptPanel.setBackground(Color.black);
        spacePromptLabel = new JLabel("");
        spacePromptLabel.setForeground(Color.white);
        try {
            spaceFont = Font.createFont(Font.TRUETYPE_FONT, new File("arcadefont.ttf")).deriveFont(20f);
        } catch (IOException|FontFormatException e) {}

        spacePromptLabel.setFont(spaceFont);
        spacePromptPanel.add(spacePromptLabel);



        //trigger panel
        triggerPanel1 = new JPanel();
        triggerPanel1.setBounds(1,1,1,1);
        triggerPanel1.setBackground(Color.black);
        CreateOpeningScene = new CreateOpeningScene();


        triggerPanel1.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "createOpeningScene");
        triggerPanel1.getActionMap().put("createOpeningScene", CreateOpeningScene);
        


        //add everything to container

        con.add(triggerPanel1);
        con.add(titleNamePanel);
        con.add(spacePromptPanel);

        triggerPanel1.requestFocus();
        

        
        //letter by letter display with timers

        titleString = "THERE WAS NO ESCAPE";
        titleTimer.start();
        promptString = "PRESS SPACE TO START...";


        //main text font

        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("arcadefont.ttf")).deriveFont(20f);
        } catch (IOException|FontFormatException e) {}


        choiceHandler = new ChoiceHandler();

    }

    public void createOpeningScreen() {

        titleNamePanel.setVisible(false);
        spacePromptPanel.setVisible(false);

        mainPanel = new JPanel();
        mainPanel.setBounds(100, 100, 600, 200);
        mainPanel.setBackground(Color.black);
        mainPanel.setVisible(true);
        con.add(mainPanel);

        mainTextArea = new JTextArea("Main text area");
        mainTextArea.setBounds(100,100,600,200);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);
        mainTextArea.setFont(textFont);
        mainTextArea.setLineWrap(true);
        mainPanel.add(mainTextArea);

        choicePanel = new JPanel();
        choicePanel.setBounds(100,300,600,200);
        choicePanel.setBackground(Color.black);
        choicePanel.setVisible(true);
        choicePanel.setLayout(new GridLayout(4,1));

        con.add(choicePanel);

        choice1 = new JButton("Choice 1");
        choice1.setOpaque(true);
        choice1.setBackground(Color.black);
        choice1.setBorderPainted(false);
        choice1.setForeground(Color.white);
        choice1.setFont(textFont);
        //choice1.setHorizontalAlignment(SwingConstants.LEFT);
        //choice1.setHorizontalTextPosition(SwingConstants.LEFT);
        choice1.addActionListener(choiceHandler);
        choice1.setActionCommand("c1");

        choice1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                choice1.setForeground(Color.gray);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                choice1.setForeground(Color.white);
            }
        });

        choicePanel.add(choice1);

        choice2 = new JButton("Choice 2");

        choice2.setBackground(Color.black);
        choice2.setForeground(Color.white);
        choice2.setBorderPainted(false);
        choice2.setOpaque(true);
        choice2.setFont(textFont);
        //choice2.setHorizontalAlignment(SwingConstants.LEFT);
        //choice2.setHorizontalTextPosition(SwingConstants.LEFT);
        choice2.addActionListener(choiceHandler);
        choice2.setActionCommand("c2");

        choice2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                choice2.setForeground(Color.gray);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                choice2.setForeground(Color.white);
            }
        });

        choicePanel.add(choice2);

        triggerPanel2 = new JPanel();
        triggerPanel2.setBounds(1,1,1,1);
        triggerPanel2.setBackground(Color.black);

        

        con.add(triggerPanel2);


        wakingUp();

    }

    public void wakingUp() {
        gameOver = false;
        sceneString = "Waking Up";
        //mainTextArea.setText("You wake up in the middle of a dark forest. You have no \nclue how you got here.");
        mainText = "You wake up in the middle of a dark forest. You have no \nclue how you got here.";
        choice1Text = "Wait and see what happens";
        choice2Text = "Wander about";
        displayText();
    }

    public void waiting() {
        sceneString = "Waiting";
       // mainTextArea.setText("You hear footsteps \napproaching... \nYou turn around quickly but \ndon't see anything. \n\nYou feel a sharp pain in yourchest before blacking out...");
        mainText = "You hear footsteps \napproaching... \nYou turn around quickly but \ndon't see anything. \n\nYou feel a sharp pain in yourchest before blacking out...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void wander() {
        sceneString = "Wandering";
        mainText = "You decide to start walking \nalong the path. There is an \nintersection ahead and a \nnote pinned to a tree.";
        choice1Text = "Inspect the note";
        choice2Text = "Turn back";
        displayText();
    }

    public void turnAround() {
        sceneString = "Turn around";
        mainText = "You hesitate as a shiver goesdown your spine. Then, you \nturn around and see a faint \noutline in the distance. It \nquickly charges toward you... \n\nYou black out in fear...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void inspectNote() {
        sceneString = "Inspect note";
        mainText = "You tear the note off the \ntree. It reads:\n\n1437\n\nYou shove the note in your\npocket.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void fork() {
        sceneString = "Fork";
        mainText = "The intersection splits off  into a left and right path.";
        choice1Text = "Turn left";
        choice2Text = "Turn right";
        displayText();
    }

    public void goRight() {
        sceneString = "Go right";
        mainText = "You walk along the right\npath... for a very long time.\nThere seems to be nothing\nhere.";
        choice1Text = "Keep going";
        choice1Text = "Turn back";
        displayText();
    }

    public void rightContinue() {
        sceneString = "Continue right";
        mainText = "You continue walking in\nthe darkness. Suddenly,\nyou hear footsteps and\na figure appears in front \nof you.\n\nYou black out.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void rightTurnBack() {
        sceneString = "Turn back right";
        mainText = "You start walking back \nalong the path. Suddenly,\na figure appears in front \nof you. It looks like it \nwas chasing you.\n\nYou black out.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void goLeft() {
        sceneString = "Go left";
        mainText = "You walk along the \nleft path...\n\nYour footsteps are the\nonly thing you can hear.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void leftContinue() {
        sceneString = "Continue left";
        mainText = "You come across a house.\nThe wooden walls are\nhalf broken.";
        choice1Text = "Enter the house";
        choice2Text = "Keep walking";
        displayText();
    }

    public void enterHouse() {
        sceneString = "Enter house";
        mainText = "You enter the house.\nThe floorboards creek\nas you walk in.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void livingRoom() {
        sceneString = "Living room";
        mainText = "You enter the living\nroom. There is a breeze\nthat causes the curtains\nto sway.";
        choice1Text = "Inspect living room";
        choice2Text = "Move to another room";
        displayText();
    }

    public void inspectLivingRoom() {
        sceneString = "Inspect living room";
        mainText = "You find nothing...\n\nYou decide to explore\nanother room.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();


    }

    public void kitchen() {
        sceneString = "Kitchen";
        mainText = "You enter the kitchen.";
        choice1Text = "Inspect kitchen";
        choice2Text = "Move to another room";
        displayText();
    }

    public void inspectKitchen() {
        sceneString = "Inspect kitchen";
        mainText = "You find a knife.\nThere's dried blood on it.";
        choice1Text = "Take the knife";
        choice2Text = "Leave it there";
        displayText();
    }

    public void takeKnife() {
        sceneString = "Take knife";
        mainText = "You take the knife.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        hasKnife = true;
        pauseBetweenScene.start();
    }

    public void leaveKnife() {
        sceneString = "Leave knife";
        mainText = "You leave the knife there";
        choice1Text = "";
        choice2Text = "";
        displayText();
        hasKnife = false;
        pauseBetweenScene.start();
    }

    public void bathroom() {
        sceneString = "Bathroom";
        mainText = "You enter the bathroom.\nThe door suddenly slams\nbehind you.";
        choice1Text = "Try to open the door";
        choice2Text = "Inspect the bathroom";
        displayText();
    }

    public void openBathroomDoor() {
        sceneString = "Open bathroom door";
        mainText = "You try and open the\ndoor. It's locked...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void openBathroomDoor2() {
        sceneString = "Open bathroom door 2";
        mainText = "A hand suddenly breaks\nthrough the door.\nBefore you can react\nit grabs at your face.\n\nYour vision goes black...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void inspectBathroom() {
        sceneString = "Inspect bathroom";
        mainText = "You search around but\nfind nothing worth taking.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void inspectBathroom2() {
        sceneString = "Inspect bathroom 2";
        mainText = "The door suddenly shakes\nthen breaks down. You\nlook in horror as a\ngangly, deceased figure\nstares at you and charges.";
        if (hasKnife) {choice1Text = "Attack with knife"; choice2Text = "Flee";}
        else {choice1Text = "Flee"; choice2Text = "";}
        displayText();
    }

    public void flee() {
        sceneString = "Flee";
        mainText = "There is no where to flee...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void attack() {
        sceneString = "Attack";
        mainText = "You stab the creature.\nIt screams out in agony.\nYou charge through the\ndoor and flee.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void passHouse() {
        sceneString = "Pass house";
        mainText = "You decide not to enter.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void passHouse2() {
        sceneString = "Pass house 2";
        mainText = "You leave the house\nbehind. You quicken your\npace.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void graveyard() {
        sceneString = "Graveyard";
        mainText = "You walk past a few\ngravestones. You find\nyourself in a graveyard...";
        choice1Text = "Explore the graveyard";
        choice2Text = "Try and leave";
        displayText();
    }

    public void leaveGraveyard() {
        sceneString = "Leave graveyard";
        mainText = "You walk ahead to find\nan exit, but you trip\nand faceplant into\nthe floor.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void tripGraveyard() {
        sceneString = "Trip graveyard";
        mainText = "Hands suddenly reach out,\nthey dig into your skin\nand you scream in pain.\n\nYour vision turns black...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void exploreGraveyard() {
        sceneString = "Explore graveyard";
        mainText = "You walk about the\ngraveyard. Your foot kicks\nsomething. It's a skull.";
        choice1Text = "Take the skull";
        choice2Text = "Leave it there";
        displayText();
    }

    public void takeSkull() {
        sceneString = "Take skull";
        mainText = "The skull is too heavy to\ncarry.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void continueExploring() {
        sceneString = "Continue exploring";
        mainText = "You find a few bones\nscattered on the floor.";
        choice1Text = "Take one";
        choice2Text = "Leave it there";
        displayText();
    }

    public void takeBone() {
        sceneString = "Take bone";
        mainText = "You take a long bone.\nIt looks like someone's\nfemur.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        hasBone = true;
        pauseBetweenScene.start();
    }

    public void leaveBone() {
        sceneString = "Leave bone";
        mainText = "You decide to leave the\nbones alone.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        hasBone = false;
        pauseBetweenScene.start();
    }

    public void exitGraveyard() {
        sceneString = "Exit graveyard";
        mainText = "You leave the graveyard.\n\nYou continue walking...";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void encounter() {
        sceneString = "Encounter";
        mainText = "You hear footsteps\napproaching rapidly from\nbehind you. The\ncreature stares at you,\nand you feel a chill.";
        if (hasBone) {
            choice1Text = "Attack with bone";
            choice2Text = "";
        }
        else {
            choice1Text = "Attack with knife";
            choice2Text = "";
        }
        displayText();
    }

    public void attackWithBone() {
        sceneString = "Attack with bone";
        mainText = "You whack the monster\nwith the bone. It\nfalls over. You make\na break for it.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void escape() {
        sceneString = "Escape";
        mainText = "You run for your life.\nYou finally reach a dead\nend. The path is\nblocked with thick vines.";
        if (hasBone) {
            choice1Text = "Cut vines down with knife";
            choice2Text = "Prepare to fight";
        }
        else {
            choice1Text = "";
            choice2Text = "";
            gameOver = true;
        }
        displayText();
    }

    public void fightCreature() {
        sceneString = "Fight creature";
        mainText = "The creature appears, and\nyou stab at it with the\nknife. It rips the knife\nout of your hand and charges\nat you.\n\nYour vision goes black.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }

    public void cutVines() {
        sceneString = "Cut vines";
        mainText = "You slash at the vines\nwith the knife and the vines\nbreak apart.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        pauseBetweenScene.start();
    }

    public void win() {
        sceneString = "Win";
        mainText = "Light shines upon your\nface. You have escaped.";
        choice1Text = "";
        choice2Text = "";
        displayText();
    }

    public void attackWithKnife() {
        sceneString = "Attack with knife";
        mainText = "You stab at the\ncreature with the knife,\nbut it catches the knife.\nIt rips it out of your\nhand and charges at you.\n\nYour vision goes black.";
        choice1Text = "";
        choice2Text = "";
        displayText();
        gameOver = true;
    }





    public void resetGame() {
        deathTimer.start();
        
    }

    public void displayText() {
        mainTextArea.setText("");
        choice1.setText("");
        choice2.setText("");
        displayMainText.start();
    }

    public class CreateOpeningScene extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (titleOpen) {
                titleCount = 0;
                promptCount = 0;
                titleTimer2.start();
                promptTimer2.start();
            }

        }
    }


    public class ChoiceHandler implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {

            String choice = e.getActionCommand();

            switch (sceneString) {
                case "Waking Up":
                    switch (choice) {
                        case "c1":
                            waiting(); break;
                        case "c2":
                            wander(); break;
                    } break;
                case "Wandering":
                    switch (choice) {
                        case "c1":
                            inspectNote(); break;
                        case "c2":
                            turnAround(); break;
                    } break;
                case "Fork":
                    switch(choice) {
                        case "c1":
                            goLeft(); break;
                        case "c2":
                            goRight(); break;
                    } break;
                case "Go right":
                    switch(choice) {
                        case "c1":
                            rightContinue(); break;
                        case "c2":
                            rightTurnBack(); break;
                    } break;
                case "Continue left":
                    switch(choice) {
                        case "c1":
                            enterHouse(); break;
                        case "c2":
                            passHouse(); break;
                    } break;
                case "Living room":
                    switch (choice) {
                        case "c1":
                            inspectLivingRoom(); break;
                        case "c2":
                            kitchen(); break;
                    } break;
                case "Kitchen":
                    switch(choice) {
                        case "c1":
                            inspectKitchen(); break;
                        case "c2":
                            bathroom();
                    } break;
                case "Inspect kitchen":
                    switch(choice) {
                        case "c1":
                            takeKnife(); break;
                        case "c2":
                            leaveKnife(); break;
                    } break;
                case "Bathroom":
                    switch(choice) {
                        case "c1":
                            openBathroomDoor(); break;
                        case "c2":
                            inspectBathroom(); break;
                    } break;
                case "Inspect bathroom 2":
                    switch(choice) {
                        case "c1":
                            if (hasKnife) {attack(); break;} else {flee(); break;}
                        case "c2":
                            if (hasKnife) {flee(); break;}
                    } break;
                case "Graveyard":
                    switch(choice) {
                        case "c1":
                            exploreGraveyard(); break;
                        case "c2":
                            leaveGraveyard(); break;
                    } break;

                case "Explore graveyard":
                    switch(choice) {
                        case "c1":
                            takeSkull(); break;
                        case "c2":
                            continueExploring(); break;
                    } break;
                case "Continue exploring":
                    switch(choice) {
                        case "c1":
                            takeBone(); break;
                        case "c2":
                            leaveBone(); break;
                    } break;
                case "Encounter":
                    switch(choice) {
                        case "c1":
                            if (hasBone) {attackWithBone(); break;} else {attackWithKnife(); break;}
                    } break;
                case "Escape":
                    switch(choice) {
                        case "c1":
                            if (hasBone) {cutVines(); break;}
                        case "c2":
                            if (hasBone) {fightCreature(); break;}
                    } break;
            }

        }
    }
   





    Timer titleTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            titleNameLabel.setText(titleString.substring(0, titleCount+1));
            titleCount++;

            if (titleCount == titleString.length()) {
                titleOpen = true;
                titleCount = 0;
                titleTimer.stop();
                pauseTimer.start();
            }
        }
    });

    Timer pauseTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            pauseTimer.stop();
            promptTimer.start();
        }
    });

    Timer promptTimer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            spacePromptLabel.setText(promptString.substring(0, promptCount+1));
            promptCount++;

            if (promptCount == promptString.length()) {
                promptCount = 0;
                promptTimer.stop();
            }
        }
    });

    Timer titleTimer2 = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            //titleNameLabel.setText(titleString.substring(titleCount));
            titleNameLabel.setText("");
            titleTimer2.stop();
            pauseTimer2.start();
        }
    });

    Timer pauseTimer2 = new Timer(300, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            pauseTimer2.stop();
            createOpeningScreen();
        }
    });

    Timer promptTimer2 = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            //spacePromptLabel.setText(promptString.substring(promptCount));
            spacePromptLabel.setText("");
            promptTimer2.stop();
        }
    });

    Timer deathTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            choice1.setText(tryAgain.substring(0, deathTimerCount + 1));
            deathTimerCount++;

            if (deathTimerCount == tryAgain.length()) {
                deathTimerCount = 0;
                deathTimer.stop();
                deathTimer2.start();
                
            }
        }
    });

    Timer deathTimer2 = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deathTimerCount2++;

            if (deathTimerCount2 == 8) {
                mainPanel.setVisible(false);
                choicePanel.setVisible(false);
                
            }

            if (deathTimerCount2 == 10) {
                deathTimer2.stop();
                deathTimerCount2 = 0;
                titleNamePanel.setVisible(true);
                spacePromptPanel.setVisible(true);
                titleCount = 0;
                promptCount = 0;

                titleOpen = true;
                hasKnife = false;
                hasBone = false;
                titleTimer.start();
            }
        }
    });

    Timer displayMainText = new Timer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            char[] characterArray = mainText.toCharArray();
            String toAppend = "" + characterArray[displayTextCount];
            mainTextArea.append(toAppend);
            displayTextCount++;

            if (displayTextCount == mainText.length()) {
                displayTextCount = 0;
                displayMainText.stop();
                choice1.setText(choice1Text);
                choice2.setText(choice2Text);
                if (gameOver) resetGame();
            }
        }
    });

    Timer pauseBetweenScene = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            pauseBetweenSceneCount++;
            if (pauseBetweenSceneCount == 2 && sceneString.equals("Inspect note")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                fork();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Go left")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                leftContinue();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Enter house")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                livingRoom();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Inspect living room")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                kitchen();
            }

            if (pauseBetweenSceneCount == 2 && (sceneString.equals("Take knife") || sceneString.equals("Leave knife"))) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                bathroom();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Open bathroom door")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                openBathroomDoor2();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Inspect bathroom")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                inspectBathroom2();
            }


            if (pauseBetweenSceneCount == 2 && (sceneString.equals("Attack") || sceneString.equals("Pass house"))) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                passHouse2();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Pass house 2")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                graveyard();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Leave graveyard")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                tripGraveyard();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Take skull")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                continueExploring();
            }

            if (pauseBetweenSceneCount == 2 && (sceneString.equals("Take bone") || sceneString.equals("Leave bone"))) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                exitGraveyard();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Exit graveyard")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                encounter();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Attack with bone")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                escape();
            }

            if (pauseBetweenSceneCount == 2 && sceneString.equals("Cut vines")) {
                pauseBetweenScene.stop();
                pauseBetweenSceneCount = 0;
                win();
            }
        }
    });

}