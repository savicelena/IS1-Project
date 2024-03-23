/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1client;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author HP
 */
public class IS1Client {

    
    public static void printResponse(String response, String del1, String del2){
        String start = response.split(del1)[0];
        System.out.println(start);
        System.out.println(del1);
        String data = response.split(del1)[1];
        String[] array = data.split(del2);
        int i = 0;
        for(String elem: array){
            System.out.print(elem);
            if(++i != array.length){
                System.out.println(del2);
            }     
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            Scanner input = new Scanner(System.in);
            
            String uri1 = "http://localhost:8080/IS1Server/api/podsistem1";
            String uri2 = "http://localhost:8080/IS1Server/api/podsistem2";
            String uri3 ="http://localhost:8080/IS1Server/api/podsistem3";
            
            boolean done = false;
            
            while(!done){
                System.out.println("Izaberite jednu od ponudjenih opcija: ");
                System.out.println("1. Kreiranje grada");
                System.out.println("2. Kreiranje korisnika");
                System.out.println("3. Promena email adrese za korisnika");
                System.out.println("4. Promena mesta za korisnika");
                System.out.println("5. Kreiranje kategorije");
                System.out.println("6. Kreiranje video snimka");
                System.out.println("7. Promena naziva video snimka");
                System.out.println("8. Dodavanje kategorije video snimku");
                System.out.println("9. Kreiranje paketa");
                System.out.println("10. Promena mesecne cene za paket");
                System.out.println("11. Kreiranje pretplate korisnika na paket");
                System.out.println("12. Kreiranje gledanja video snimka od strane korisnika");
                System.out.println("13. Kreiranje ocene korisnika za video snimak");
                System.out.println("14. Menjanje ocene korisnika za video snimak");
                System.out.println("15. Brisanje ocene korisnika za video snimak");
                System.out.println("16. Brisanje video snimka od strane korisnika koji ga je kreirao");
                System.out.println("17. Dohvatanje svih mesta");
                System.out.println("18. Dohvatanje svih korisnika");
                System.out.println("19. Dohvatanje svih kategorija");
                System.out.println("20. Dohvatanje svih video snimaka");
                System.out.println("21. Dohvatanje kategorija za odredjeni video snimak");
                System.out.println("22. Dohvatanje svih paketa");
                System.out.println("23. Dohvatanje svih pretplata za korisnika");
                System.out.println("24. Dohvatanje svih gledanja za video snimak");
                System.out.println("25. Dohvatanje svih ocena za video snimak");
                System.out.println("26. Kraj");
                String option = input.nextLine();
                
                switch(option){
                    case "1.":
                        System.out.println("Unesite ime grada");
                        String name = input.nextLine();
                        int blank1 = name.indexOf(" ");
                        if(blank1 != -1){
                            name = name.replace(" ", "%20");
                        }
                        String request1 = uri1 + "/" + name;
                        HttpPost post1 = new HttpPost(request1);
                        HttpResponse response1 = client.execute(post1);
                        String res1 = EntityUtils.toString(response1.getEntity());
                        System.out.println(res1);
                        break;
                    case "2.":
                        System.out.println("Unesite ime korisnika: ");
                        String userName = input.nextLine();
                        int blank2 = userName.indexOf(" ");
                        if(blank2 != -1){
                            userName = userName.replace(" ", "%20");
                        }
                        System.out.println("Unesite email korisnika: ");
                        String userEmail = input.nextLine();
                        System.out.println("Unesite godiste korisnika: ");
                        String userBorn = input.nextLine();
                        System.out.println("Unesite pol korisnika: ");
                        String userGender = input.nextLine();
                        System.out.println("Unesite ID mesta iz kojeg je korisnik: ");
                        String placeID = input.nextLine();
                        
                        String request2 = uri1 + "/?name=" + userName + "&email=" + userEmail + "&born=" + userBorn + "&gender=" + userGender + "&place=" + placeID;
                        HttpPost post2 = new HttpPost(request2);
                        HttpResponse response2 = client.execute(post2);
                        String res2 = EntityUtils.toString(response2.getEntity());
                        System.out.println(res2);
                        break;
                    case "3.":
                        System.out.println("Unesite ID korisnika ciji email zelite da promenite: ");
                        String userID3 = input.nextLine();
                        System.out.println("Unesite novi email: ");
                        String userEmail3 = input.nextLine();
                        
                        String request3 = uri1 + "/email/?id=" + userID3 + "&email=" + userEmail3;
                        HttpPut put3 = new HttpPut(request3);
                        HttpResponse response3 = client.execute(put3);
                        String res3 = EntityUtils.toString(response3.getEntity());
                        System.out.println(res3);
                        break;
                        
                    case "4.":
                        System.out.println("Unesite ID korisnika cije mesto zelite da promenite: ");
                        String userID = input.nextLine();
                        System.out.println("Unesite naziv mesta koje zelite da dodelite korisniku");
                        String placeName = input.nextLine();
                        int blank4 = placeName.indexOf(" ");
                        if(blank4 != -1){
                            placeName = placeName.replace(" ", "%20");
                        }
                        String request4 = uri1 + "/?userID=" + userID + "&placeName=" + placeName;
                        HttpPut put4 = new HttpPut(request4);
                        HttpResponse response4 = client.execute(put4);
                        String res4 = EntityUtils.toString(response4.getEntity());
                        System.out.println(res4);
                        break;
                    case "5.":
                        System.out.println("Unesite naziv kategorije: ");
                        String catName5 = input.nextLine();
                        int blank5 = catName5.indexOf(" ");
                        if(blank5 != -1){
                            catName5 = catName5.replace(" ", "%20");
                        }
                        String request5 = uri2 + "/" + catName5;
                        HttpPost post5 = new HttpPost(request5);
                        HttpResponse response5 = client.execute(post5);
                        String res5 = EntityUtils.toString(response5.getEntity());
                        System.out.println(res5);
                        break;
                    case "6.":
                        System.out.println("Unesite naziv video snimka koji kreirate: ");
                        String videoName6 = input.nextLine();
                        int blank6 = videoName6.indexOf(" ");
                        if(blank6 != -1){
                            videoName6 = videoName6.replace(" ", "%20");
                        }
                        System.out.println("Unesite trajanje video snimka koji kreirate: ");
                        String videoDur6 = input.nextLine();
                        System.out.println("Unesite ID korisnika koji postavlja video snimak: ");
                        String userID6 = input.nextLine();
                        
                        String request6 = uri2 + "/video/?name=" + videoName6 + "&duration=" + videoDur6 + "&user=" + userID6;
                        HttpPost post6 = new HttpPost(request6);
                        HttpResponse response6 = client.execute(post6);
                        String res6 = EntityUtils.toString(response6.getEntity());
                        System.out.println(res6);
                        break;
                    case "7.":
                        System.out.println("Unesite ID video snimka ciji naziv zelite da promenite: ");
                        String videoID7 = input.nextLine();
                        System.out.println("Unesite novi naziv: ");
                        String videoName7 = input.nextLine();
                        int blank7 = videoName7.indexOf(" ");
                        if(blank7 != -1){
                            videoName7 = videoName7.replace(" ", "%20");
                        }
                        
                        String request7 = uri2 + "/" + videoID7 + "/?name=" + videoName7;
                        HttpPut pu7 = new HttpPut(request7);
                        HttpResponse response7 = client.execute(pu7);
                        String res7 = EntityUtils.toString(response7.getEntity());
                        System.out.println(res7);
                        break;
                    case "8.":
                        System.out.println("Unesite ID kategorije koju zelite da dodate video snimku: ");
                        String catID = input.nextLine();
                        System.out.println("Unesite ID video snimka kome zelite da dodate kategoriju: ");
                        String videoID = input.nextLine();
                        
                        String request8 = uri2 + "/videokat/?catID=" + catID + "&videoID=" + videoID;
                        HttpPost post8 = new HttpPost(request8);
                        HttpResponse response8 = client.execute(post8);
                        String res8 = EntityUtils.toString(response8.getEntity());
                        System.out.println(res8);
                        break;
                    case "9.":
                        System.out.println("Unesite cenu paketa: ");
                        String price9 = input.nextLine();
                        
                        String request9 = uri3 + "/paket/" + price9;
                        HttpPost post9 = new HttpPost(request9);
                        HttpResponse response9 = client.execute(post9);
                        String res9 = EntityUtils.toString(response9.getEntity());
                        System.out.println(res9);
                        break;
                    case "10.":
                        System.out.println("Unesite ID paketa ciju mesecnu cenu zelite da promenite: ");
                        String packID10 = input.nextLine();
                        System.out.println("Unesite novu cenu paketa: ");
                        String price10 = input.nextLine();
                        
                        String request10 = uri3 + "/?id=" + packID10 + "&price=" + price10;
                        HttpPut put10 = new HttpPut(request10);
                        HttpResponse response10 = client.execute(put10);
                        String res10 = EntityUtils.toString(response10.getEntity());
                        System.out.println(res10);
                        break;
                    case "11.":
                        System.out.println("1. Pretplata od odredjenog datuma");
                        System.out.println("2.Pretplata od danasnjeg datuma");
                        System.out.println("Izaberite jednu od ponudjenih opcija: ");
                        String opt= input.nextLine();
                        
                        String timestamp11 = "current";
                        if(opt.equals("1.")){
                            System.out.println("Unesite datum pocetka pretplate u formatu YYYY-MM-DD: ");
                            String date11 = input.nextLine();
                            System.out.println("Unesite vreme pocetka pretplate u formatu HH:MM:SS: ");
                            String time11 = input.nextLine();
                            timestamp11 = date11 + "%20" + time11;
                        }
                        
                        System.out.println("Unesite ID korisnika za koji se kreira pretplata: ");
                        String userID11 = input.nextLine();
                        System.out.println("Unesite ID paketa na koji se korisnik pretplacuje: ");
                        String packID11 = input.nextLine();
                        
                        
                        String request11 = uri3 + "/pretplata/?userID=" + userID11 + "&packID=" + packID11 + "&date=" + timestamp11;
                        HttpPost post11 = new HttpPost(request11);
                        HttpResponse response11 = client.execute(post11);
                        String res11 = EntityUtils.toString(response11.getEntity());
                        System.out.println(res11);
                        break;
                    case "12.":
                        System.out.println("Unesite ID korisnika koji gleda snimak: ");
                        String userID12 = input.nextLine();
                        System.out.println("Unesite ID video snimka: ");
                        String videoID12 = input.nextLine();
                        System.out.println("Unesite pocetnu sekundu gledanja: ");
                        String second12 =input.nextLine();
                        System.out.println("Unesite koliko sekundi je odgledano: ");
                        String watched12 = input.nextLine();
                        
                        String request12 = uri3 + "/gledanje/?userID=" + userID12 + "&videoID=" + videoID12 + "&start=" + second12 + "&watched=" + watched12;
                        HttpPost post12 = new HttpPost(request12);
                        HttpResponse response12 = client.execute(post12);
                        String res12 = EntityUtils.toString(response12.getEntity());
                        System.out.println(res12);
                        break;
                    case "13.":
                        System.out.println("Unesite ID korisnika koji daje ocenu: ");
                        String userID13 = input.nextLine();
                        System.out.println("Unesite ID video snimka koji se ocenjuje: ");
                        String videoID13 = input.nextLine();
                        String grade13;
                        do{
                           System.out.println("Unesite ocenu [1-5]: ");
                           grade13 = input.nextLine();
                        }while (Integer.parseInt(grade13) < 1 || Integer.parseInt(grade13) > 5);
                        
                        String request13 = uri3 + "/ocena/?userID=" + userID13 + "&videoID=" + videoID13 + "&grade=" + grade13;
                        HttpPost post13 = new HttpPost(request13);
                        HttpResponse response13 = client.execute(post13);
                        String res13 = EntityUtils.toString(response13.getEntity());
                        System.out.println(res13);
                        break;
                    case "14.":
                        System.out.println("Unesite ID korisnika koji menja ocenu: ");
                        String userID14 = input.nextLine();
                        System.out.println("Unesite ID video snimka kome se menja ocena: ");
                        String videoID14 = input.nextLine();
                        String grade14;
                        do{
                           System.out.println("Unesite ocenu [1-5]: ");
                           grade14 = input.nextLine();
                        }while (Integer.parseInt(grade14) < 1 || Integer.parseInt(grade14) > 5);
                        
                        String request14 = uri3 + "/promenaocene/?userID=" + userID14 + "&videoID=" + videoID14 + "&grade=" + grade14;
                        HttpPut put14 = new HttpPut(request14);
                        HttpResponse response14 = client.execute(put14);
                        String res14 = EntityUtils.toString(response14.getEntity());
                        System.out.println(res14);
                        break;
                    case "15.":
                        System.out.println("Unesite ID korisnika cija se ocena brise: ");
                        String userID15 = input.nextLine();
                        System.out.println("Unesite ID video snimka za koji se brise ocena: ");
                        String videoID15 = input.nextLine();
                        
                        String request15 = uri3 + "/brisanjeocene/?userID=" + userID15 + "&videoID=" + videoID15;
                        HttpDelete delete15 = new HttpDelete(request15);
                        HttpResponse response15 = client.execute(delete15);
                        String res15 = EntityUtils.toString(response15.getEntity());
                        System.out.println(res15);
                        break;
                        
                    case "16.":
                        System.out.println("Unesite ID korisnika koji zeli da obrise video snimak: ");
                        String userID16 = input.nextLine();
                        System.out.println("Unesite ID video snimka za brisanje: ");
                        String videoID16 = input.nextLine();
                        
                        String request16 = uri2 + "/videobrisanje/?userID=" + userID16 + "&videoID=" + videoID16;
                        HttpDelete delete16 = new HttpDelete(request16);
                        HttpResponse response16 = client.execute(delete16);
                        String res16 = EntityUtils.toString(response16.getEntity());
                        System.out.println(res16);
                        break;
                        
                    case "17.":
                        String request17 = uri1 + "/mesta"; 
                        HttpGet get17 = new HttpGet(request17);
                        HttpResponse response17 = client.execute(get17);
                        
                       System.out.println(response17.getStatusLine().getStatusCode());
                       
                       String res17 = EntityUtils.toString(response17.getEntity());
                       printResponse(res17, "<mestoes>", "</mesto>");
                        break;
                    case "18.":
                        String request18 = uri1 + "/korisnici";
                        HttpGet get18 = new HttpGet(request18);
                        HttpResponse response18 = client.execute(get18);
                        
                        String res18 = EntityUtils.toString(response18.getEntity());
                        printResponse(res18, "<korisniks>", "</korisnik>");
                        break;
                    case "19.":
                        String request19 = uri2 + "/kategorije";
                        HttpGet get19 = new HttpGet(request19);
                        HttpResponse response19 = client.execute(get19);
                        
                        String res19 = EntityUtils.toString(response19.getEntity());
                        printResponse(res19, "<kategorijas>", "</kategorija>");
                        //System.out.println(res19);
                        break;
                    case "20.":
                        String request20 = uri2 + "/videosnimci";
                        HttpGet get20 = new HttpGet(request20);
                        HttpResponse response20 = client.execute(get20);
                        
                        String res20 = EntityUtils.toString(response20.getEntity());
                        printResponse(res20, "<videoes>", "</video>");
                        break;
                    case "21.":
                        System.out.println("Unesite ID video snimka cije kategorije zelite da vidite: ");
                        String videoID21 = input.nextLine();
                        String request21 = uri2 + "/kategorijevidea/" + videoID21;
                        HttpGet get21 = new HttpGet(request21);
                        HttpResponse response21 = client.execute(get21);
                        
                        String res21 = EntityUtils.toString(response21.getEntity());
                        String[] catArr = res21.split("<kategorijas>");
                        if(catArr.length == 1){
                            System.out.println(res21);
                            break;
                        }
                        printResponse(res21, "<kategorijas>", "</kategorija>");
                        break;
                    case "22.":
                        String request22 = uri3 + "/paketi";
                        HttpGet get22 = new HttpGet(request22);
                        HttpResponse response22 = client.execute(get22);
                        
                        String res22 = EntityUtils.toString(response22.getEntity());
                        printResponse(res22, "<pakets>", "</paket>");
                        break;
                    case "23.":
                        System.out.println("Unesite ID korisnika cije pretplate zelite da vidite: ");
                        String userID23 = input.nextLine();
                        
                        String request23 = uri3 + "/pretplata/?id=" + userID23;
                        HttpGet get23 = new HttpGet(request23);
                        HttpResponse response23 = client.execute(get23);
                        
                        String res23 = EntityUtils.toString(response23.getEntity());
                        String[] subArr = res23.split("<pretplatas>");
                        if(subArr.length == 1){
                            System.out.println(res23);
                            break;
                        }
                        printResponse(res23, "<pretplatas>", "</pretplata>");
                        break;
                    case "24.":
                        System.out.println("Unesite ID video snimka cija gledanja zelite da vidite: ");
                        String videoID24 = input.nextLine();
                        
                        String request24 = uri3 + "/gledanje/?videoID=" + videoID24;
                        HttpGet get24 = new HttpGet(request24);
                        HttpResponse response24 = client.execute(get24);
                        
                        String res24 = EntityUtils.toString(response24.getEntity());
                        String[] watchArr = res24.split("<gledanjes>");
                        if(watchArr.length == 1){
                            System.out.println(res24);
                            break;
                        }
                        printResponse(res24, "<gledanjes>", "</gledanje>");
                        break;
                    case "25.":
                        System.out.println("Unesite ID video snimka cije ocene zelite da vidite: ");
                        String videoID25 = input.nextLine();
                        
                        String request25 = uri3 + "/ocena/?videoID=" + videoID25;
                        HttpGet get25 = new HttpGet(request25);
                        HttpResponse response25 = client.execute(get25);
                        
                        String res25 = EntityUtils.toString(response25.getEntity());
                        String[] gradeArr = res25.split("<ocenas>");
                        if(gradeArr.length == 1){
                            System.out.println(res25);
                            break;
                        }
                        printResponse(res25, "<ocenas>", "</ocena>");
                        break;
                        
                    case "26.":
                        done = true;
                        break;
                }
            }
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(IS1Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
