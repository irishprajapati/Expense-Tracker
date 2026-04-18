package model;
import java.time.LocalDateTime;

public class Expense {
    private  int id;
    private String title;
    private double amount;
    private Category category;
    private LocalDateTime date;
    public Expense(int id, String title, double amount, Category category, LocalDateTime date){
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getDate() {
        return date;
    }
    //overidding the toString method

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", category=" + category +
                ", date=" + date +
                '}';
    }
}
