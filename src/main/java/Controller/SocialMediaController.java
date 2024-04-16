package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler); //1
        app.post("/login", this::loginAccountHandler); //2
        app.post("/messages", this::createMessageHandler); //3
        app.get("/messages", this::getMessagesHandler); //4
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler); //5
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler); //6
        app.patch("/messages/{message_id}", this::updateMessageHandler); //7
        app.get("/accounts/{account_id}/messages", this::getMessageForAccountHandler); //8
        
        return app;
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount != null)
        {
            ctx.json(mapper.writeValueAsString(registeredAccount));
        }
        else
        {
            ctx.status(400);
        }
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void loginAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        //accountService.loginAccount
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void createMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        //messageService.createMessage
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void getMessagesHandler(Context ctx)
    {
        //messageService.getMessages
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void getMessageByMessageIdHandler(Context ctx)
    {
        //messageService.getMessageById
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void deleteMessageByMessageIdHandler(Context ctx)
    {
        //messageService.deleteMessageById
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void updateMessageHandler(Context ctx)
    {
        //messageService.updateMessage
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void getMessageForAccountHandler(Context ctx)
    {
        //messageService.getMessageForAccount
    }

}