package validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import model.Expense;
public class ExpenseValidator {
    public List<String> validate(Expense e) {
        List<String> errors = new ArrayList<>();
        //title cannot be null or blank
        if(e.getTitle() ==null || e.getTitle().isBlank()){
            errors.add("Title can't be empty");
        }
        //  amount must be greater than 0
        if(e.getAmount() <= 0){
            errors.add("Amount cannot be negative");
        }
        if(e.getCategory() == null){
            System.out.println("Category cannot be null");
        }
        if(e.getDate().isAfter(LocalDateTime.now())){
            System.out.println("Date cannot be in future");
        }
        return errors;
    }

}
