package model;

import java.time.Month;

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
}
