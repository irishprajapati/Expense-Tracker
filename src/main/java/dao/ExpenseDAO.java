package dao;
import model.Expense;
import model.Category;
import db.DBConnection;

import java.sql.*;
import java.util.*;

public class ExpenseDAO {
    public void addExpense(Expense e) throws SQLException{
        String sql = "INSERT INTO expenses(title, amount, category, date) VALUES(?,?,?,?)";
        //try method to establish a connection
        try(Connection con = DBConnection.getConnection();
            //Prepared Statement to execute the SQL
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, e.getTitle());
            ps.setDouble(2, e.getAmount());
            ps.setString(3, e.getCategory().name());
            ps.setTimestamp(4, Timestamp.valueOf(e.getDate()));
            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("Expense added successfully as: " + e.getTitle());
            }else{
                System.out.println("Something went wrong. Error adding expenses.");
            }
            //e was already declared in Expense e so renamed as message
        }catch (SQLException message){
            System.out.println("Error adding expense: " + message.getMessage());
        }
    }
    public List<Expense> getAllExpenses() throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        Category.valueOf(rs.getString("category")),
                        rs.getTimestamp("date").toLocalDateTime()
                );
                expenses.add(expense);
            }
        }catch (SQLException e){
            System.out.println("Error adding expenses: " +  e.getMessage());
        }
        return expenses;
    }
    public List<Expense> getExpenseByCategory(String category) throws SQLException{
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE category=?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Expense expense = new Expense(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getDouble("amount"),
                            Category.valueOf(rs.getString("category")),
                            rs.getTimestamp("date").toLocalDateTime()
                    );
                    expenses.add(expense);
                }
            } catch (SQLException e){
            System.out.println("Error getting expenses: " + e.getMessage());
        }
        return  expenses;
        }
    public Map<String, Double> getMonthlyTotals() throws SQLException{
        Map<String, Double> data = new LinkedHashMap<>();
        String sql = "SELECT \n" +
                "    TO_CHAR(date, 'YYYY-MM') AS month,\n" +
                "    SUM(amount) AS total\n" +
                "FROM expenses\n" +
                "GROUP BY TO_CHAR(date, 'YYYY-MM')\n" +
                "ORDER BY month";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String month = rs.getString("month");
                double total = rs.getDouble("total");
                data.put(month,total);
                }
            } catch (SQLException e){
            System.out.println("Error getting monthly expenses: " + e.getMessage());
        }
        return data;
    }
    public void deleteExpense(int id) throws SQLException{
        String sql = "DELETE FROM expenses WHERE id = ?";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Expense deleted successfully");
            }else{
                System.out.println("Something went wrong");
            }
        }catch (SQLException e){
            System.out.println("Error deleting expense: " + e.getMessage() );
        }
    }
}
