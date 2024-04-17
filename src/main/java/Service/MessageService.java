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

    public Message createMessage(Message message)
    {
        return messageDAO.createMessage(message);
    }

    public List<Message> retrieveMessages()
    {
        return messageDAO.retrieveMessages();
    }

    public Message retrieveMessageByMessageId(int id)
    {
        return messageDAO.retrieveMessageByMessageId(id);
    }

    public Message deleteMessageByMessageId(int id)
    {
        return messageDAO.deleteMessageByMessageId(id);
    }

    public Message updateMessage(int id, String text)
    {
        return messageDAO.updateMessage(id, text);
    }

    public List<Message> retrieveMessagesForAccount(int id)
    {
        return messageDAO.retrieveMessagesForAccount(id);
    }
}
