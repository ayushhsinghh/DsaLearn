package org.ayushsingh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class DSADesignQuestion {

    // LRU Cache Design
    // Using Map and DoubleLinkedList
    class LRUCache {
        class Node {
            public int key;
            public int val;
            public Node next,prev;

            public Node(int key, int val) {
                this.val = val;
                this.key = key;
            }
            public Node(int key, int val, Node mext, Node prev) {
                this(key, val);
                this.next = next;
                this.prev = prev;
            }
        }
        Node head = null;  // Head of LinkedList. Always contains the most recently used element
        Node tail = null; // Tail, Always remove elements from tail.
        Map<Integer, Node> map;
        int cap;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            this.cap = capacity;
        }

        public int get(int key) {
            if(map.containsKey(key)) {   // check if its present
                Node tmp = map.get(key);
                remove(tmp);    // remove the element from Linkedlist
                addToFront(tmp);  // Add the same element to the top of linkedlist i.e. Head
                return tmp.val;
            }

            return -1;
        }

        public void put(int key, int value) {
            if (cap == 0) return;   // If Capacity is 0, dont add.
            if(map.containsKey(key)) {   // Check if the value is already present
                Node tmp = map.get(key);
                tmp.val = value;     // Update the value of this key
                remove(tmp);       // Move the element to the head of linkedList.
                addToFront(tmp);
                return;
            }

            Node toAdd = new Node(key, value);
            if(head == null) {
                head = toAdd;    // If first element in the linkedlist, mark it Head and tail.
                tail = toAdd;
            } else {
                addToFront(toAdd);    // Always add new element to front of the linkedlist.
            }
            map.put(key, toAdd);

            if(map.size() > cap) {
                removeTail();   // if the capacity is full, remove an element from tail.
            }

        }

        void addToFront(Node node) {
            node.prev = null;
            node.next = head;

            if(head != null) {
                head.prev = node;
            }

            head = node;
            if(tail == null) {
                tail = head;
            }
        }

        void remove(Node node) {
            // if Node is Head
            if(node.prev == null) {
                head = node.next;
                if(head != null)
                    head.prev = null;
                else tail = null;
                return;
            }

            // if Node is Tail
            if(node.next == null) {
                tail = tail.prev;
                tail.next = null;
                return;
            }

            // if Middle
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }

        void removeTail() {
            if(tail == null) return;
            map.remove(tail.key);
            remove(tail);
        }
    }


    // MinStack
    // Use 2 Stack, one will always contains most recent min Elemnt so far and other as normal stack.
    class MinStack {
        Stack<Integer> minS;
        Stack<Integer> st;

        public MinStack() {
            minS = new Stack<>();
            st = new Stack<>();
        }

        public void push(int val) {
            if(minS.isEmpty()) {
                minS.push(val); // if it's the first element, Add it to minStack, as it min. so far.
            } else {
                if(minS.peek() >= val) {
                    minS.push(val); // Later, Only add to min Stack if the top Element of minStack is larger then current element
                }
            }
            st.push(val);
        }

        public void pop() {
            if(st.peek().equals(minS.peek())) {
                minS.pop();  // remove minStack only if both the stack peek is same.
            }
            st.pop();
        }

        public int top() {
            return st.peek();
        }

        public int getMin() {
            return minS.peek();
        }
    }


    // Insert Delete GetRandom O(1)
    // Use List and Map, List to get the random element access using index.
    class RandomizedSet {
        Map<Integer, Integer> st; // the map contains, Map<Number, Index of that number in list>
        List<Integer> idx;
        Random random;
        public RandomizedSet() {
            st = new HashMap<>();
            idx = new ArrayList<>();
            random = new Random();
        }


        public boolean insert(int val) {
            if(st.containsKey(val)) {
                return false;
            }
            idx.add(val);
            st.put(val, idx.size()-1);
            return true;
        }

        // while remove, just swap the element to remove from list with the last element and update the map with the index.
        public boolean remove(int val) {
            if(!st.containsKey(val)) {
                return false;
            }
            int toRemove = st.get(val);
            int last = idx.get(idx.size()-1);


            idx.set(toRemove, last);
            st.put(last, toRemove);


            st.remove(val);
            idx.remove(idx.size() -1);

            return true;
        }

        public int getRandom() {
            int id = random.nextInt(idx.size());
            return idx.get(id);
        }
    }
}
