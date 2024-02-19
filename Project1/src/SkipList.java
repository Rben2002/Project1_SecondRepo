import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V> 
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List
    //private int level; //Highest level of the skiplist

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 1);
        size = 0;
        //level = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     * 
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     * @return ArrayList<KVPair<K, V>>
     *            List of every rectangle with that name
     *        
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        ArrayList<KVPair<K, V>> resultList = new ArrayList<>();
        SkipNode x = head;
        for (int i = head.level; i >= 0; i--) {
            while ((x.forward[i] != null) && 
                (x.forward[i].pair.getKey().compareTo(key) < 0)) {
                x = x.forward[i];
            }
        }
        x = x.forward[0];
        while (x != null && x.pair.getKey().compareTo(key) == 0) {
            resultList.add(x.pair);
            x = x.forward[0];
        }
        return resultList.isEmpty() ? null : resultList;
    }
    


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        // Can't have a null key
        if (it.getKey() == null) {
            System.out.println("ERROR: Key is null");
            return;
        }
        // Get random level
        int newLev = randomLevel();
        // If new level is higher than head, adjust head
        // forward pointers array
        if (newLev > head.level) {
            adjustHead(newLev);
        }

        SkipNode[] nodeUpdate = 
    	        (SkipNode[])Array.newInstance(SkipNode.class, head.level + 1);
        
        // Start from head node and proceed down each level
        // to find insertion point right before biggest node
        SkipNode curr = head;
 
        for (int j = head.level; j >= 0; j--) {
    	    while (curr.forward[j] != null && 
                curr.forward[j].pair.getKey().compareTo(it.getKey()) < 0) {
                curr = curr.forward[j];
            }

            if (j <= newLev) {
                nodeUpdate[j] = curr;
            }
        }

        SkipNode nodeInsert = new SkipNode(it, newLev);

        for (int j = 0; j <= newLev; j++) {
            nodeInsert.forward[j] = nodeUpdate[j].forward[j];
 
            nodeUpdate[j].forward[j] = nodeInsert;
        }
        // update skipList size
        size = size + 1;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    public void adjustHead(int newLevel) {   	
    	SkipNode headReplacement = new SkipNode(null, newLevel);
    	System.arraycopy(head.forward, 0, headReplacement.forward, 
    			0, head.forward.length);
    	head = headReplacement;
    	
    }


    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     * 
     * @param key
     *            the KVPair to be removed
     * @return KVPair<K, V>
     *          returns the removed pair if the pair was valid and null if not
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        SkipNode[] nodeUpdate = (SkipNode[])Array.newInstance(
        		SkipNode.class, head.level + 1);
        
        SkipNode curr = head;
        
        // Start at top level and keep going down until 
        // greatest key that is before the node were looking
        // for is found
        for (int j = head.level; j >= 0; j--) {
        	
        	// Check that key is less than search key in order
        	// to move forward
        	while (curr.forward[j] != null && 
        			curr.forward[j].pair.getKey().compareTo(key) < 0) {
        		curr = curr.forward[j];
        	}
        	
        	// store node in current level update list
        	nodeUpdate[j] = curr;
        }
        
        // move to next suitable node
        curr = curr.forward[0];
        
        // Check if node curr is the one we want to remove
        if (curr != null && curr.pair.getKey().compareTo(key) == 0) {
        	// skip over current node and update forward pointers
        	for (int j = 0; j <= head.level; j++) {
        		if (nodeUpdate[j].forward[j] != curr )
        			break;
        		
        		nodeUpdate[j].forward[j] = curr.forward[j];
        	}
        	// update size since a node has been removed
        	size = size - 1;
        	
        	// return the removed key value pair
        	return curr.pair;
        }
        // function returns null if key isnt found
        return null;
    }
    
  
    /**
     * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> removeByValue(V val) {
        SkipNode current = head;
        SkipNode[] update = 
        		(SkipNode[])Array.newInstance(
        				SkipList.SkipNode.class, head.level + 1);

        for (int i = head.level; i >= 0; i--) {
            while (current.forward[i] != null && 
            		!current.forward[i].element().getValue().equals(val)) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current != null && current.element().getValue().equals(val)) {
            for (int i = 0; i <= head.level 
            		&& update[i].forward[i] == current; i++) {
                update[i].forward[i] = current.forward[i];

            }
            while (head.level > 0 && head.forward[head.level] == null) {
                head.level--;
            }
            return current.element();
        }
        return null;

    }

    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
        System.out.println("SkipList dump:");
        // Check if list is empty
        if (head.forward[0] == null) {
            System.out.printf("Node has depth %d, Value null\n", head.level);
        }
        else {
            //Otherwise go through entire list and print nodes
            SkipNode curr = head;
            while (curr != null) {
                String infoStr = curr.pair == null
                    ? "null" : curr.pair.toString();
                System.out.printf("Node with depth %d, Value %s\n",
                    curr.level, infoStr);
                curr = curr.forward[0];
            }
        }
        
        // Print size of skipList
        System.out.printf("SkipList size is: %d\n", size);
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes
        private SkipNode[] forward;
        // the level of the node
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }
    
    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;
		
        public SkipListIterator() {
        	current = head;
        }
        @Override
		public boolean hasNext() {
			// Auto-generated method stub
			return current.forward[0] != null;
		}

		@Override
		public KVPair<K, V> next() {
			// Auto-generated method stub
			KVPair<K, V> elem = current.forward[0].element();
			current = current.forward[0];
			return elem;
		}
    	
    }

	@Override
	public Iterator<KVPair<K, V>> iterator() {
		// Auto-generated method stub
		return new SkipListIterator();
	}

}