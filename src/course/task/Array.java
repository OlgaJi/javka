package course.task;

public interface Array {

    int size();

    boolean contains(int value);

    int get(int index);

    int set(int index, int value);

    int indexOf(int value);

    int lastIndexOf(int value);

    void reverse();

    Array subArray(int fromIndex, int toIndex);

    void sort();

    void sort(ArraySort sort);
}
