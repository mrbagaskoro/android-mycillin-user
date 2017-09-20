package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 20/09/2017.
 */

public class AccountList {
    private String accountPic;
    private String accountName;

    public AccountList(String accountPic, String accountName){
        this.accountPic = accountPic;
        this.accountName = accountName;
    }

    public String getAccountPic() {
        return accountPic;
    }

    public void setAccountPic(String accountPic) {
        this.accountPic = accountPic;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
