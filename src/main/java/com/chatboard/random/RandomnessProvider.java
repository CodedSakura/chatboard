package com.chatboard.random;

import java.security.SecureRandom;

/**
 * A class for providing cryptographically
 * strong randomness
 */
public class RandomnessProvider {

    private RandomnessProvider() {}
    
    
    
    private static SecureRandom sr;
    
    static {
        sr = new SecureRandom();
    }
    
    
    
    /**
     * Generates a cryptographically strong
     * random integer
     */
    public static synchronized int getRandomInt() {
        return sr.nextInt();
    }
    
    /**
     * Generates a a cryptographically strong
     * random integer between 0 (inclusive) and
     * max (exclusive)
     * 
     * @throws IllegalArgumentException if max <= 0
     */
    public static synchronized int getRandomInt(int max) {
        if(max <= 0) {
            throw new IllegalArgumentException();
        }
        return sr.nextInt(max);
    }
    
    /**
     * Generates a a cryptographically strong
     * random integer between min (inclusive) and
     * max (exclusive)
     * 
     * @throws IllegalArgumentException if min <= 0 || max <= 0 || min >= max
     */
    public static synchronized int getRandomInt(int min, int max) {
        if(min <= 0 || max <= 0 || min >= max) {
            throw new IllegalArgumentException();
        }
        return (sr.nextInt(max - min + 1) + min);
    }
    
    /**
     * Generates a cryptographically strong
     * random double between 0.0 (inclusive)
     * and 1.0 (exclusive)
     */
    public static synchronized double getRandom() {
        return sr.nextDouble();
    }
    
    /**
     * Generates a cryptographically strong
     * random long
     */
    public static synchronized long getRandomLong() {
        return sr.nextLong();
    }
    
    /**
     * Chooses a random array element from a given array based
     * on a cryptographically strong random number generator
     */
    public static synchronized <T> T getRandomArrayElement(T[] array) {
        if(array.length == 0) {
            return null;
        }
        int index = (int) ((double) (array.length) * getRandom());
        return array[index];
    }
    
    
}
