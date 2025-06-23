package mpp.model;

public class User extends Entitate<Integer> {
    //Integer id;
    private String alias;

    public User(String alias) {
        this.alias = alias;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

}
