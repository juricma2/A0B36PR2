/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralkapr2;

import semestralkapr2.Server;
import static semestralkapr2.Server.poslat;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Client extends javax.swing.JFrame implements Runnable{
    //deklarace promennych
    private Socket socket;
    private String klic ="a";
    private static ObjectOutputStream out;
    private ObjectInputStream in;
    private String IP;
    private int PORT;
    //konstruktor
    public Client(String IP,int PORT) throws IOException {
        this.IP=IP;
        this.PORT=PORT;
        initComponents(); 
    }
    //metody na kodovani a dekodovani
    private String koduj(String x, String klic){
        //promenne
        String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .,!-?/_";
        String text = "";
        int pomocna = 0;
        char pismenoKlice;
        for (int i = 0; i < x.length(); i++) {
            
            if (pomocna == klic.length()){
                pomocna = 0;
            }
            pismenoKlice = klic.charAt(pomocna);
            char pismeno = x.charAt(i);
            int diference = 0;
            for (int j = 0; j < 60; j++) {
                if (pismenoKlice==abeceda.charAt(j)) {
                    diference = j;//zjisteni rozdilu mezi skutecnym a kodovanym pismenem
                } 
            }
            for (int j = 0; j < 60; j++) {
                //nahrazeni skutecneho pismene kodovanim
                if (pismeno == abeceda.charAt(j)) {
                    if ((j+diference)>59) {
                        text=text+(abeceda.charAt((j+diference)-60));
                    
                    } else {
                        text=text+(abeceda.charAt(j+diference));          
                    }         
                }
            }
            pomocna++;
        }
        return text;
    }
    private String dekoduj(String x, String klic){
        String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .,!-?/_";
        String text = "";
        int pomocna = 0;
        char pismenoKlice;
        for (int i = 0; i < x.length(); i++) {
            
            if (pomocna == klic.length()){
                pomocna = 0;
            }
            pismenoKlice = klic.charAt(pomocna);
            char pismeno = x.charAt(i);
            int diference = 0;
            for (int j = 0; j < 60; j++) {
                if (pismenoKlice==abeceda.charAt(j)) {//zjisteni rozdilu mezi skutecnym a kodovanym pismenem
                    diference = j;
                } 
            }
            for (int j = 0; j < 60; j++) {//nahrazeni kodovaneho pismene skutecnym
                if (pismeno == abeceda.charAt(j)) {
                    if ((j-diference)<0) {
                        text=text+(abeceda.charAt((j-diference)+60));
                    
                    } else {
                        text=text+(abeceda.charAt(j-diference));          
                    }         
                }
            }
            pomocna++;
        }
        return text;
    }
    //format upozorneni
    private void inform(String message){
        jTextArea1.setText(jTextArea1.getText()+"\n"+message);//vypise text do horniho textarea
    }
    private void alert(String message) {
        JOptionPane.showMessageDialog(null, message);//vyhodi okno se zpravou
    }
    //metody pro nastaveni
    private void nastav() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());//nastaveni vystupu
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());//nastaveni vstupu
        inform("Streams are now setup!");
 
 
    }
    private void pripojit() throws IOException {
        inform("Attempting connection...");
        try {//pokuosi se vytvorit pripojeni s IP adresou a portem
            socket = new Socket(InetAddress.getByName(IP), PORT);
        }catch(Exception e){//pri spatnem portu, IP, nebo jine chybe
            alert("Connection failed");
            System.exit(0);
        }
        inform("Connected to server" + socket.getInetAddress().getHostName());
    }
    private void konec() {
        inform("Closing Connection...");
 
        try {//zavreni pripojeni
            out.close();
            in.close();
            socket.close();
        } catch (IOException ioException) {
            System.exit(0);
        }
 
    }
    private void prubeh() throws IOException {
        String message = "You are now connected!";
        inform(message);
        String zprava;
 
        do {
            try {   
                zprava = (String) in.readObject();//precteni zpravy ze vstupu
                String pomocna = dekoduj(zprava,klic);
                inform("Server said: "+pomocna);
                jTextArea1.setCaretPosition(jTextArea1.getText().length());//presunuti kurzoru na konec
                
            } catch (Exception e) {
            }
            
        } while (!message.equals("CLIENT - END"));//nekonecna smycka
    }
    public void spustit() {
        try {
            setTitle("Client");
            pripojit();//pripojeni
            nastav();//nastaveni vstupu a vystupu
            prubeh();//prije a odesilani zprav
        } catch (EOFException eofException) {
            alert("Server terminated connection");//kdyz server skonci
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            konec();
        }
    }
    //odeslani
    public static void poslat(String text) throws IOException {
        
        out.writeObject(text);
        out.flush();//samotne odeslani textu a "splachnuti" vystupu      
 
    }
    //prevod do puzitelneho formatu textu
    private String prevod (String x){
        
        String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .,!-?/";
        String pomocna = "";
        String y="";
        if (x.charAt(0)=='\n') {//kdyz je na zacatku enter, tak se text posune
            for (int i = 1; i < x.length(); i++) {
                y=y+x.charAt(i);
            }
        } else {
            y=x;
        }
        boolean ok = false;
        for (int i = 0; i < y.length(); i++) {//vymazani nespravnych znaku
            for (int j = 0; j < 59; j++) {
                if (y.charAt(i)==abeceda.charAt(j)) {
                    pomocna = pomocna+y.charAt(i);
                    ok=true;
                }
                
            }
            if (ok==false) {
                pomocna=pomocna+"_";//pokud znak neznam, nahradim ho podtrzitkem
            }
            ok=false;
        }
        return pomocna;
    }
    //formatovani a priprava k odeslani
    private void odeslat() throws IOException{
        String pomocna = prevod(jTextArea2.getText());//prevedeni nespravneho vstupu na spravny
        jTextArea1.setText(jTextArea1.getText()+"\n"+"You said: "+pomocna);//co jsem ja rekl
        
        jTextArea2.setText("");
        poslat(koduj(pomocna,klic));//zakodovani textu a odeslani pomoci metody poslat
    }
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jButton2.setText("Send");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setAutoscrolls(false);
        jTextArea2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextArea2InputMethodTextChanged(evt);
            }
        });
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea2);

        jButton3.setText("Leave");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPasswordField1.setText("heslo");

        jButton1.setText("Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Client...");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //tlacitko odeslat
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            odeslat();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    //enter - dela to same, co tlacitko odeslat
    private void jTextArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String pomocna="";
            for (int i = 0; i < (jTextArea2.getText().length()); i++) {
                pomocna=pomocna+jTextArea2.getText().charAt(i);
            }
            
            jTextArea2.setText(null);//vymazani napsaneho
            jTextArea2.setCaretPosition(0);//presun kurzoru
            jTextArea1.setText(jTextArea1.getText()+"\n"+"You said: "+prevod(pomocna));//co ja rekl
                try {
                    poslat(koduj(prevod(pomocna),klic));//odeslani
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea2KeyPressed

    private void jTextArea2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextArea2InputMethodTextChanged
        
    }//GEN-LAST:event_jTextArea2InputMethodTextChanged
    //tlacitko ukonceni - leave
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            poslat("BYEBYE");//text, ktery zavre server
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        konec();
        System.exit(0);        // vypnout program
    }//GEN-LAST:event_jButton3ActionPerformed
    //nastaveni hesla
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        klic="";
        int pomocna = jPasswordField1.getPassword().length;
        for (int i = 0; i < pomocna; i++) {
            klic=klic+jPasswordField1.getPassword()[i];//ulozeni klice do promenne
        }
        klic=prevod(klic);//prevod nevhodneho hesla na pouzitelne
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[],String IP,int PORT) throws IOException {//hlavni metoda
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Client client;
        client = new Client(IP,PORT);//vytvoreni klienta, je zadana IP a port
        client.setVisible(true);
        client.spustit();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {//provede se pri spusteni vlakna
        try {
            main(null, IP, PORT);//provede hlavni metodu, 
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
