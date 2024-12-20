package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.accountDAO;
import Model.Account;
import Model.Message;
import Service.accountService;
import Service.messageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    accountService accountService;
    messageService messageService;
    public SocialMediaController(){
        this.accountService = new accountService();
        this.messageService = new messageService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login", this::getAccountByUsernamePassword);
        app.post("/register", this::registerAccount);
        app.get("/messages", this::getAllMessages);
        app.get("/accounts/{id}/messages",this::getMessageById);
        app.post("/messages",this::addMessage);
        app.get("/messages/{id}",this::findMessageByMessageId);
        app.delete("/messages/{id}",this::deleteMessageByMessageId);
        app.patch("/messages/{id}",this::updateMessageByMessageId);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void getAccountByUsernamePassword(Context ctx) throws  JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.getAccountByUsernamePassword(account);
        System.out.println(foundAccount);
        if(foundAccount!=null){
            ctx.json(mapper.writeValueAsString(foundAccount));
        }else{
            ctx.status(401);
        }
    }
    private void registerAccount(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(ctx.body(), Account.class);
        if(newAccount.getPassword().length() <4 || accountService.findAccountByUsername(newAccount.getUsername())!=null || newAccount.getUsername() == ""){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(accountService.addNewAccount(newAccount)));
        }
    }

    private void getAllMessages(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        
        List<Message> res= this.messageService.getAllMessages();
        if(res != null){
            ctx.json(mapper.writeValueAsString(res));
        }else{
            ctx.status(400);
        }
    }
    private void getMessageById(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int acc = Integer.parseInt(ctx.pathParam("id"));
        List<Message> res = this.messageService.getMessageById(acc);
        if(res != null){
            ctx.json(mapper.writeValueAsString(res));
        }else{
            ctx.status(400);
        }
    }
    private void addMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(ctx.body(),Message.class);
        if(accountService.findAccountById(newMessage.getPosted_by()) != null && newMessage.getMessage_text().length()<=255 && newMessage.getMessage_text()!= ""){
            ctx.json(mapper.writeValueAsString(this.messageService.addMessage(newMessage)));
        }else{
            ctx.status(400);
        }
    }

    private void findMessageByMessageId(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int msgId = Integer.parseInt(ctx.pathParam("id"));
        Message message = this.messageService.findMessageByMessageId(msgId);
        if(message!=null){
            ctx.json(mapper.writeValueAsString(message));
        }else{
            ctx.status(200);
        }

    }
    private void deleteMessageByMessageId(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int msgId = Integer.parseInt(ctx.pathParam("id"));
        Message message = this.messageService.deleteMessageByMessageId(msgId);
        if(message!=null){
            ctx.json(mapper.writeValueAsString(message));
        }else{
            ctx.status(200);
        }
    }
    private void updateMessageByMessageId(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int msgId = Integer.parseInt(ctx.pathParam("id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        System.out.println(message.getMessage_text());

        if(this.messageService.findMessageByMessageId(msgId)!=null && message.getMessage_text()!="" && message.getMessage_text().length()<=255){
            
            ctx.json(mapper.writeValueAsString(this.messageService.updateMessageByMessageId(msgId,message)));
        }else{
            ctx.status(400);
        }
    }
}