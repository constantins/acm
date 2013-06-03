package acm.vo;

import java.util.Date;

import acm.model.Account;


public class AccountVO {

    private Long userId;
    private String user;
    private Long id;
    private String number;
    private Long sold;

	public AccountVO(Account account) {
        setUserId(account.getUser().getId());
        setUser(account.getUser().getUsername());
        setId(account.getId());
        setNumber(account.getNumber());
        setSold(account.getSold());
	}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
