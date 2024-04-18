package DAO;

import Util.ConnectionUtil;
import Model.Message;
import Model.Account;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    /**
     * Create a new message for the database
     * @param message 
    */
    public Message createMessage(Message message)
    {
        //If message is blank or over 255 characters, return null
        if(message.getMessage_text().equals("") || message.getMessage_text().length() > 255)
        {
            return null;
        }

        //If user does not exist in the database, return null
        Account sender = getUser(message);
        if(sender == null)
        {
            return null;
        }

        //Insert new message into database
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO messages (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);)
        {

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            try(ResultSet pkeyResultSet = ps.getGeneratedKeys();)
            {
                if(pkeyResultSet.next())
                {
                    int gen_msg_id = pkeyResultSet.getInt(1);
                    return new Message(gen_msg_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
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
     * Helper function to aid with creation of message
     * @param message 
    */
    private Account getUser(Message message)
    {
        //Check to make sure the account exists
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE account_id=?");) 
        {   
            ps.setInt(1, message.getPosted_by());

            try(ResultSet rs = ps.executeQuery();)
            {
                if(rs.next())
                {
                    return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
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
     *  Retrieve all the messages in the database
    */
    public List<Message> retrieveMessages()
    {
        //Create a list of all messages in the database
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();)
        {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /**
     *  Retrieve a message with the message ID = id
     *  @param id
    */
    public Message retrieveMessageByMessageId(int id)
    {
        //Search for message with message_id = id
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        return new Message();
    }

    /**
     *  Delete the message of ID = id
     *  @param id
    */
    public Message deleteMessageByMessageId(int id)
    {
        //Check to see if the message exists, then perform deletion
        Message message = retrieveMessageByMessageId(id);
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        return message;
    }

    /**
     *  Change the message of ID = id
     *  @param id
     *  @param text
    */
    public Message updateMessage(int id, String text)
    {
        //If new text is empty or too long, return null
        if(text.equals("") || text.length() > 255)
        {
            return null;
        }

        //If the message id does not exist, return null
        Message message = retrieveMessageByMessageId(id);
        if(message.equals(new Message()))
        {
            return null;
        }

        //Set message_text of message where message_id = id to text
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, text);
            ps.setInt(2, id);

            message.setMessage_text(text);
            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        return message;
    }

    /**
     *  Retrieve all messages posted by a certain user
     *  @param id
    */
    public List<Message> retrieveMessagesForAccount(int id)
    {
        //Retrieve all messages where posted_by = id
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try 
        {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
