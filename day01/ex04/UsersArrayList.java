public class UsersArrayList implements UsersList {
    User[] users = new User[10];
    int max = 10;
    int num = 0;

    @Override
    public void addUser(User us) {
        if (num == max){
            User[] users = new User[max * 2];
            for(int i = 0; i < max; i++){
                users[i] = this.users[i];
            }
            this.users = users;
            max = max * 2;
        }
        this.users[this.num] = us;
        this.num++;
    }

    @Override
    public User RetId(int id) throws UserNotFoundException {
        for(int i = 0; i < this.num; i++){
            if(this.users[i].getID() == id)
                return this.users[i];
        }
        throw new UserNotFoundException();
    }

    @Override
    public User RetIndex(int index) throws UserNotFoundException {
        if (index <= num)
            return this.users[index];
        throw new UserNotFoundException();
    }

    @Override
    public int numUsers() {
        return this.num;
    }
}
