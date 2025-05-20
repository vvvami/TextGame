package net.vami.util;

public class Input {
    private InputReceiver receiver;
    public static Input playerInput = new Input();

    public boolean captureInput(InputReceiver inputReceiver) {
        if (receiver != null) {
            return false;
        }

        receiver = inputReceiver;
        LogUtil.Log("Input captured");
        return true;
    }

    public void releaseInput(InputReceiver inputReceiver) {
        if (receiver != inputReceiver) {
            return;
        }
        receiver = null;
        LogUtil.Log("Input released");
    }

    public void setInput(String input) {
        if (receiver != null) {
            receiver.receiveInput(input);
            LogUtil.Log("Input received");
        }
    }
}
