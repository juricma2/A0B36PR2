/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralkapr2;

/**
 *
 * @author Administrator
 */
public class Message {
    private String tweet;
    private final String abeceda = "abcdefghijklmnopqrstuvwxyz 1234567890.,-/:!?+()";
    
    public String code(String password){
        return null;
    }

    public String decode(String password){
        return null;
    }
    
    public Message(String tweet) {
        this.tweet = tweet;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
    
}
