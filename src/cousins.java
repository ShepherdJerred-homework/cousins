// cousins
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class cousins {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("cousins.in");
        File outputFile = new File("cousins.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            int numberOfNodesToRead = Integer.valueOf(splitLine[0]);
            int intToCheck = Integer.valueOf(splitLine[1]);

            if (numberOfNodesToRead == 0 && intToCheck == 0) {
                break;
            }

            line = scanner.nextLine();
            String[] nodes = line.split(" ");
            int[] nodesAsInts = new int[nodes.length];
            for (int i = 0; i < nodes.length; i++) {
                nodesAsInts[i] = Integer.valueOf(nodes[i]);
            }

            Node nodeToCheck = null;
            Node head = new Node(nodesAsInts[0], null);

            Node currentNode = head;
            for (int i = 1; i < nodesAsInts.length; i++) {
                if (nodesAsInts[i] - 1 != nodesAsInts[i - 1]) {
                    currentNode = findNextChildWithoutChildren(head);
                }
                Node n = new Node(nodesAsInts[i], currentNode);
                currentNode.children.add(n);
                if (n.value == intToCheck) {
                    nodeToCheck = n;
                }
            }

            int cousins = findNumberOfCousins(nodeToCheck);

            System.out.println(cousins);
            printWriter.println(cousins);

        }
        printWriter.close();
    }

    private static int findNumberOfCousins(Node head) {
        if (head == null || head.parent == null || head.parent.parent == null) {
            return 0;
        }

        Node parent = head.parent;
        Node grandparent = parent.parent;

        int total = 0;
        for (Node grandchild : grandparent.children) {
            if (grandchild != parent) {
                total += countChildren(grandchild);
            }
        }

        return total;
    }

    private static int countChildren(Node head) {
        return head.children.size();
    }

    private static Node findNextChildWithoutChildren(Node head) {
        if (head.children.isEmpty()) {
            return head;
        } else {
            List<Node> nodes = new ArrayList<>();
            for (Node child : head.children) {
                nodes.add(findNextChildWithoutChildren(child));
            }
            Node minNode = null;
            for (Node node : nodes) {
                if (minNode == null || node.value < minNode.value) {
                    minNode = node;
                }
            }
            return minNode;
        }
    }

    public static class Node {
        int value;
        ArrayList<Node> children;
        Node parent;
        public Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", children=" + children +
                    '}';
        }
    }
}
