package sample;

public class ClientCounter {
    public int count;
    public ClientCounter(int counts){
        count = counts;
    }
    public int getCount() {
        return count;
    }
    public void increment() {
        count++;
    }
}
