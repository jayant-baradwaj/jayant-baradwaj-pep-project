package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;

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

        //Insert the new account into the database
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO account (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);) 
        {
            System.out.println("\n");
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0)
            {
                return null;
            }
            try (ResultSet pkeyResultSet = ps.getGeneratedKeys();) 
            {
                if(pkeyResultSet.next())
                {
                    account.setAccount_id(pkeyResultSet.getInt(1));
                    return account;
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
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
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE username=? AND password=?");)
        {

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            try(ResultSet rs = ps.executeQuery();)
            {
                if(rs.next())
                {
                    return(new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password")));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }
}