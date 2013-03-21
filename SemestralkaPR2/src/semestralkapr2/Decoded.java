/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralkapr2;

/**
 *
 * @author Administrator
 */
public class Decoded extends Text{
    private String password;
    private String content;

    public Decoded(String password, String content) {
        this.password = password;
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public String getContent() {
        return content;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
    @Override
    Message transfer(String content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
