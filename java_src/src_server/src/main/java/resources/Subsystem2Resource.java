/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import entities.Kategorija;
import entities.Video;
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
import javax.ws.rs.DELETE;
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

@Path("podsistem2")
public class Subsystem2Resource {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory factory;
    
    @Resource(lookup="subsystem1Topic")
    private Topic subsystem1Topic;
    
    @Resource(lookup="subsystem1ReturnTopic")
    private Topic subsystem1ReturnTopic;
    
    
    @POST
    @Path("{catName}")
    public Response addCathegory(@PathParam("catName") String catName){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 5, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText(catName);
            txtMess.setIntProperty("Namenjen", 5);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                TextMessage txt = (TextMessage)reply;
                String returnStr = txt.getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @POST
    @Path("video")
    public Response addVideo(@QueryParam("name") String name, @QueryParam("duration") String duration, @QueryParam("user") String userID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 6, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setText(name + "%" + duration + "%" + userID);
            txtMess.setIntProperty("Namenjen", 6);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @PUT
    @Path("{videoID}")
    public Response changeVideoName(@PathParam("videoID") String videoID, @QueryParam("name") String name){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 7, false);
            
            TextMessage txtMess = context.createTextMessage(videoID + "%" + name);
            txtMess.setIntProperty("Namenjen", 7);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
        
    }
    
    @POST
    @Path("videokat")
    public Response addCatToVid(@QueryParam("catID") String catID, @QueryParam("videoID") String videoID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 8, false);
            
            TextMessage txtMess = context.createTextMessage(catID + "%" + videoID);
            txtMess.setIntProperty("Namenjen", 8);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
        
    }
    
    @DELETE
    @Path("videobrisanje")
    public Response deleteVideo(@QueryParam("userID") String userID, @QueryParam("videoID") String videoID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 16, false);
            
            TextMessage txtMess = context.createTextMessage(userID + "%" + videoID);
            txtMess.setIntProperty("Namenjen", 16);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
        
    }
    
    @GET
    @Path("kategorije")
    public Response getCat(){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 19, false);
            
            TextMessage txtMess = context.createTextMessage("Pronadji kategorije");
            txtMess.setIntProperty("Namenjen", 19);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Kategorija> catList = (List<Kategorija>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Kategorija>>(catList) {}).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("videosnimci")
    public Response getVideos(){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 20, false);
            
            TextMessage txtMess = context.createTextMessage("Pronadji video snimke");
            txtMess.setIntProperty("Namenjen", 20);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Video> videoList = (List<Video>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Video>>(videoList) {}).build();
                
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("kategorijevidea/{videoID}")
    public Response getCatVideo(@PathParam("videoID") String videoID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 21, false);
            
            TextMessage txtMess = context.createTextMessage(videoID);
            txtMess.setIntProperty("Namenjen", 21);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Kategorija> catList = (List<Kategorija>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Kategorija>>(catList) {}).build();
            }else if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    
    
}
