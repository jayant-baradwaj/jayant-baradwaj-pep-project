package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

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
        app.get("/messages", this::retrieveMessagesHandler); //4
        app.get("/messages/{message_id}", this::retrieveMessageByMessageIdHandler); //5
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler); //6
        app.patch("/messages/{message_id}", this::updateMessageHandler); //7
        app.get("/accounts/{account_id}/messages", this::retrieveMessagesForAccountHandler); //8
        
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
            ctx.status(200);
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
        Account login = accountService.loginAccount(account);
        if(login != null)
        {
            ctx.json(mapper.writeValueAsString(login));
            ctx.status(200);
        }
        else
        {
            ctx.status(401);
        }
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void createMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null)
        {
            ctx.json(mapper.writeValueAsString(createdMessage));
            ctx.status(200);
        }
        else
        {
            ctx.status(400);
        }
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void retrieveMessagesHandler(Context ctx)
    {
        List<Message> messages = messageService.retrieveMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void retrieveMessageByMessageIdHandler(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.retrieveMessageByMessageId(id);
        ctx.json(message);
        ctx.status(200);
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void deleteMessageByMessageIdHandler(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByMessageId(id);
        ctx.json(message);
        ctx.status(200);
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void updateMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        String newText = mapper.readValue(ctx.body(), String.class);
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.updateMessage(id, newText);
        if(message != null)
        {
            ctx.json(message);
            ctx.status(200);
        }
        else
        {
            ctx.status(400);
        }
    }

    /**
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
    */
    private void retrieveMessagesForAccountHandler(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.retrieveMessagesForAccount(id);
        ctx.json(messages);
        ctx.status(200);
    }

}