public class BSTOperations {
    public static Node deleteNode(Node root, int deleteKey) {
        if (deleteKey < root.data) {
            root.left = deleteNode(root.left, deleteKey);
        } else if (deleteKey > root.data) {
            root.right = deleteNode(root.right, deleteKey);
        } else {
            if (root.left != null && root.right != null) { // 2 Children
                // Find the inorder successor (smallest value in the right subtree)
                Node successor = findMinValue(root.right);
                root.data = successor.data;
                root.right = deleteNode(root.right, successor.data);
            } else if (root.left != null) {
                return root.left;
            } else if (root.right != null) {
                return root.right;
            } else {
                return null;
            }
        }
        return root;
    }

    private static Node findMinValue(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private static class Node {
        int data;
        Node left;
        Node right;
    }
}
