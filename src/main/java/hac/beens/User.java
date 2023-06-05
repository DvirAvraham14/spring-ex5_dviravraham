//package hac.beens;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    private String username;
//    private String password;
//    private String email;
//    private String securityToken;
//
//    public User() {
//    }
//
//    public User(String username, String password, String email, String securityToken) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.securityToken = securityToken;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getSecurityToken() {
//        return securityToken;
//    }
//
//    public void setSecurityToken(String securityToken) {
//        this.securityToken = securityToken;
//    }
//}
//
