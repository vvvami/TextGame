package net.vami.game.display.panel;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Hoverable;
import net.vami.game.world.Position;
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
import java.util.ArrayDeque;
import java.util.IllegalFormatException;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Hoverable currentHoverObject = null;
    private int currentHoverStart = -1;
    private int currentHoverEnd = -1;
    private boolean hoverPopupVisible = false;

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

        gameText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hideHoverIfNeeded();
            }
        });

        gameText.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                StyledDocument doc = gameText.getStyledDocument();
                int documentLength = doc.getLength();

                int offset = gameText.viewToModel2D(e.getPoint());

                if (offset < 0 || offset >= documentLength) {
                    hideHoverIfNeeded();
                    return;
                }

                Element element = doc.getCharacterElement(offset);
                AttributeSet attributes = element.getAttributes();

                Hoverable hoverObject = (Hoverable) attributes.getAttribute(HOVER_OBJECT_KEY);

                if (hoverObject == null) {
                    hideHoverIfNeeded();
                    return;
                }

                int hoverStart = element.getStartOffset();
                int hoverEnd = element.getEndOffset();

                boolean sameHoverRegion =
                        hoverPopupVisible &&
                                hoverObject == currentHoverObject &&
                                hoverStart == currentHoverStart &&
                                hoverEnd == currentHoverEnd;

                if (sameHoverRegion) {
                    return;
                }

                currentHoverObject = hoverObject;
                currentHoverStart = hoverStart;
                currentHoverEnd = hoverEnd;
                hoverPopupVisible = true;

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

    private void hideHoverIfNeeded() {
        if (!hoverPopupVisible) {
            return;
        }

        hoverPopupVisible = false;
        currentHoverObject = null;
        currentHoverStart = -1;
        currentHoverEnd = -1;

        Game.getFrame().getHoverPanel().hidePopup();
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
//        StyleConstants.setUnderline(style, true);

        style.addAttribute(HOVER_OBJECT_KEY, hoverObject);

        try {
            document.insertString(document.getLength(), text, style);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFormattedText(String text, Color baseColor, Hoverable hoverObject) {
        Color currentColor = baseColor != null ? baseColor : TextUtil.defaultTextColor;

        int chunkStart = 0;
        int i = 0;

        while (i < text.length() - 1) {
            String colorCode = text.substring(i, i + 2);

            if (TextUtil.colorMap.containsKey(colorCode)) {
                writeChunk(
                        text,
                        chunkStart,
                        i,
                        currentColor,
                        hoverObject
                );

                Color mappedColor = TextUtil.colorMap.get(colorCode);

                currentColor = mappedColor != null
                        ? mappedColor
                        : TextUtil.defaultTextColor;

                i += 2;
                chunkStart = i;
            } else {
                i++;
            }
        }

        writeChunk(
                text,
                chunkStart,
                text.length(),
                currentColor,
                hoverObject
        );
    }

    private static final Pattern FORMAT_SPECIFIER = Pattern.compile(
            "%(?:\\d+\\$)?[-#+ 0,(<]*\\d*(?:\\.\\d+)?[tT]?[a-zA-Z%]"
    );

    private record TextRun(
            String text,
            Color color,
            Hoverable hoverObject,
            Sound sound,
            Position soundPosition,
            int volume
    ) {
        static TextRun text(String text, Color color, Hoverable hoverObject) {
            return new TextRun(text, color, hoverObject, null, null, 0);
        }

        static TextRun textWithSound(
                String text,
                Color color,
                Hoverable hoverObject,
                Position position,
                Sound sound,
                int volume
        ) {
            return new TextRun(text, color, hoverObject, sound, position, volume);
        }

        boolean hasSound() {
            return sound != null;
        }
    }

    private Sound pendingDisplaySound = null;
    private Position pendingDisplaySoundPosition = null;
    private int pendingDisplaySoundVolume = 0;

    private final Queue<TextRun> textQueue = new ArrayDeque<>();
    private final Timer textTimer = new Timer(Game.GAME_DELAY, e -> flushNextLine());
    private boolean ignoreTimer = false;

    public void showText(Color color, String text, Object... args) {

        Color baseColor = color != null ? color : TextUtil.defaultTextColor;

        Matcher matcher = FORMAT_SPECIFIER.matcher(text);

        int lastTextIndex = 0;
        int argIndex = 0;

        while (matcher.find()) {

            if (matcher.start() > lastTextIndex) {
                String rawChunk = text.substring(lastTextIndex, matcher.start());
                queueFormattedText(rawChunk, baseColor, null);
            }

            String specifier = matcher.group();
            char conversion = specifier.charAt(specifier.length() - 1);

            if (conversion == '%') {
                queueFormattedText("%", baseColor, null);
            } else if (conversion == 'n') {
                queueFormattedText("\n", baseColor, null);
            } else {
                if (argIndex >= args.length) {
                    queueFormattedText(specifier, baseColor, null);
                } else {
                    Object arg = args[argIndex++];

                    Hoverable hoverObject = null;
                    Object displayValue = arg;

                    if (arg instanceof Hoverable hoverable) {
                        hoverObject = hoverable;
                        displayValue = hoverable.getDisplayName();
                    }

                    String renderedText;

                    try {
                        renderedText = String.format(specifier, displayValue);
                    } catch (IllegalFormatException e) {
                        renderedText = String.valueOf(displayValue);
                    }

                    queueFormattedText(renderedText, baseColor, hoverObject);
                }
            }

            lastTextIndex = matcher.end();
        }

        if (lastTextIndex < text.length()) {
            queueFormattedText(text.substring(lastTextIndex), baseColor, null);
        }

        if (!textTimer.isRunning()) {
            textTimer.setInitialDelay(0);
            textTimer.restart();
        }

        if (ignoreTimer) {
            ignoreTimer = false;
            textTimer.setInitialDelay(0);
            textTimer.stop();
            flushNextLine();
        }
    }

    public void display(
            Position position,
            Color color,
            String text,
            Sound sound,
            int volume,
            Object... args
    ) {
        pendingDisplaySound = sound;
        pendingDisplaySoundPosition = position;
        pendingDisplaySoundVolume = volume;

        showText(color, text, args);

        // Fallback: if the text was empty or only newlines, still play the sound.
        if (pendingDisplaySound != null) {
            textQueue.add(new TextRun(
                    "",
                    color,
                    null,
                    pendingDisplaySound,
                    pendingDisplaySoundPosition,
                    pendingDisplaySoundVolume
            ));

            pendingDisplaySound = null;
            pendingDisplaySoundPosition = null;
            pendingDisplaySoundVolume = 0;
        }
    }

    private void addTextRun(String text, Color color, Hoverable hoverObject) {
        if (pendingDisplaySound != null && !text.equals("\n")) {
            textQueue.add(new TextRun(
                    text,
                    color,
                    hoverObject,
                    pendingDisplaySound,
                    pendingDisplaySoundPosition,
                    pendingDisplaySoundVolume
            ));

            pendingDisplaySound = null;
            pendingDisplaySoundPosition = null;
            pendingDisplaySoundVolume = 0;
            return;
        }

        textQueue.add(TextRun.text(text, color, hoverObject));
    }

    private void queueFormattedText(String text, Color color, Hoverable hoverObject) {
        text = text.replace("\r\n", "\n").replace("\r", "\n");

        int start = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                if (i > start) {
                    addTextRun(text.substring(start, i), color, hoverObject);
                }

                addTextRun("\n", color, null);
                start = i + 1;
            }
        }

        if (start < text.length()) {
            addTextRun(text.substring(start), color, hoverObject);
        }
    }

    private void flushNextLine() {
        if (textQueue.isEmpty()) {
            textTimer.stop();
            return;
        }

        while (!textQueue.isEmpty()) {
            TextRun run = textQueue.poll();

            if (run.hasSound()) {
                run.sound().playAudible(run.soundPosition(), run.volume());
            }

            writeFormattedText(run.text(), run.color(), run.hoverObject());

            if (run.text().equals("\n")) {
                break;
            }
        }

        gameText.scrollRectToVisible(
                new Rectangle(0, gameText.getHeight(), 1, 10)
        );

        gameText.repaint();

        if (textQueue.isEmpty()) {
            textTimer.stop();
        }
    }

    public void print(Color color, String text, Object... args) {
        showText(color, text, args);

        while (!textQueue.isEmpty()) {
            flushNextLine();
        }
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
            if (playerTextInput.isBlank() || !textQueue.isEmpty()) {
                return;
            }
            playerTextInput = playerTextInput.stripLeading();
            playerTextInputArea.setText("");
            LogUtil.log(playerTextInput);
            Display.print(TextUtil.defaultTextColor, "> %s%n", playerTextInput);

            Input.playerInput.setInput(playerTextInput);
        }

    }
}
