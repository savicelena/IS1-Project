/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1subsystem1;

import entities.Korisnik;
import entities.Mesto;
import java.io.Serializable;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 *
 * @author HP
 */


public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory factory;
    
    @Resource(lookup="subsystem1Topic")
    private static Topic subsystem1Topic;
    
    @Resource(lookup="subsystem1ReturnTopic")
    private static Topic subsystem1ReturnTopic;
    
    @Resource(lookup="subsystemTalk")
    private static Topic subsystemTalk;
    
    
    public static void request1(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "1", "Namenjen=" + 1, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
               TextMessage txt = (TextMessage)msg;
               try {
                   
                   String nameCity = txt.getText();
                   
                   TypedQuery<Mesto> query = em.createQuery("SELECT m FROM Mesto m WHERE m.naziv = :naziv", Mesto.class);
                   query.setParameter("naziv", nameCity);
                   Mesto city = query.getSingleResult();
                   
                   TextMessage txt0 = context.createTextMessage();
                   txt0.setText("Vec postoji mesto sa zadatim nazivom");
                   txt0.setIntProperty("Return", 1);
                   producer.send(subsystem1ReturnTopic, txt0);
                   
                   
               } catch (JMSException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }catch (NoResultException e){
                   try {
                       em.getTransaction().begin();
                       Mesto place = new Mesto();
                       place.setNaziv(txt.getText());
                       em.persist(place);
                       em.getTransaction().commit();
                       
                       TextMessage txt1 = context.createTextMessage();
                       txt1.setText("Napravljeno je novo mesto sa nazivom: " + txt.getText());
                       txt1.setIntProperty("Return", 1);
                       producer.send(subsystem1ReturnTopic, txt1);
                   } catch (JMSException ex) {
                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
           }
        });
    }
    
    public static void request2(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "2", "Namenjen=" + 2, false);
        JMSProducer producer = context.createProducer();
        
       
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    //System.out.println("USLO2");
                    TextMessage txtMess = (TextMessage)msg;
                    String name = txtMess.getText().split("%")[0];
                    //System.out.println(name);
                    String email = txtMess.getText().split("%")[1];
                    //System.out.println(email);
                    String born = txtMess.getText().split("%")[2];
                    //System.out.println(born);
                    String gender = txtMess.getText().split("%")[3];
                    //System.out.println(gender);
                    String placeID = txtMess.getText().split("%")[4];
                    //System.out.println(placeID);
                    Mesto place = em.find(Mesto.class, Integer.parseInt(placeID));
                    
                    if(place == null){
                        TextMessage txt0 = context.createTextMessage();
                        txt0.setText("Ne postoji mesto sa zadatim ID-jem; korisnik nije kreiran");
                        txt0.setIntProperty("Return", 2);
                        producer.send(subsystem1ReturnTopic, txt0);
                        return;
                    }
                    Korisnik newUser = new Korisnik();
                    newUser.setEmail(email);
                    newUser.setIme(name);
                    newUser.setGodiste(born);
                    newUser.setPol(gender);
                    newUser.setIDMes(place);
                    
                    em.getTransaction().begin();
                    em.persist(newUser);
                    em.getTransaction().commit();
                    
                    //slanje drugim podsistemima za azuriranje korisnika
                    producer.send(subsystemTalk, txtMess);
                    
                    TextMessage txt1 = context.createTextMessage();
                    txt1.setText("Korisnik je uspesno kreiran");
                    txt1.setIntProperty("Return", 2);
                    producer.send(subsystem1ReturnTopic, txt1);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request3(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "3", "Namenjen=" + 3, false);
        JMSProducer producer = context.createProducer();
        
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String email = txtMess.getText().split("%")[1];
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    if(user == null){
                        TextMessage txt0 = context.createTextMessage();
                        txt0.setText("Ne postoji korisnik ciju email adresu zelite da promenite");
                        txt0.setIntProperty("Return", 3);
                        producer.send(subsystem1ReturnTopic, txt0);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    user.setEmail(email);
                    em.getTransaction().commit();
                    
                    //slanje poruke za promenu email-a drugim podsistemima
                    producer.send(subsystemTalk, txtMess);
                    
                    TextMessage txt1 = context.createTextMessage();
                    txt1.setText("Email adresa za korisnika sa IDKor = " + userID + " je uspesno promenjena");
                    txt1.setIntProperty("Return", 3);
                    producer.send(subsystem1ReturnTopic, txt1);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request4(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "4", "Namenjen=" + 4, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage) msg;
                    String userID = txtMess.getText().split("%")[0];
                    String placeName = txtMess.getText().split("%")[1];
                    
                    TypedQuery<Mesto> query0 = em.createQuery("SELECT m FROM Mesto m WHERE m.naziv = :placeName", Mesto.class);
                    query0.setParameter("placeName", placeName);
                    Mesto place = query0.getSingleResult();
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    if(user == null){
                        TextMessage txt1 = context.createTextMessage();
                        txt1.setText("Ne postoji korisnik sa navedenim ID-jem");
                        txt1.setIntProperty("Return", 4);
                        producer.send(subsystem1ReturnTopic, txt1);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    user.setIDMes(place);
                    em.getTransaction().commit();
                    
                    TextMessage txt2 = context.createTextMessage();
                    txt2.setText("Korisniku IDKor = " + user.getIDKor() + " je promenjeno mesto na " + place.getNaziv());
                    txt2.setIntProperty("Return", 4);
                    producer.send(subsystem1ReturnTopic, txt2);
                 

                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }catch (NoResultException e){
                    try {
                        TextMessage txt0 = context.createTextMessage();
                        txt0.setText("Ne postoji navedeni grad u bazi");
                        txt0.setIntProperty("Return", 4);
                        producer.send(subsystem1ReturnTopic, txt0);
                    } catch (JMSException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        });
    }
    
    public static void request17(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "17", "Namenjen=" + 17, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            
            try {
                //em.getTransaction().begin();
                TypedQuery<Mesto> query = em.createQuery("SELECT m FROM Mesto m", Mesto.class);
                List<Mesto> cities = query.getResultList();
                
                System.out.println("USLO");
                
                ObjectMessage objMsg = context.createObjectMessage((Serializable) cities);
                objMsg.setIntProperty("Return", 17);
                producer.send(subsystem1ReturnTopic, objMsg);
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       
        });
    }
    
    public static void request18(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "18", "Namenjen=" + 18, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            
            try {
                TypedQuery<Korisnik> query = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class);
                List<Korisnik> users = query.getResultList();
                
                ObjectMessage objMsg = context.createObjectMessage((Serializable) users);
                objMsg.setIntProperty("Return", 18);
                producer.send(subsystem1ReturnTopic, objMsg);
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public static void main(String[] args) {
       JMSContext context = factory.createContext();
       context.setClientID("1");
       
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS1Subsystem1PU");
       EntityManager em = emf.createEntityManager();
       
       request1(context, em);
       request2(context, em);
       request3(context,em);
       request4(context, em);
       request17(context, em);
       request18(context, em);
       while(true);
       
       
       
    }
    
}
