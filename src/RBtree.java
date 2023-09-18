enum Color {RED, BLACK};

public class RBtree {
    Node root;

    class Node {
        int value;
        Node left;
        Node right;
        Color color;
    }

    public boolean insert(int value) {
        if (root != null) {
            boolean result = insert(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean insert(Node node, int value) {
        if (node.value == 0)
            return false;
        if (node.value > value) {
            if (node.left != null) {
                boolean result = insert(node.left, value);
                node.left = rebalance(node.left);
                return result;
            } else {
                node.left = new Node();
                node.left.color = Color.RED;
                node.left.value = value;
                return true;
            }
        } else {
            if (node.right != null) {
                boolean result = insert(node.right, value);
                node.right = rebalance(node.right);
                return result;
            } else {
                node.right = new Node();
                node.right.color = Color.RED;
                node.right.value = value;
                return true;
            }
        }
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.RED
                    && (result.left == null || result.left.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED
                    && result.left.left != null && result.left.left.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED
                    && result.right != null && result.right.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        } while (needRebalance);
        return result;
    }

    private void colorSwap(Node node) {
        node.right.color = Color.BLACK;
        node.left.color = Color.BLACK;
        node.color = Color.RED;
    }

    private Node leftSwap(Node node) {
        Node leftChild = node.left;
        Node between = leftChild.right;
        leftChild.right = node;
        node.left = between;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private Node rightSwap(Node node) {
        Node rightChild = node.right;
        Node between = rightChild.left;
        rightChild.left = node;
        node.right = between;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    public boolean find(int value) {
        return find(root, value);
    }

    private boolean find(Node node, int value) {
        if (node == null) {
            return false;
        }
        if (node.value == value) {
            return true;
        }

        if (node.value < value) {
            return find(node.right, value);
        } else {
            return find(node.left, value);
        }
    }
}

class Main {
    public static void main(String[] args) {
        RBtree rbtree = new RBtree();
        for (int i = 1; i <= 100; i++) {
                rbtree.insert(i);
        }
    }
}
