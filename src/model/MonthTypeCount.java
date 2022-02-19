package model;

import java.time.Month;
import java.util.Objects;

/**
 * Models MonthTypeCount which describes the number of appointments of a certain type that are in the month.
 */
public class MonthTypeCount {
    private String type;
    private Month month;
    private int count;

    /**
     * Constructor for MonthTypeCount.
     *
     * @param type the type to set
     * @param month the month to set
     * @param count the count to set
     */
    public MonthTypeCount(String type, Month month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType(){
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type to set
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets the month.
     *
     * @param month the month to set
     */
    public void setMonth(Month month){
        this.month = month;
    }

    /**
     * Gets the count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count.
     *
     * @param count the count to set.
     */
    public void setCount(int count){
        this.count = count;
    }

    /**
     * Override of equals method: MonthTypeCounts are equal when count, type, and month are equal.
     *
     * @param o the object to check for equality
     * @return true of the objects are equal, false if they are not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthTypeCount that = (MonthTypeCount) o;
        return count == that.count && type.equals(that.type) && month == that.month;
    }

    /**
     * Override of hashCode
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, month, count);
    }

    /**
     * Override of toString: returns type, month, count in a String
     * @return the month, type, and count in a String
     */
    @Override
    public String toString() {
        return "MonthTypeCount{" +
                "type='" + type + '\'' +
                ", month=" + month +
                ", count=" + count +
                '}';
    }
}
