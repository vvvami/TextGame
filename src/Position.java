public class Position {
    private int xPos;
    private int yPos;
    private int zPos;

    public Position(int xPos, int yPos, int zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public Node getNodeFromPosition() {
        for (Node node : Node.nodeMap) {
            if (node.getNodePosition() == this) {
                return node;
            }
        }
        return null;
    }

    public int getPosX() {
        return this.xPos;
    }

    public int getPosY() {
        return this.yPos;
    }

    public int getPosZ() {
        return this.zPos;
    }
}
