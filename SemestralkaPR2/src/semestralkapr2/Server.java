/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralkapr2;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Server extends javax.swing.JFrame {
    private Socket socket;
    private String klic="a";
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private ServerSocket server;
    
    //konstuktor
    public Server() throws IOException {
        initComponents();  
    }
    //metody na kodovani a dekodovani
    private String koduj(String x, String klic){
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
                    diference = j;
                } 
            }
            for (int j = 0; j < 60; j++) {
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
                if (pismenoKlice==abeceda.charAt(j)) {
                    diference = j;
                } 
            }
            for (int j = 0; j < 60; j++) {
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
    //prevod textu do pouzitelneho tvaru, napriklad pokud pouziva nepovolene znaky
    private String prevod (String x){
        
        String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .,!-?/";
        String pomocna = "";
        String y="";
        if (x.charAt(0)=='\n') {
            for (int i = 1; i < x.length(); i++) {
                y=y+x.charAt(i);
            }
        } else {
            y=x;
        }
        boolean ok = false;
        for (int i = 0; i < y.length(); i++) {
            for (int j = 0; j < 59; j++) {
                if (y.charAt(i)==abeceda.charAt(j)) {
                    pomocna = pomocna+y.charAt(i);
                    ok=true;
                }
                
            }
            if (ok==false) {
                pomocna=pomocna+"_";
            }
            ok=false;
        }
        return pomocna;
    }
    //formatovni a priprava k odeslani
    private void odeslat() throws IOException{
        String pomocna = prevod(jTextArea2.getText());
        String kod = koduj(pomocna,klic);
        jTextArea1.setText(jTextArea1.getText()+"\n"+"You said: "+pomocna);
        jTextArea2.setText("");
        poslat(kod);
    }
    //format upozorneni
    private void alert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    private void inform(String message){
        jTextArea1.setText(jTextArea1.getText()+"\n"+message);
    }
    //metody pro spravny beh sitovani
    public void spustit() {
        try {
            setTitle("Server");
            server = new ServerSocket(2187, 4);
            while (true) {
                
                try {
                    
                    cekani();
                    nastav();
                    prubeh();
                } catch (EOFException eofException) {
                    alert("Client ended the connection!");
                } finally {
                    konec();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void nastav() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        inform("Streams are now setup!");
 
 
    }
    private void cekani() throws IOException {
        socket = server.accept();
        inform("Now connected to " + socket.getInetAddress().getHostName());
    }
    private void konec() {
        inform("Closing Connection...");
        try {
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
                zprava = (String)in.readObject();
                String pomocna = dekoduj(zprava,klic);
                inform("Client said: "+pomocna);
                jTextArea1.setCaretPosition(jTextArea1.getText().length());
            } catch (Exception e) {
            }
            
        } while (!message.equals("CLIENT - END"));
    }
    //odeslani textu
    public static void poslat(String text) throws IOException {
        out.writeObject(text);
        out.flush();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPasswordField1.setText("Heslo");

        jButton1.setText("Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Server...");
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setToolTipText("");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setAutoscrolls(false);
        jTextArea2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextArea2CaretPositionChanged(evt);
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

        jButton2.setText("Send");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Leave");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            odeslat();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String pomocna="";
            for (int i = 0; i < (jTextArea2.getText().length()); i++) {
                pomocna=pomocna+jTextArea2.getText().charAt(i);
            }
            jTextArea2.setText(null);
            jTextArea2.setCaretPosition(0);
            jTextArea1.setText(jTextArea1.getText()+"\n"+"You said: "+prevod(pomocna));
            
                try {
                    poslat(koduj(prevod(pomocna),klic));
                    jTextArea2.setText(null);
                    jTextArea2.setCaretPosition(0);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                
        }
        
    }//GEN-LAST:event_jTextArea2KeyPressed

    private void jTextArea2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextArea2InputMethodTextChanged
        
    }//GEN-LAST:event_jTextArea2InputMethodTextChanged

    private void jTextArea2CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextArea2CaretPositionChanged
    }//GEN-LAST:event_jTextArea2CaretPositionChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        konec();
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        klic="";
        int pomocna = jPasswordField1.getPassword().length;
        for (int i = 0; i < pomocna; i++) {
            klic=klic+jPasswordField1.getPassword()[i];
        }
        klic=prevod(klic);// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Server server;
        server = new Server();
        server.setVisible(true);
        server.spustit();
        
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
}
