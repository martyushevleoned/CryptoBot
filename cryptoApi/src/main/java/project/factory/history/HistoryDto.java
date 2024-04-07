package project.factory.history;

import java.util.Date;

public class HistoryDto {
    private double price;
    private Date time;

    public HistoryDto(double price, Date time) {
        this.price = price;
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "time=" + time +
                ", price=" + price +
                '}';
    }
}
