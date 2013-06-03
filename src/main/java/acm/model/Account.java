package acm.model;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(unique = true)
    private String number;
    private Long sold;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public void setUser(User user) {
        this.user = user;
        user.getAccounts().add(this);
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", number=" + number + ", user=" + user
                + ", sold=" + sold + "]";
    }

}
