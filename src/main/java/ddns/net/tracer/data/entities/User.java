package ddns.net.tracer.data.entities;


import afu.org.checkerframework.common.aliasing.qual.Unique;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.FIND_ALL",
        query= "select u from User u")
@SqlResultSetMapping(
        name = "userResult",
        entities = @EntityResult(entityClass = User.class)
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "create.form.name.surname.empty" )
    @Column(name = "username")
    private String name;

    @NotBlank(message = "create.form.name.surname.empty")
    @Column(name = "last_name")
    private String last_name;

    @JsonIgnore
    @Unique
    @NotBlank(message = "create.form.email.error" )
    @Email(message = "create.form.email.error" )
    @Column(name ="email")
    private String email;

    @JsonIgnore
    @Size(min = 2,message = "create.form.pass.length")
    @Column(name = "pass")
    private String pass;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column
    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(name = "user_target",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "target_id")})
    private List<Target> targets = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private Set<Authorities> roles;

    public User(){
        this.enabled = true;
    }

    public User(String name, String last_name, String email, String pass) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.pass = pass;
        this.enabled = true;
    }

    public void addTarget(Target child){
        targets.add(child);
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authorities> getRoles() {
        return roles;
    }

    public void setRoles(Set<Authorities> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return email;
    }
}
