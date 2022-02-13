package model;

import java.time.Month;
import java.util.Objects;

public class MonthTypeCount {
    private String type;
    private Month month;
    private int count;

    public MonthTypeCount(String type, Month month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    public String getType(){
        return type;
    }

    public Month getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setMonth(Month month){
        this.month = month;
    }

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthTypeCount that = (MonthTypeCount) o;
        return count == that.count && type.equals(that.type) && month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, month, count);
    }
}
