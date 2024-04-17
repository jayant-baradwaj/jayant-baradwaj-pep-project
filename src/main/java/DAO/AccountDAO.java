package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class AccountDAO {
    
    /**
     * Register a new account in the database
     * @param account 
    */
    public Account registerAccount(Account account)
    {
        //If Username is blank or Password length is less than four, return null
        if(account.getUsername().equals("") || account.getPassword().length() < 4)
        {
            return null;
        }

        //If username already exists in the database, return null
        List<Account> accounts = getAllAccounts();
        for(Account acc : accounts)
        {
            if(acc.getUsername().equals(account.getUsername()))
            {
                return null;
            }
        }

        //Insert the new account into the database
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next())
            {
                int gen_acc_id = pkeyResultSet.getInt(1);
                return new Account(gen_acc_id, account.getUsername(), account.getPassword());
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Check if a username and password pair exists in the database
     * @param account
    */
    public Account loginAccount(Account account)
    {
        //Check to make sure the username and password pair exists in database
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "SELECT * FROM account WHERE username=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return(new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password")));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * private helper method to assist with user registration to check for existing username
    */
    private List<Account> getAllAccounts()
    {
        //Create a list of all accounts registered
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try 
        {
            String sql = "SELECT * FROM account";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        } 

        return accounts;
    }
}
