package trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import list.ItemSet;
import list.Transaction;
/**
 * Trie are used for efficiently searching for a pattern of items in a transaction in frequent
 * itemset mining algorithms. This represents the structure of a Trie.
 *
 */

public class Trie {
    private TrieNode rootNode;
    private final int height;

    public Trie(int height) {
        rootNode = new TrieNode();
        this.height = height;
    }

    /**
     * This method adds a key into the trie.
     * @param itemSet Its length must match the height of the trie.
     * @return true on succesful insertion, false if the key
     *      already exists or the height is not correct.
     */
    public boolean add(ItemSet itemSet) {
        // For this assignment we do not consider the case
        // of different lengths
        if (height != itemSet.size())
            return false;

        // Returns if it contains the key
        if (contains(itemSet))
            return false;

        TrieNode currentNode = getRootNode();
        for (int i = 0; i < height; i++) {
            Integer currentValue = itemSet.get(i);

            if (currentNode.containsKey(currentValue)) {
                currentNode = currentNode.get(currentValue);

            } else {
                TrieNode nextNode = new TrieNode();

                // If it is last node, set it as a leaf node
                if (i == height-1) {
                    nextNode.setLeafNode(true);
                    nextNode.add(itemSet);
                }
                currentNode.put(currentValue, nextNode);
                currentNode = currentNode.get(currentValue);
            }
            
        }

		return true;
    }

    /**
     * This test if a key is inside the trie.
     * @param itemSet Its length must match the height of the trie.
     * @return true if the key is inside the trie, false if the key
     *      does not exists or the height is not correct.
     */
    public boolean contains(ItemSet itemSet) {
        // For this assignment we do not consider the case
        // of different lengths
        if (height != itemSet.size())
            return false;

        // Iterative algorithm to find the key
        TrieNode currentNode = rootNode;
        for (int i = 0; i < height; i++) {
            Integer currentValue = itemSet.get(i);

            if (currentNode.containsKey(currentValue)) {
                currentNode = currentNode.get(currentValue);

            } else {
                return false;
            }
        }
		return true;
    }

    public TrieNode getRootNode() {
        return rootNode;
    }

    private void traverse(TrieNode currentNode, ArrayList<ItemSet> items, ArrayList<Integer> transaction) {
        if (!currentNode.isLeafNode()) {
            for (Integer current : transaction)
                if (currentNode.containsKey(current)) { 
                    traverse(currentNode.get(current), items, transaction);
                }
        
        } else {
            items.add(currentNode.getItemSet());
        }
    }

    /**
     * Find the the combination of a list of integers inside the trie.
     * @param matchedItemSet Output parameter which will contain the ItemSets
     *        which match with the given input.
     * @param transaction A list of Integers which its combinations will test 
     *        if its contained in the trie.
     */
    public void findItemSets(ArrayList<ItemSet> matchedItemSet, ArrayList<Integer> transaction) {
        traverse(getRootNode(), matchedItemSet, transaction);
    }
}
