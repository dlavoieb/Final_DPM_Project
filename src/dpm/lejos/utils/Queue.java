package dpm.lejos.utils;

import java.util.EmptyQueueException;

/**
 * Override <code>java.util.Queue</code> to change
 * return type of pop from <code>Object</code> to <code>E</code>
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class Queue <E> extends java.util.Queue<E>{
    /**
     * fetches an object from the start of the Queue
     * and removes it
     * @return Object the object removed from the start of the stock
     * @throws EmptyQueueException
     */
    @Override
    public synchronized E pop() throws EmptyQueueException {
        // get object
        E popped = peek();
        // remove and return object
        removeElementAt(0);
        return popped;
    } // pop()

    ////////////////////////////////////////////
    /**
     * fetches an object from the start of the Queue
     * <br>does not remove it!
     * @return Object the object at the start of the Queue
     * @throws EmptyQueueException
     */
    @Override
    public synchronized E peek() throws EmptyQueueException {
        // empty Queue?
        if(size()==0)
            throw new EmptyQueueException();
        // return first element
        return elementAt(0);
    } // peek()

}
