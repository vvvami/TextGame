package net.vami.game.display.panels.custom;

import net.vami.game.Game;
import net.vami.game.interactables.ai.PlayerHandler;
import net.vami.util.Input;
import net.vami.util.LogUtil;
import net.vami.util.TextUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class GamePanel extends JPanel {
    Container mainContainer;
    Font mainFont = new Font("VCR OSD Mono", Font.PLAIN, 40);
    SpringLayout mainLayout;
    JTextPane gameText;
    JTextField playerTextInputArea;
    Action enterAction;
    private static Color textColor = TextUtil.defaultTextColor;
    private static Color parentTextColor = null;


    public GamePanel(JFrame frame) {

        gameText = new JTextPane();
        gameText.setMinimumSize(new Dimension(0, 0));
        gameText.setBackground(Color.black);
        gameText.setEditable(false);
        gameText.setForeground(TextUtil.defaultTextColor);
        gameText.setCaretColor(Color.black);
        gameText.setSelectedTextColor(Color.black);
        gameText.setSelectionColor(Color.white);
//        gameTextArea.setRows(0);
//        gameTextArea.setColumns(1);
        gameText.setFont(mainFont);
        gameText.setVisible(true);
//        gameText.setEditorKit(new HTMLEditorKit());

        JScrollPane gameTextArea = new JScrollPane(gameText);
        gameTextArea.setBorder(new LineBorder(Color.white, 0));
        gameTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        gameTextArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gameTextArea.setWheelScrollingEnabled(false);


        // Player text input
        playerTextInputArea = new JTextField();
        playerTextInputArea.setFont(mainFont);
        playerTextInputArea.setBackground(Color.black);
        playerTextInputArea.setBorder(new LineBorder(Color.white, 0));
        playerTextInputArea.setForeground(Color.lightGray);
        playerTextInputArea.setSelectedTextColor(Color.black);
        playerTextInputArea.setCaretColor(Color.white);
        playerTextInputArea.setSelectionColor(Color.white);
        playerTextInputArea.setEditable(false);

        // Action instantiation
        enterAction = new enterAction();
        playerTextInputArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        playerTextInputArea.getActionMap().put("enterAction", enterAction);

        // Layout
        mainLayout = new SpringLayout();

        // Container / Content Pane
        mainContainer = frame.getContentPane();
        mainContainer.setBackground(Color.black);
        mainContainer.setPreferredSize(frame.getSize());
        mainContainer.add(playerTextInputArea);
        mainContainer.add(gameTextArea);
        mainContainer.setLayout(mainLayout);

        mainLayout.putConstraint(SpringLayout.NORTH, gameTextArea, 0, SpringLayout.NORTH, mainContainer);
        mainLayout.putConstraint(SpringLayout.SOUTH, playerTextInputArea, -10, SpringLayout.SOUTH, mainContainer);
        mainLayout.putConstraint(SpringLayout.SOUTH, gameTextArea, 10, SpringLayout.NORTH, playerTextInputArea);


        mainLayout.putConstraint(SpringLayout.EAST, gameTextArea, -10, SpringLayout.EAST, mainContainer);
        mainLayout.putConstraint(SpringLayout.WEST, gameTextArea, 10, SpringLayout.WEST, mainContainer);
        mainLayout.putConstraint(SpringLayout.EAST, playerTextInputArea, -10, SpringLayout.EAST, mainContainer);
        mainLayout.putConstraint(SpringLayout.WEST, playerTextInputArea, 10, SpringLayout.WEST, mainContainer);
    }

    private void write(String text, Color c)
    {
        StyledDocument document = gameText.getStyledDocument();
        AttributeSet attSet = new SimpleAttributeSet();
        Style style = gameText.addStyle("", null);

        gameText.setCharacterAttributes(attSet, false);
        StyleConstants.setForeground(style, c);
        try {
            gameText.getDocument().insertString(document.getLength(), text, style);
        }
        catch (BadLocationException e) {
            throw new RuntimeException();
        }
    }

    public void display(String text, Color color) {
        boolean colorChanged = false;
        String firstHalf = "";
        String secondHalf = "";

        if (text.contains(TextUtil.code)) {
            String code = text.substring(text.indexOf(TextUtil.code), text.indexOf(TextUtil.code) + 2);
            textColor = TextUtil.colorMap.get(code);
            colorChanged = true;
            int codeIndex = text.indexOf(code);
            text = text.replaceFirst(code, "");
            firstHalf = text.substring(0, codeIndex);
            secondHalf = text.substring(codeIndex);
        }

        if (textColor == null) {
            textColor = parentTextColor;
        }

        if (colorChanged) {
            write(firstHalf, color);
            display(secondHalf, textColor);
        } else {
            write(text, parentTextColor);
        }
        gameText.scrollRectToVisible(new Rectangle(0, gameText.getHeight(), 1, 10));
    }

    public static void setParentTextColor(Color color) {
        parentTextColor = color;
    }

    public JTextField getPlayerInput() {
        return playerTextInputArea;
    }

    private class enterAction extends AbstractAction {

        // When the enter key is pressed, this method is called
        @Override
        public void actionPerformed(ActionEvent e) {
            String playerTextInput = playerTextInputArea.getText();
            if (playerTextInput.isBlank()) {
                return;
            }
            playerTextInputArea.setText("");
            LogUtil.Log(playerTextInput);
            Game.display("> %s%n", TextUtil.defaultTextColor, playerTextInput);

            Input.playerInput.setInput(playerTextInput);
        }

    }
}
