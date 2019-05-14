package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Users extends Model {

    @Id
    @Constraints.Min(10)
    public Long id;

    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String hashed_password;

    @Constraints.Required
    public String email;

    public String token;

    public static final Finder<Long, Users> find = new Finder<>(Users.class);
}