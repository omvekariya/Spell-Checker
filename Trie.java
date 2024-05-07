import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    
    TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.isEndOfWord;
    }

    public String finder(String toFind) {
        if (search(toFind)) {
            return toFind;
        }
        Stack<AbstractMap.SimpleEntry<TrieNode, String>> stack = new Stack<>();
        stack.push(new AbstractMap.SimpleEntry<>(root, ""));
        int closestEd = Integer.MAX_VALUE;
        String closestWord = "";
        while (!stack.isEmpty()) {
            AbstractMap.SimpleEntry<TrieNode, String> entry = stack.pop();
            TrieNode currentNode = entry.getKey();
            String currentPrefix = entry.getValue();

            if (currentNode.isEndOfWord) {
                int ed = EditDistance.editDist(toFind, currentPrefix);
                if (ed == 0) {
                    return toFind;
                } else if (ed < closestEd) {
                    closestEd = ed;
                    closestWord = currentPrefix;
                }
            }

            for (Map.Entry<Character, TrieNode> childEntry : currentNode.children.entrySet()) {
                char nextChar = childEntry.getKey();
                TrieNode nextNode = childEntry.getValue();
                stack.push(new AbstractMap.SimpleEntry<>(nextNode, currentPrefix + nextChar));
            }
        }
        return closestWord;
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private void delete(TrieNode node, String word, int index) {
        if (index == word.length()) {
            node.isEndOfWord = false;
            return;
        }

        char currentChar = word.charAt(index);
        TrieNode nextNode = node.children.get(currentChar);

        delete(nextNode, word, index + 1);

        if (nextNode.children.isEmpty() && !nextNode.isEndOfWord) {
            node.children.remove(currentChar);
        }
    }

    public void updateDict() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("dictionary.txt")));
        Stack<AbstractMap.SimpleEntry<TrieNode, String>> stack = new Stack<>();
        stack.push(new AbstractMap.SimpleEntry<>(root, ""));
        while (!stack.isEmpty()) {
            AbstractMap.SimpleEntry<TrieNode, String> entry = stack.pop();
            TrieNode currentNode = entry.getKey();
            String currentPrefix = entry.getValue();

            if (currentNode.isEndOfWord) {
                writer.write(currentPrefix);
                writer.newLine();
            }

            for (Map.Entry<Character, TrieNode> childEntry : currentNode.children.entrySet()) {
                char nextChar = childEntry.getKey();
                TrieNode nextNode = childEntry.getValue();
                stack.push(new AbstractMap.SimpleEntry<>(nextNode, currentPrefix + nextChar));
            }
        }
    }
}
