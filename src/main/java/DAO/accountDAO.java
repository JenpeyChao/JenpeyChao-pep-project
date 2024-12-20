package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class accountDAO {
    public List<Account> getAllAccounts(){
        List<Account> res = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Account account = new Account(resultSet.getInt("account_id"),resultSet.getString("username"),resultSet.getString("password"));
                res.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return res;
    }
    public Account getAccountByUsernamePassword(String username,String password){
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            System.out.println(getAllAccounts());
            // System.out.println(username);
            // System.out.println(password);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                System.out.println(rs);
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                // System.out.println(rs.next());
                return account;
            }
        }catch(SQLException e ){
            System.out.println(e.getMessage());
        }
        return null;

    }
    public Account findAccountByUsername(String username) {
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                System.out.println(rs);
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                // System.out.println(rs.next());
                return account;
            }
        }catch(SQLException e ){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account findAccountById(int id) {
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                System.out.println(rs);
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                // System.out.println(rs.next());
                return account;
            }
        }catch(SQLException e ){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account addNewAccount(Account newAccount) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            System.out.println(newAccount);
            String sql = "insert into account(username,password) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newAccount.getUsername());
            preparedStatement.setString(2, newAccount.getPassword());
            preparedStatement.executeUpdate();
            return findAccountByUsername(newAccount.getUsername());
        }catch(SQLException e ){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
