package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    /**
     * @param messageDAO 
    */
    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    /**
     * @param message
    */
    public Message createMessage(Message message)
    {
        return messageDAO.createMessage(message);
    }

    /**
     *  
    */
    public List<Message> retrieveMessages()
    {
        return messageDAO.retrieveMessages();
    }

    /**
     * @param id 
    */
    public Message retrieveMessageByMessageId(int id)
    {
        return messageDAO.retrieveMessageByMessageId(id);
    }

    /**
     * @param id 
    */
    public Message deleteMessageByMessageId(int id)
    {
        return messageDAO.deleteMessageByMessageId(id);
    }

    /**
     * @param id
     * @param text 
    */
    public Message updateMessage(int id, String text)
    {
        return messageDAO.updateMessage(id, text);
    }

    /**
     * @param id 
    */
    public List<Message> retrieveMessagesForAccount(int id)
    {
        return messageDAO.retrieveMessagesForAccount(id);
    }
}
