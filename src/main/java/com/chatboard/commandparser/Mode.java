package com.chatboard.commandparser;

/**
 * A class for wrapping execution modes
 * for a command, e.g., YES|NO
 */
public class Mode {

    public Mode(String mode) {
        this.mode = mode.toUpperCase();
    }
    
    
    
    private String mode;
    
    
    
    /** Returns the string value of the mode */
    public String getMode() {
        return mode;
    }
    @Override
    public String toString() {
        return "Mode[" + mode + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Mode)) {
            return false;
        }
        Mode other = (Mode) obj;
        if (mode == null) {
            if (other.mode != null) {
                return false;
            }
        } else if (!mode.equals(other.mode)) {
            return false;
        }
        return true;
    }
    

}
