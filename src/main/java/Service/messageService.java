package Service;

import java.util.List;

import DAO.messageDAO;
import Model.Message;

public class messageService {
    private messageDAO messageDAO;
    public messageService(){
        this.messageDAO = new messageDAO();
    }
    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    public List<Message> getMessageById(int id){
        return this.messageDAO.getMessageById(id);
    }
    public Message addMessage(Message message){
        return this.messageDAO.addMessage(message);
    }
    public Message findMessageByMessageId(int messageId){
        return this.messageDAO.findMessageByMessageId(messageId);
    }
    public Message deleteMessageByMessageId(int msgId) {
        // TODO Auto-generated method stub
        return this.messageDAO.deleteMessageByMessageId(msgId);
    }
    public Message updateMessageByMessageId(int msgId, Message message) {
        // TODO Auto-generated method stub
        return this.messageDAO.updateMessageByMessageId(msgId,message);
    }
}
