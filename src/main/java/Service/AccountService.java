package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    /**
     * @param accountDAO 
    */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * @param account 
    */
    public Account registerAccount(Account account)
    {
        return accountDAO.registerAccount(account);
    }

    public Account loginAccount(Account account)
    {
        //accountDAO.loginAccount
        return null;
    }
}
