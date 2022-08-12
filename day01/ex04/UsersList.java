public interface UsersList {
    public void addUser(User us);
    public User RetId(int id) throws UserNotFoundException;
    public User RetIndex(int index);
    public int numUsers();
}
