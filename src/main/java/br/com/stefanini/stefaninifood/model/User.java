//package br.com.stefanini.stefaninifood.model;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//
//@Entity
//public class User implements UserDetails {
//
//    @Id
//    private String username;
//    private String password;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Profile> profiles = new ArrayList<>();
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User login = (User) o;
//        return Objects.equals(username, login.username) && Objects.equals(password, login.password);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username, password);
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public List<Profile> getProfiles() {
//        return profiles;
//    }
//
//    public void setProfiles(List<Profile> profiles) {
//        this.profiles = profiles;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.profiles;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
