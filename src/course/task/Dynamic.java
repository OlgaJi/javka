package course.task;

public interface Dynamic {

    void add(int value);

    void add(int index, int value);

    void addAll(Array array);

    boolean remove(int value);

    boolean removeAll(int[] values);

    int removeOf(int index);
}
