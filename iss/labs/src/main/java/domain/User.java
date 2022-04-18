package domain;

import java.io.Serializable;

public class User implements Identifiable<Integer>, Serializable {
    protected String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    Integer ID;
    public Integer getID(){
        return ID;
    }
    public void setID(Integer id){
        ID = id;
    }
}
