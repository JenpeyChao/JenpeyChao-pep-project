package Service;

import DAO.accountDAO;
import Model.Account;

public class accountService {
    private accountDAO accountDAO;
    public accountService(){
        this.accountDAO = new accountDAO();
    }
    public Account getAccountByUsernamePassword(Account account){
        return this.accountDAO.getAccountByUsernamePassword(account.getUsername(), account.getPassword());
    }
    public Account findAccountByUsername(String username) {
        // TODO Auto-generated method stub
        return this.accountDAO.findAccountByUsername(username);
    }
    public Account addNewAccount(Account newAccount) {
        // TODO Auto-generated method stub
        return this.accountDAO.addNewAccount(newAccount);
    }
    public Account findAccountById(int id){
        return this.accountDAO.findAccountById(id);
    }
}
