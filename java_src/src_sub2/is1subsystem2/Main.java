/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1subsystem2;

import entities.Kategorija;
import entities.Korisnik;
import entities.Video;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    
    public static void request2(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystemTalk, "sub2req2", "Namenjen=" + 2, false);
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    System.out.println("USLO");
                    TextMessage txtMess = (TextMessage)msg;
                    String name = txtMess.getText().split("%")[0];
                    String email = txtMess.getText().split("%")[1];
                    String born = txtMess.getText().split("%")[2];
                    String gender = txtMess.getText().split("%")[3];
                    
                    Korisnik newUser = new Korisnik();
                    newUser.setEmail(email);
                    newUser.setIme(name);
                    newUser.setGodiste(born);
                    newUser.setPol(gender);
                    
                    em.getTransaction().begin();
                    em.persist(newUser);
                    em.getTransaction().commit();
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request3(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystemTalk, "sub2req3", "Namenjen=" + 3, false);
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String email = txtMess.getText().split("%")[1];
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    
                    em.getTransaction().begin();
                    user.setEmail(email);
                    em.getTransaction().commit();
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request5(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req5", "Namenjen=" + 5, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                TextMessage txtMess = (TextMessage)msg;
                try {
                    
                    String name = txtMess.getText();
                    
                    TypedQuery<Kategorija> query = em.createQuery("SELECT k FROM Kategorija k WHERE k.naziv = :naziv", Kategorija.class);
                    query.setParameter("naziv", name);
                    Kategorija cat = query.getSingleResult();
                    
                    TextMessage txt0 = context.createTextMessage();
                    txt0.setText("Vec postoji kategorija sa zadatim nazivom");
                    txt0.setIntProperty("Return", 5);
                    producer.send(subsystem1ReturnTopic, txt0);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }catch (NoResultException e){
                    try {
                        em.getTransaction().begin();
                        Kategorija newCat = new Kategorija();
                        newCat.setNaziv(txtMess.getText());
                        em.persist(newCat);
                        em.getTransaction().commit();
                        
                        TextMessage txt1 = context.createTextMessage();
                        txt1.setText("Napravljena je kategorija sa nazivom: " + txtMess.getText());
                        txt1.setIntProperty("Return", 5);
                        producer.send(subsystem1ReturnTopic, txt1);
                    } catch (JMSException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    public static void request6(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req6", "Namenjen=" + 6, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoName = txtMess.getText().split("%")[0];
                    String videoDur = txtMess.getText().split("%")[1];
                    String userID = txtMess.getText().split("%")[2];
                    System.out.println(txtMess.getText());
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    
                    if(user == null){
                        TextMessage txt0 = context.createTextMessage("Ne postoji korisnik koji zeli da kreira video snimak");
                        txt0.setIntProperty("Return", 6);
                        producer.send(subsystem1ReturnTopic, txt0);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    Video video = new Video();
                    video.setIDKor(user);
                    video.setNaziv(videoName);
                    video.setTrajanje(Integer.parseInt(videoDur));
                    video.setDatumVremePost(new Date());
                    em.persist(video);
                    em.getTransaction().commit();
                    
                    //slanje podsistemu3
                    producer.send(subsystemTalk, txtMess);
                    
                    TextMessage txt1 = context.createTextMessage("Video snimak je uspesno kreiran");
                    txt1.setIntProperty("Return", 6);
                    producer.send(subsystem1ReturnTopic, txt1);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request7(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createConsumer(subsystem1Topic, "Namenjen=" + 7, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoID = txtMess.getText().split("%")[0];
                    String name = txtMess.getText().split("%")[1];
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    
                    if(video == null){
                        TextMessage txt0 = context.createTextMessage("Video sa zadatim ID-jem ne postoji u bazi");
                        txt0.setIntProperty("Return", 7);
                        producer.send(subsystem1ReturnTopic, txt0);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    video.setNaziv(name);
                    em.getTransaction().commit();
                    
                    //slanje podsistemu3
                    producer.send(subsystemTalk, txtMess);
                    
                    TextMessage txt1 = context.createTextMessage("Naziv video snimka ID = " + videoID + " je uspesno promenjen");
                    txt1.setIntProperty("Return", 7);
                    producer.send(subsystem1ReturnTopic, txt1);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request8(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req8", "Namenjen=" + 8, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String catID = txtMess.getText().split("%")[0];
                    String videoID = txtMess.getText().split("%")[1];
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    if(video == null){
                        TextMessage txt0 = context.createTextMessage("Ne postoji video kome zelite da dodate kategoriju");
                        txt0.setIntProperty("Return", 8);
                        producer.send(subsystem1ReturnTopic, txt0);
                        return;
                    }
                    
                    Kategorija cat = em.find(Kategorija.class, Integer.parseInt(catID));
                    if(cat == null){
                        TextMessage txt1 = context.createTextMessage("Ne postoji kategorija koju zelite da dodate video snimku");
                        txt1.setIntProperty("Return", 8);
                        producer.send(subsystem1ReturnTopic, txt1);
                        return;
                    }
                    
                    List<Video> videoList = cat.getVideoList();
                    for(int i = 0; i < videoList.size(); i++){
                        if(videoList.get(i) == video){
                            TextMessage txt3 = context.createTextMessage("Duplikat u bazi");
                            txt3.setIntProperty("Return", 8);
                            producer.send(subsystem1ReturnTopic, txt3);
                            return;
                        }
                    }
                    
                    em.getTransaction().begin();
                    videoList.add(video);
                    em.getTransaction().commit();
                    
                    TextMessage txt2 = context.createTextMessage("Video snimku ID = " + videoID + " je uspesno dodata kategorija ID = " + catID);
                    txt2.setIntProperty("Return", 8);
                    producer.send(subsystem1ReturnTopic, txt2);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request16(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req16", "Namenjen=" + 16, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 16);
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String videoID = txtMess.getText().split("%")[1];
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    if(user == null){
                        txt.setText("Ne postoji zadati korisnik");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    if(video == null){
                        txt.setText("Ne postoji video koji zelite da obrisete");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    Korisnik owner = video.getIDKor();
                    if(owner == user){
                        em.getTransaction().begin();
                        em.remove(video);
                        em.getTransaction().commit();
                        
                        //slanje podsistemu3
                        producer.send(subsystemTalk, txtMess);
                        
                        txt.setText("Video je uspesno obrisan od strane vlasnika");
                        producer.send(subsystem1ReturnTopic, txt);
                    }else{
                        txt.setText("Korisnik koji zeli da obrise video nije njegov vlasnik; neuspesno brisanje");
                        producer.send(subsystem1ReturnTopic, txt);
                    }
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    public static void request19(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req19", "Namenjen=" + 19, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TypedQuery<Kategorija> query = em.createNamedQuery("Kategorija.findAll", Kategorija.class);
                    List<Kategorija> catList = query.getResultList();
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable) catList);
                    objMsg.setIntProperty("Return", 19);
                    producer.send(subsystem1ReturnTopic, objMsg);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request20(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req20", "Namenjen=" + 20, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TypedQuery<Video> query = em.createNamedQuery("Video.findAll", Video.class);
                    List<Video> videoList = query.getResultList();
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable) videoList);
                    objMsg.setIntProperty("Return", 20);
                    producer.send(subsystem1ReturnTopic, objMsg);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request21(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub2req21", "Namenjen=" + 21, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) ->{
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoID = txtMess.getText();
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    if(video == null){
                        TextMessage txt = context.createTextMessage("Ne postoji video cije kategorije zelite da vidite");
                        txt.setIntProperty("Return", 21);
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    TypedQuery<Kategorija> query = em.createQuery("SELECT v FROM Video v JOIN FETCH v.kategorijaList WHERE v.iDVid = :video", Kategorija.class);
                    query.setParameter("video", Integer.parseInt(videoID));
                    List<Kategorija> catList = video.getKategorijaList();
                    
                    /*for(Kategorija cat: catList){
                        System.out.println(cat);
                    }*/
                    
                    if(catList.isEmpty()){
                        TextMessage txt = context.createTextMessage("Video ne pripada nijednoj kategoriji");
                        txt.setIntProperty("Return", 21);
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable)catList);
                    objMsg.setIntProperty("Return", 21);
                    producer.send(subsystem1ReturnTopic, objMsg);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
    }
    
    
    public static void main(String[] args) {
        JMSContext context = factory.createContext();
        context.setClientID("2");
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS1Subsystem2PU");
        EntityManager em = emf.createEntityManager();
        
        request2(context, em);
        request3(context, em);
        request5(context, em);
        request6(context, em);
        request7(context, em);
        request8(context, em);
        request16(context, em);
        request19(context, em);
        request20(context, em);
        request21(context, em);
        while(true);
    }
    
}
