package net.vami.util;

public class Input {
    private InputReceiver receiver;
    public static Input playerInput = new Input();

    public boolean captureInput(InputReceiver inputReceiver) {
        if (receiver != null) {
            return false;
        }

        receiver = inputReceiver;
        return true;
    }

    public void releaseInput(InputReceiver inputReceiver) {
        if (receiver != inputReceiver) {
            return;
        }
        receiver = null;
    }

    public void setInput(String input) {
        if (receiver != null) {
            receiver.receiveInput(input);
        }
    }
}
