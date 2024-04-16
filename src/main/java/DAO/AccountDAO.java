package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class AccountDAO {
    
    /**
     * @param account 
    */
    public Account registerAccount(Account account)
    {
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4)
        {
            return null;
        }
        List<Account> accounts = getAllAccounts();
        for(Account acc : accounts)
        {
            if(acc.getUsername().equals(account.getUsername()))
            {
                return null;
            }
        }
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);//, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            return new Account(account.getUsername(), account.getPassword());
            /*ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next())
            {
                int gen_acc_id = pkeyResultSet.getInt(1);
                return new Account(gen_acc_id, account.getUsername(), account.getPassword());
            }*/
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * @param account
    */
    public Account loginAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();

        return null;
    }

    /**
     * @param accountDAO 
    */
    private List<Account> getAllAccounts()
    {
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
