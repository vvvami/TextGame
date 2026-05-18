package net.vami.game.display.panel.custom;

import net.vami.game.Game;
import net.vami.game.display.panel.GameFrame;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Hoverable;
import net.vami.util.Input;
import net.vami.util.LogUtil;
import net.vami.util.TextUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    Container mainContainer;
    Font mainFont = new Font(GameFrame.FONT, Font.PLAIN, 40);
    SpringLayout mainLayout;
    JTextPane gameText;

    JTextField playerTextInputArea;
    Action enterAction;

    private static Color textColor = TextUtil.defaultTextColor;
    private static Color parentTextColor = null;

    private static final String HOVER_OBJECT_KEY = "hoverObject";


    public GamePanel(JFrame frame) {
        gameText = new JTextPane();
        gameText.setMinimumSize(new Dimension(0, 0));
        gameText.setBackground(Color.black);
        gameText.setEditable(false);
        gameText.setForeground(TextUtil.defaultTextColor);
        gameText.setCaretColor(Color.black);
        gameText.setSelectedTextColor(Color.black);
        gameText.setSelectionColor(Color.white);
        gameText.setFont(mainFont);
        gameText.setVisible(true);

        gameText.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int offset = gameText.viewToModel2D(e.getPoint());

                if (offset < 0 || offset >= gameText.getDocument().getLength()) {
                    Game.getFrame().getHoverPanel().hidePopup();
                    return;
                }

                StyledDocument doc = gameText.getStyledDocument();
                Element element = doc.getCharacterElement(offset);
                AttributeSet attributes = element.getAttributes();

                Hoverable hoverObject = (Hoverable) attributes.getAttribute(HOVER_OBJECT_KEY);

                if (hoverObject != null) {
                    Point glassPoint = SwingUtilities.convertPoint(
                            gameText,
                            e.getPoint(),
                            Game.getFrame().getGlassPane()
                    );

                    Game.getFrame().getHoverPanel().showAt(
                            glassPoint.x + 20,
                            glassPoint.y + 20,
                            hoverObject.getHoverInfo()
                    );
                } else {
                    Game.getFrame().getHoverPanel().hidePopup();
                }
            }
        });

        JScrollPane gameTextArea = new JScrollPane(gameText);
        gameTextArea.setBorder(new LineBorder(Color.white, 0));
        gameTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        gameTextArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gameTextArea.setWheelScrollingEnabled(true);


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
        gameText.setComponentZOrder(playerTextInputArea, 0);

        // Action instantiation
        enterAction = new enterAction();
        playerTextInputArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        playerTextInputArea.getActionMap().put("enterAction", enterAction);
        playerTextInputArea.setFocusable(true);

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addPropertyChangeListener("focusOwner", e -> {
                    if (playerTextInputArea.isShowing()
                            && e.getNewValue() != playerTextInputArea) {
                        SwingUtilities.invokeLater(() ->
                                playerTextInputArea.requestFocusInWindow()
                        );
                    }
                });

        // Layout
        mainLayout = new SpringLayout();


        // content pane
        mainContainer = this;
        mainContainer.setBackground(Color.black);
        mainContainer.setPreferredSize(frame.getSize());
        mainContainer.setLayout(mainLayout);

        mainContainer.add(playerTextInputArea);
        mainContainer.add(gameTextArea);

        mainLayout.putConstraint(SpringLayout.NORTH, gameTextArea, 0, SpringLayout.NORTH, mainContainer);
        mainLayout.putConstraint(SpringLayout.SOUTH, playerTextInputArea, -15, SpringLayout.SOUTH, mainContainer);
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
        Style style = gameText.addStyle(null, null);

        gameText.setCharacterAttributes(attSet, false);
        StyleConstants.setForeground(style, c);
        try {
            gameText.getDocument().insertString(document.getLength(), text, style);
        }
        catch (BadLocationException e) {
            throw new RuntimeException();
        }
    }

    private void writeHoverable(String text, Color color, Hoverable hoverObject) {
        StyledDocument document = gameText.getStyledDocument();

        Style style = gameText.addStyle(null, null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setUnderline(style, true);

        style.addAttribute(HOVER_OBJECT_KEY, hoverObject);

        try {
            document.insertString(document.getLength(), text, style);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void display(String text, Color color, Hoverable hoverObject) {
        Color defaultColor = color != null ? color : parentTextColor;
        Color currentColor = defaultColor;

        Hoverable currentHoverObject = null;

        int chunkStart = 0;
        int i = 0;

        while (i < text.length()) {

            // Color code: &7, &r, &c, etc.
            if (text.charAt(i) == '&' && i + 1 < text.length()) {
                writeChunk(text, chunkStart, i, currentColor, currentHoverObject);

                String code = text.substring(i, i + 2);

                if (code.equals("&r")) {
                    currentColor = defaultColor;
                } else {
                    Color newColor = TextUtil.colorMap.get(code);
                    if (newColor != null) {
                        currentColor = newColor;
                    }
                }

                i += 2;
                chunkStart = i;
                continue;
            }

            // Hover toggle marker
            if (text.startsWith(TextUtil.HOVER_CODE, i)) {
                writeChunk(text, chunkStart, i, currentColor, currentHoverObject);

                if (currentHoverObject == null) {
                    currentHoverObject = hoverObject;
                } else {
                    currentHoverObject = null;
                }

                i += TextUtil.HOVER_CODE.length();
                chunkStart = i;
                continue;
            }

            i++;
        }

        writeChunk(text, chunkStart, text.length(), currentColor, currentHoverObject);

        gameText.scrollRectToVisible(new Rectangle(0, gameText.getHeight(), 1, 10));
    }

    private void writeChunk(String text, int start, int end, Color color, Hoverable hoverObject) {
        if (start >= end) return;

        String chunk = text.substring(start, end);

        if (hoverObject != null) {
            writeHoverable(chunk, color, hoverObject);
        } else {
            write(chunk, color);
        }
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
            playerTextInput = playerTextInput.stripLeading();
            playerTextInputArea.setText("");
            LogUtil.Log(playerTextInput);
            Game.display("> %s%n", TextUtil.defaultTextColor, playerTextInput);

            Input.playerInput.setInput(playerTextInput);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
