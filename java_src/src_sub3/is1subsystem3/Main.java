/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1subsystem3;

import entities.Gledanje;
import entities.Korisnik;
import entities.Ocena;
import entities.OcenaPK;
import entities.Paket;
import entities.Pretplata;
import entities.Video;
import java.io.Serializable;
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
    
    public static Date convert(String timestamp){
        //timestamp = YYYY-MM-DD HH-MM-SS
        String date = timestamp.split(" ")[0];
        String time = timestamp.split(" ")[1];
                    
        int year = Integer.parseInt(date.substring(0, 4)) - 1900;
        int month = Integer.parseInt(date.substring(5,7)) - 1;
        int day = Integer.parseInt(date.substring(8));
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        int second = Integer.parseInt(time.substring(6));
        
        return new Date(year, month, day, hour, minute, second);
    }
    
    public static void request2(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystemTalk, "sub3req2", "Namenjen=" + 2, false);
        
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    //System.out.println("USLO");
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
        JMSConsumer consumer = context.createDurableConsumer(subsystemTalk, "sub3req3", "Namenjen=" + 3, false);
        
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
    
    public static void request6(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystemTalk, "sub3req6", "Namenjen=" + 6, false);
        
        consumer.setMessageListener((Message msg)-> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoName = txtMess.getText().split("%")[0];
                    String videoDur = txtMess.getText().split("%")[1];
                    String userID = txtMess.getText().split("%")[2];
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    
                    em.getTransaction().begin();
                    Video video = new Video();
                    video.setIDKor(user);
                    video.setNaziv(videoName);
                    video.setTrajanje(Integer.parseInt(videoDur));
                    video.setDatumVremePost(new Date());
                    em.persist(video);
                    em.getTransaction().commit();
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request7(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystemTalk, "sub3req7", "Namenjen=" + 7, false);
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoID = txtMess.getText().split("%")[0];
                    String name = txtMess.getText().split("%")[1];
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    
                    em.getTransaction().begin();
                    video.setNaziv(name);
                    em.getTransaction().commit();
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request9(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req9", "Namenjen=" + 9, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String price = txtMess.getText();
                    
                    em.getTransaction().begin();
                    Paket pack = new Paket();
                    pack.setCenaMesec(Integer.parseInt(price));
                    em.persist(pack);
                    em.getTransaction().commit();
                    
                    TextMessage txt = context.createTextMessage("Paket je uspesno napravljen");
                    txt.setIntProperty("Return", 9);
                    producer.send(subsystem1ReturnTopic, txt);
                            
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }
    
    public static void request10(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req10", "Namenjen=" + 10, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String packID = txtMess.getText().split("%")[0];
                    String price = txtMess.getText().split("%")[1];
                    
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 10);
                    
                    Paket pack = em.find(Paket.class, Integer.parseInt(packID));
                    if(pack == null){
                        txt.setText("Ne postoji paket sa zadatim ID-jem");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    pack.setCenaMesec(Integer.parseInt(price));
                    em.getTransaction().commit();
                    
                    txt.setText("Cena paketa ID = " +  packID + " je uspesno promenjena na " + price);
                    producer.send(subsystem1ReturnTopic, txt);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
    }
    
    public static void request11(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req11", "Namenjen=" + 11, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg)->{
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String packID = txtMess.getText().split("%")[1];
                    String timestamp = txtMess.getText().split("%")[2];
                    Date date;
                    if(timestamp.equals("current")){
                        date = new Date();
                    }else{
                        date = convert(timestamp);
                    }
                   
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 11);
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    if(user == null){
                        txt.setText("Ne postoji korisnik za koga zelite da kreirate pretplatu");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    Paket pckt = em.find(Paket.class, Integer.parseInt(packID));
                    if(pckt == null){
                        txt.setText("Ne postoji paket na koji zelite da kreirate pretplatu");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    TypedQuery<Pretplata> query = em.createQuery("SELECT p FROM Pretplata p WHERE p.iDKor.iDKor = :id", Pretplata.class);
                    query.setParameter("id", user.getIDKor());
                    List<Pretplata> subs = query.getResultList();
                    
                    for(int i = 0; i < subs.size(); i++){
                        Date prev = subs.get(i).getDatumVremePoc();
                        int prevMonth = prev.getMonth();
                        Date next = (Date)prev.clone();
                        if(prevMonth == 11){
                            prevMonth = 0;
                            next.setYear(prev.getYear()+1);
                        }else{
                            prevMonth++;
                        }
                        next.setMonth(prevMonth);
                        if(date.compareTo(prev) >= 0 && date.compareTo(next) < 0){
                            txt.setText("Korisniku jos uvek nije istekla prethodna pretplata i ne moze da kreira novu");
                            producer.send(subsystem1ReturnTopic, txt);
                            return;
                        }
                                
                    }
                    
                    em.getTransaction().begin();
                    Pretplata subscription = new Pretplata();
                    subscription.setDatumVremePoc(date);
                    subscription.setIDKor(user);
                    subscription.setIDPak(pckt);
                    subscription.setCena(pckt.getCenaMesec());
                    em.persist(subscription);
                    em.getTransaction().commit();
                    
                    txt.setText("Pretplata je uspesno kreirana");
                    producer.send(subsystem1ReturnTopic, txt);
                    
                    
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request12(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req12", "Namenjen=" + 12, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) ->{
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String videoID = txtMess.getText().split("%")[1];
                    String start = txtMess.getText().split("%")[2];
                    String watched = txtMess.getText().split("%")[3];
                    Date watchDate = new Date();
                    
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 12);
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    if(user == null){
                        txt.setText("Ne postoji korisnik cije gledanje zelite da kreirate");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    if(video == null){
                        txt.setText("Ne postoji video koji zelite da gledate");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    TypedQuery<Pretplata> query = em.createQuery("SELECT p FROM Pretplata p WHERE p.iDKor.iDKor = :id", Pretplata.class);
                    query.setParameter("id", user.getIDKor());
                    List<Pretplata> subs = query.getResultList();
                    
                    boolean hasSub = false;
                    for(int i = 0; i < subs.size(); i++){
                        Date startDate = subs.get(i).getDatumVremePoc();
                        int month = startDate.getMonth();
                        Date endDate = (Date)startDate.clone();
                        if(month == 11){
                            month = 0;
                            endDate.setYear(endDate.getYear()+1);
                        }else{
                            month++;
                        }
                        endDate.setMonth(month);
                        if(watchDate.compareTo(startDate) >= 0 && watchDate.compareTo(endDate) < 0){
                            hasSub = true;
                            break;
                        }
                    }
                    
                    if(!hasSub){
                        txt.setText("Korisnik ne moze da gleda video snimak jer trenutno nema pretplatu");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    int endSecond = Integer.parseInt(start) + Integer.parseInt(watched);
                    if(endSecond > video.getTrajanje()){
                        txt.setText("Deo koji zelite da odgledate nije deo video snimka");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    Gledanje newWatch = new Gledanje();
                    newWatch.setDatumVremePoc(watchDate);
                    newWatch.setIDKor(user);
                    newWatch.setIDVid(video);
                    newWatch.setSekundPoc(Integer.parseInt(start));
                    newWatch.setOdgledanoSek(Integer.parseInt(watched));
                    em.persist(newWatch);
                    em.getTransaction().commit();
                    
                    txt.setText("Gledanje je uspesno kreirano");
                    producer.send(subsystem1ReturnTopic, txt);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request13(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req13", "Namenjen=" + 13, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String videoID = txtMess.getText().split("%")[1];
                    String grade = txtMess.getText().split("%")[2];
                    
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 13);
                    
                    Korisnik user = em.find(Korisnik.class, Integer.parseInt(userID));
                    if(user == null){
                        txt.setText("Korisnik sa zadatim ID-jem ne postoji");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    Video video = em.find(Video.class, Integer.parseInt(videoID));
                    if(video == null){
                        txt.setText("Video sa zadatim ID-jem ne postoji");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    TypedQuery<Ocena> query = em.createQuery("SELECT o FROM Ocena o WHERE o.korisnik = :user AND o.video = :video", Ocena.class);
                    query.setParameter("user", user);
                    query.setParameter("video", video);
                    List<Ocena> gradeList = query.getResultList();
                    
                    TypedQuery<Gledanje> query1 = em.createQuery("SELECT g FROM Gledanje g WHERE g.iDKor = :user AND g.iDVid = :video", Gledanje.class);
                    query1.setParameter("user", user);
                    query1.setParameter("video", video);
                    List<Gledanje> watchList = query1.getResultList();
                    
                    if(watchList.isEmpty()){
                        txt.setText("Ne postoji gledanje video snimka od strane korisnika i ocena se ne moze kreirati");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    if(gradeList.isEmpty()){
                        em.getTransaction().begin();
                        OcenaPK gradePK = new OcenaPK(user.getIDKor(), video.getIDVid());
                        Ocena newGrade = new Ocena(gradePK, Integer.parseInt(grade), new Date());
                        em.persist(newGrade);
                        em.getTransaction().commit();
                        
                        txt.setText("Ocena je uspesno kreirana");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    txt.setText("Vec postoji ocena od strane zadatog korisnika za zadati video snimak");
                    producer.send(subsystem1ReturnTopic, txt);
                            
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request14(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req14", "Namenjen=" + 14, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String videoID = txtMess.getText().split("%")[1];
                    String newGrade = txtMess.getText().split("%")[2];
                    
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 14);
                    
                    TypedQuery<Ocena> query = em.createQuery("SELECT o FROM Ocena o WHERE o.korisnik.iDKor = :user AND o.video.iDVid = :video", Ocena.class);
                    query.setParameter("user", Integer.parseInt(userID));
                    query.setParameter("video", Integer.parseInt(videoID));
                    List<Ocena> gradeList = query.getResultList();
                    
                    if(gradeList.isEmpty()){
                        txt.setText("Ne postoji ocena koju zelite da promenite");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    Ocena grade = gradeList.get(0);
                    em.getTransaction().begin();
                    grade.setOcena(Integer.parseInt(newGrade));
                    em.getTransaction().commit();
                    
                    txt.setText("Ocena je uspesno promenjena");
                    producer.send(subsystem1ReturnTopic, txt);
                    
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request15(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req15", "Namenjen=" + 15, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText().split("%")[0];
                    String videoID = txtMess.getText().split("%")[1];
                    
                    TextMessage txt = context.createTextMessage();
                    txt.setIntProperty("Return", 15);
                    
                    TypedQuery<Ocena> query = em.createQuery("SELECT o FROM Ocena o WHERE o.korisnik.iDKor = :userid AND o.video.iDVid = :videoid", Ocena.class);
                    query.setParameter("userid", Integer.parseInt(userID));
                    query.setParameter("videoid", Integer.parseInt(videoID));
                    List<Ocena> gradeList = query.getResultList();
                    
                    if(gradeList.isEmpty()){
                        txt.setText("Ne postoji ocena koju zelite da obrisete");
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    em.getTransaction().begin();
                    em.remove(gradeList.get(0));
                    em.getTransaction().commit();
                    
                    txt.setText("Ocena je uspesno obrisana");
                    producer.send(subsystem1ReturnTopic, txt);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request16(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createConsumer(subsystemTalk, "Namenjen=" + 16, false);
        
        consumer.setMessageListener((Message msg) -> {
            try {
                TextMessage txtMess = (TextMessage)msg;
                String videoID = txtMess.getText().split("%")[1];
                
                Video video = em.find(Video.class, Integer.parseInt(videoID));
                
                
                em.getTransaction().begin();
                em.remove(video);
                em.getTransaction().commit();
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public static void request22(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req22", "Namenjen=" + 22, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TypedQuery<Paket> query = em.createNamedQuery("Paket.findAll", Paket.class);
                    List<Paket> packs = query.getResultList();
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable)packs);
                    objMsg.setIntProperty("Return", 22);
                    producer.send(subsystem1ReturnTopic, objMsg);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void request23(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req23", "Namenjen=" + 23, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String userID = txtMess.getText();
                    
                    TypedQuery<Pretplata> query = em.createQuery("SELECT p FROM Pretplata p WHERE p.iDKor.iDKor = :id", Pretplata.class);
                    query.setParameter("id", Integer.parseInt(userID));
                    List<Pretplata> subList = query.getResultList();
                    
                    if(subList.isEmpty()){
                        TextMessage txt = context.createTextMessage("Ne postoje pretplate za zadatog korisnika");
                        txt.setIntProperty("Return", 23);
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable) subList);
                    objMsg.setIntProperty("Return", 23);
                    producer.send(subsystem1ReturnTopic, objMsg);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    public static void request24(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req24", "Namenjen=" + 24, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoID = txtMess.getText();
                    
                    TypedQuery<Gledanje> query = em.createQuery("SELECT g FROM Gledanje g WHERE g.iDVid.iDVid = :id", Gledanje.class);
                    query.setParameter("id", Integer.parseInt(videoID));
                    List<Gledanje> watchList = query.getResultList();
                    
                    if(watchList.isEmpty()){
                        TextMessage txt = context.createTextMessage("Ne postoje gledanja za zadati video snimak");
                        txt.setIntProperty("Return", 24);
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable) watchList);
                    objMsg.setIntProperty("Return", 24);
                    producer.send(subsystem1ReturnTopic, objMsg);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    public static void request25(JMSContext context, EntityManager em){
        JMSConsumer consumer = context.createDurableConsumer(subsystem1Topic, "sub3req25", "Namenjen=" + 25, false);
        JMSProducer producer = context.createProducer();
        
        consumer.setMessageListener((Message msg) -> {
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMess = (TextMessage)msg;
                    String videoID = txtMess.getText();
                    
                    TypedQuery<Ocena> query = em.createQuery("SELECT o FROM Ocena o WHERE o.video.iDVid = :id", Ocena.class);
                    query.setParameter("id", Integer.parseInt(videoID));
                    List<Ocena> gradeList = query.getResultList();
                    
                    if(gradeList.isEmpty()){
                        TextMessage txt = context.createTextMessage("Ne postoje ocene za zadati video snimak");
                        txt.setIntProperty("Return", 25);
                        producer.send(subsystem1ReturnTopic, txt);
                        return;
                    }
                    
                    ObjectMessage objMsg = context.createObjectMessage((Serializable) gradeList);
                    objMsg.setIntProperty("Return", 25);
                    producer.send(subsystem1ReturnTopic, objMsg);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    public static void main(String[] args) {
        JMSContext context = factory.createContext();
        context.setClientID("3");
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS1Subsystem3PU");
        EntityManager em = emf.createEntityManager();
        
        request2(context, em);
        request3(context, em);
        request6(context, em);
        request7(context, em);
        request9(context, em);
        request10(context, em);
        request11(context, em);
        request12(context, em);
        request13(context, em);
        request14(context, em);
        request15(context, em);
        request16(context, em);
        request22(context, em);
        request23(context, em);
        request24(context, em);
        request25(context, em);
        
        
       while(true){}
    }
    
}
