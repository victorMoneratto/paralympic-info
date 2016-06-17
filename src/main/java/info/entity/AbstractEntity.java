package info.entity;

import java.io.Serializable;

/**
 * AbstractEntity is the parent class for all of our mapped entity.
 * It's purpose is to provide some utilities such as having some
 * meaningful object string representation.
 */
public abstract class AbstractEntity implements Serializable{

    /**
     * Get a string representation for the primary key.
     * This becomes the return value of the toString() method
     * and is quite useful because then displaying the entity
     * as anobject returns a reasonable output.
     *
     * @return a formatted string with the entity primary key
     */
    public abstract String getFormattedPrimaryKey();

    @Override
    public String toString() {
        return getFormattedPrimaryKey();
    }
}
