package org.ayushsingh;

import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueueQuestions {

    //Find Median from Data Stream
    /*
    * Think of it like this:
        maxQ is the left hand holding smaller values
        minQ is the right hand holding larger values
        Your thumbs (heap tops) touch at the median boundary.

    * Whenever:
        One hand holds too much → pass the top item to the other hand.
        A new number arrives → compare it with the boundary (maxQ.peek()) to decide which hand takes it.
    * */
    static class MedianFinder {
        PriorityQueue<Integer> minQ;
        PriorityQueue<Integer> maxQ;

        public MedianFinder() {
            maxQ = new PriorityQueue<>(Collections.reverseOrder());
            minQ = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (maxQ.isEmpty() || num <= maxQ.peek()) {
                maxQ.offer(num);
            } else {
                minQ.offer(num);
            }
            // Rebalance
            if (maxQ.size() > minQ.size() + 1) {
                minQ.offer(maxQ.poll());
            } else if (minQ.size() > maxQ.size()) {
                maxQ.offer(minQ.poll());
            }
        }
        public double findMedian() {
            if ((maxQ.size() + minQ.size()) % 2 == 0) {
                return (maxQ.peek() + minQ.peek()) / 2d;
            } else {
                return maxQ.peek() / 1d;
            }
        }
    }


    public static void main(String[] args) {

    }


}
