/*
Name: Megan Meyers
Date:4/23/15
Project: CS182 Project 4 LinkLists --- BLACKJACK
Description: This is the main class, and includes a nested class for custom painting. This code creates a top-level container, a JFrame, and
adds several content panes to it. Most of this code was given by Prof but some of it is tweaked per my changes to the game. I gave the 
content panes colors and labels here. I also added more content panes than original. Action events have been updated for my game.
 */
package project.pkg4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project4 extends JFrame implements ActionListener {

    private static int winxpos = 0, winypos = 0;      // place window here
    private JButton exitButton, newButton, hitButton, standButton, dealButton;
    static MyPanel centerPanel;
    private static JFrame myFrame = null;
    private JPanel northPanel;
    static JPanel southPanel, westPanel;
    static JLabel dealer;
    Game game;

    ////////////              MAIN      ////////////////////////
    public static void main(String[] args) {

        Project4 tpo = new Project4();

    }

    ////////////            CONSTRUCTOR   /////////////////////
    public Project4() {
        myFrame = this; // need a static variable reference to a JFrame object//you can replace "this" with "new JFrame()"
        myFrame.setResizable(false);

        northPanel = new JPanel();
        northPanel.setBackground(Color.RED);

        westPanel = new JPanel();
        westPanel.setBackground(Color.DARK_GRAY);
        Dimension dim = new Dimension(100, 600);
        westPanel.setPreferredSize(dim);

        southPanel = new JPanel();
        southPanel.setBackground(Color.RED);

        dealer = new JLabel("Dealer stands on 17.", JLabel.LEFT);
        dealer.setHorizontalTextPosition(JLabel.LEFT);
        southPanel.add(dealer);

        dealButton = new JButton("Deal");
        northPanel.add(dealButton);
        dealButton.addActionListener(this);

        hitButton = new JButton("Hit");
        northPanel.add(hitButton);
        hitButton.addActionListener(this);

        standButton = new JButton("Stand");
        northPanel.add(standButton);
        standButton.addActionListener(this);

        newButton = new JButton("Clear Hand");
        northPanel.add(newButton);
        newButton.addActionListener(this);

        exitButton = new JButton("Exit");
        northPanel.add(exitButton);
        exitButton.addActionListener(this);

        centerPanel = new MyPanel();

        getContentPane().add("North", northPanel);
        getContentPane().add("Center", centerPanel);
        getContentPane().add("South", southPanel);
        getContentPane().add("West", westPanel);

        game = new Game();

        setSize(800, 700);
        setLocation(winxpos, winypos);

        setVisible(true);

    }

    ////////////   BUTTON CLICKS ///////////////////////////
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            dispose();
            System.exit(0);
        }
        if (e.getSource() == dealButton) {
            Game.deal();
            repaint();
        }
        if (e.getSource() == newButton) {
            Game.clear();
            repaint();
        }
        if (e.getSource() == standButton) {
            Game.stand();
            repaint();
        }
        if (e.getSource() == hitButton) {
            Game.hit();
            repaint();
        }
    }

// This routine will load an image into memory
//
    public static Image load_picture(String fname) {
        // Create a MediaTracker to inform us when the image has
        // been completely loaded.
        Image image;
        MediaTracker tracker = new MediaTracker(myFrame);

        // getImage() returns immediately.  The image is not
        // actually loaded until it is first used.  We use a
        // MediaTracker to make sure the image is loaded
        // before we try to display it.
        image = myFrame.getToolkit().getImage(fname);

        // Add the image to the MediaTracker so that we can wait
        // for it.
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        if (tracker.isErrorID(0)) {
            image = null;
        }
        return image;
    }
// --------------   end of load_picture ---------------------------
//NESTED CLASS pkg private

    class MyPanel extends JPanel { //always create a seperate class to do custom painting and then add it into your content pane

        ////////////    PAINT   ////////////////////////////////
        public void paintComponent(Graphics g) {

            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth(), getHeight());
            //////////////////////////////////////////////////////////////////////     
            int xpos = 200, ypos = 250;
            if (Game.theDeck == null) {
                return;
            }
            Card playerCurrent = Game.player.getFirstCard();
            while (playerCurrent != null) {
                Image tempimage = playerCurrent.getCardImage();
                g.drawImage(tempimage, xpos, ypos, this);
                // note: tempimage member variable must be set BEFORE paint is called
                xpos += 80;
                if (xpos > 700) {
                    xpos = 25;
                    ypos += 105;
                }
                playerCurrent = playerCurrent.getNextCard();
            } //end while

            xpos = 200;
            ypos = 400;
            g.setColor(Color.BLACK);
            g.drawString("Dealer Hand", 50, 450);
            Card current = Game.dealer.getFirstCard();
            while (current != null) {
                Image tempimage = current.getCardImage();
                g.drawImage(tempimage, xpos, ypos, this);
                // note: tempimage member variable must be set BEFORE paint is called
                xpos += 80;
                if (xpos > 700) {
                    xpos = 25;
                    ypos += 105;
                }
                current = current.getNextCard();
            } //end while

            xpos = 100;
            ypos = 40;
            g.drawString("Player Hand", 50, 300);
            Card temp = (new Card(0, Card.Suit.CLUB));
            Image[] array = new Image[Game.theDeck.getNumCards()];
            for (int i = 0; i < Game.theDeck.getNumCards() - 1; i++) {
                Image tempimage = temp.getCardImage();
                array[i] = temp.getCardImage();
                g.drawImage(tempimage, xpos, ypos, this);
                // note: tempimage member variable must be set BEFORE paint is called
                xpos += 9;
                if (xpos > 700) {
                    xpos = 25;
                    ypos += 105;
                    //      }
                    //    current = current.getNextCard();
                }//endif
            }//end for
        }//endpaint
    }//endclass Mypanel
}    // End Of class Project4

