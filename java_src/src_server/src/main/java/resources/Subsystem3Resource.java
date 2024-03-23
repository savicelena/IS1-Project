/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import entities.Gledanje;
import entities.Ocena;
import entities.Paket;
import entities.Pretplata;
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

@Path("podsistem3")
public class Subsystem3Resource {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory factory;
    
    @Resource(lookup="subsystem1Topic")
    private Topic subsystem1Topic;
    
    @Resource(lookup="subsystem1ReturnTopic")
    private Topic subsystem1ReturnTopic;
    
    @POST
    @Path("paket/{cena}")
    public Response addPack(@PathParam("cena") String price){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 9, false);
            
            TextMessage txtMess = context.createTextMessage(price);
            txtMess.setIntProperty("Namenjen", 9);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @PUT
    public Response changePack(@QueryParam("id") String packID, @QueryParam("price") String price){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 10, false);
            
            TextMessage txtMess = context.createTextMessage(packID + "%" + price);
            txtMess.setIntProperty("Namenjen", 10);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @POST
    @Path("pretplata")
    public Response createSubscription(@QueryParam("userID") String userID, @QueryParam("packID") String packID, @QueryParam("date") String date){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 11, false);
            
            
            TextMessage txtMess = context.createTextMessage(userID + "%" + packID + "%" + date);
            txtMess.setIntProperty("Namenjen", 11);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @POST
    @Path("gledanje")
    public Response createWatch(@QueryParam("userID") String userID, @QueryParam("videoID") String videoID, @QueryParam("start") String start, @QueryParam("watched") String watched){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 12, false);
            
            TextMessage txtMess = context.createTextMessage(userID + "%" + videoID + "%" + start + "%" + watched);
            txtMess.setIntProperty("Namenjen", 12);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @POST
    @Path("ocena")
    public Response createGrade(@QueryParam("userID") String userID, @QueryParam("videoID") String videoID, @QueryParam("grade") String grade){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 13, false);
            
            TextMessage txtMess = context.createTextMessage(userID + "%" + videoID + "%" + grade);
            txtMess.setIntProperty("Namenjen", 13);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.CREATED).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @PUT
    @Path("promenaocene")
    public Response changeGrade(@QueryParam("userID") String userID, @QueryParam("videoID") String videoID, @QueryParam("grade") String grade){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 14, false);
            
            TextMessage txtMess = context.createTextMessage(userID + "%" + videoID + "%" + grade);
            txtMess.setIntProperty("Namenjen", 14);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @DELETE
    @Path("brisanjeocene")
    public Response deleteGrade(@QueryParam("userID") String userID, @QueryParam("videoID") String videoID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 15, false);
            
            TextMessage txtMess = context.createTextMessage(userID + "%" + videoID);
            txtMess.setIntProperty("Namenjen", 15);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("paketi")
    public Response getPack(){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 22, false);
            
            TextMessage txtMess = context.createTextMessage();
            txtMess.setIntProperty("Namenjen", 22);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Paket> packs = (List<Paket>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Paket>>(packs){}).build();
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("pretplata")
    public Response getSub(@QueryParam("id") String id){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 23, false);
            
            TextMessage txtMess = context.createTextMessage(id);
            txtMess.setIntProperty("Namenjen", 23);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Pretplata> subs = (List<Pretplata>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Pretplata>>(subs){}).build();
            }else if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            } 
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("gledanje")
    public Response getWatch(@QueryParam("videoID") String videoID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 24, false);
            
            TextMessage txtMess = context.createTextMessage(videoID);
            txtMess.setIntProperty("Namenjen", 24);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Gledanje> watched = (List<Gledanje>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Gledanje>>(watched){}).build();
            }else if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            } 
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
    @GET
    @Path("ocena")
    public Response getGrade(@QueryParam("videoID") String videoID){
        try {
            JMSContext context = factory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(subsystem1ReturnTopic, "Return=" + 25, false);
            
            TextMessage txtMess = context.createTextMessage(videoID);
            txtMess.setIntProperty("Namenjen", 25);
            producer.send(subsystem1Topic, txtMess);
            
            Message reply = consumer.receive();
            if(reply instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)reply;
                Object object = objMsg.getObject();
                List<Ocena> grades = (List<Ocena>)object;
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<Ocena>>(grades){}).build();
            }else if(reply instanceof TextMessage){
                String returnStr = ((TextMessage)reply).getText();
                return Response.status(Response.Status.OK).entity(returnStr).build();
            } 
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Zahtev nije uspesno izvrsen").build();
    }
    
}
