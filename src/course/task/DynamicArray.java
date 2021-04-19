package course.task;

/**
 * Динамический массив
 */
public class DynamicArray extends StaticArray implements Dynamic {

    private static final int DEFAULT_CAPACITY = 10;
    private static final float GROW_FACTOR = 1.5f;

    /**
     * текущая длина массива
     */
    private int size;

    public DynamicArray() {
        super(new int[DEFAULT_CAPACITY]);
        size = 0;
    }

    public DynamicArray(int[] array) {
        super(array);
        size = array.length;
    }

    @Override
    public int size() {
        return size;
    }

    private void extend() {
        if (size == array.length) {
            int[] newArray = new int[(int) (array.length * GROW_FACTOR)];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }

    @Override
    public void add(int value) {
        // TODO: добавить элемент в конец массива
        extend();
        array[size] = value;
        size++;
    }

    @Override
    public void add(int index, int value) {
        // TODO: добавить элемент в указанный индекс (остальные сдвинуть вправо)
        extend();
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = value;
        size++;
    }

    @Override
    public void addAll(Array array) {
        // TODO: конкатенация с переданным массивом
        int[] newArray = new int[array.size() + size];
        for (int i = 0; i < size; i++) {
            newArray[i] = this.array[i];
        }
        for (int i = 0; i < array.size(); i++) {
            newArray[size + i] = array.get(i);
        }
        size = newArray.length;
        this.array = newArray;
    }

    @Override
    public boolean remove(int value) {
        // TODO: удалить элемент из массива
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }
        removeOf(index);
        return true;
    }

    @Override
    public boolean removeAll(int[] values) {
        // TODO: удалить все указанные элементы из массива
        boolean success = false;
        for (int current : values) {
            while (remove(current)) {
                success = true;
            }
        }
        return success;
    }

    @Override
    public int removeOf(int index) {
        // удалить элемент по индексу
        size--;
        int oldValue = array[index];
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }
        return oldValue;
    }
}
