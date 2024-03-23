/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

;
import entities.Korisnik;
import entities.Mesto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;


/**
 *
 * @author HP
 */

@Path("podsistem1")
//@Stateless
public class Subsystem1Resource {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory factory;
    
    @Resource(lookup="subsystem1Topic")
    private Topic subsystem1Topic;
    
    @Resource(lookup="subsystem1ReturnTopic")
    private Topic subsystem1ReturnTopic;
    
    
    @POST
    @Path("{nazivGrada}")
    public Response addCity(@PathParam("nazivGrada") String name){
        
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 1, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText(name);
            txtMess.setIntProperty("Namenjen", 1);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                TextMessage txt = (TextMessage)reply;
                String returnStr = txt.getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
        
    }
    
    @POST
    public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("born") String born, @QueryParam("gender") String gender, @QueryParam("place") String IDMes){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 2, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText(name + "%" + email + "%" + born + "%" + gender + "%" + IDMes);
            txtMess.setIntProperty("Namenjen", 2);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                TextMessage txt = (TextMessage)reply;
                String returnStr = txt.getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @PUT
    @Path("email")
    public Response changeOfEmail(@QueryParam("id") String userID,@QueryParam("email") String email){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 3, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText(userID + "%" + email);
            txtMess.setIntProperty("Namenjen", 3);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                TextMessage txt = (TextMessage)reply;
                String returnStr = txt.getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @PUT
    public Response changeOfPlace(@QueryParam("userID") String userID, @QueryParam("placeName") String placeName){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 4, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText(userID + "%" + placeName);
            txtMess.setIntProperty("Namenjen", 4);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                TextMessage txt = (TextMessage)reply;
                String returnStr = txt.getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
        
    }
    
    @GET
    @Path("mesta")
    public Response getAllPlaces(){
        try {
            JMSContext context = factory.createContext();
            //context.setClientID("17");
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 17, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText("Pronadji sve gradove");
            txtMess.setIntProperty("Namenjen", 17);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Mesto> cities = (List<Mesto>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Mesto>>(cities) {}).build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("korisnici")
    public Response getAllUsers(){
        
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 18, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText("Pronadji sve korisnike");
            txtMess.setIntProperty("Namenjen", 18);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Korisnik> users = (List<Korisnik>) object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Korisnik>>(users) {}).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
        
    }
    
}
