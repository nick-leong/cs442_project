package teamm.cs442_project;

/**
 * Created by Vignesh on 4/16/2017.
 */

public class user {
    // Private info
    private String id;
    private String username;
    private String password;
    private String email;
    private String clan;

    public user(String id, String userName, String password, String email, String clan){
        this.id = id;
        this.username = userName;
        this.password = password;
        this.email = email;
        this.clan = clan;
    }

    public String getId(){    return id;    }
    public void setId(String id){   this.id = id;   }

    public String getUserName(){    return username;    }
    public void setUsername(String username){   this.username = username;   }

    public String getPassword(){    return password;    }
    public void setPassword(String password){   this.password = password;   }

    public String getEmail(){    return email;    }
    public void setEmail(String email){   this.password = email;   }

    public String getClan(){    return clan;    }
    public void setClan(String clan){   this.clan = clan;   }

}
