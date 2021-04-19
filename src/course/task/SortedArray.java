package course.task;

/**
 * Сортированный статический массив (по возрастанию).
 * <p>
 * Любая операция должна гарантировать, что массив, по ее окончании, отстортирован
 */
public class SortedArray extends StaticArray {

    public SortedArray(int[] array) {
        super(array);
        if (!isAscSorted()) {
            sort();
        }
    }

    @Override
    public boolean contains(int value) {
        return binarySearch(value, 0, array.length) != -1;
    }

    @Override
    public int set(int index, int value) {
        // TODO: присовить значение по индексу
        super.set(index, value);
        return 0;
    }

    @Override
    public int indexOf(int value) {
        return binarySearch(value, 0, array.length - 1);
    }

    @Override
    public int lastIndexOf(int value) {
        // TODO: получить индекс последнего подходящего элемента
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private int binarySearch(int value, int left, int right) {
        // TODO: реализовать бинарный поиск
        if (array[left] == value) {
            return left;
        } else if (left == right) {
            return -1;
        }

        int newEnd = (right - left) / 2 + left;
        if (array[newEnd] > value) {
            if (right == newEnd) {
                return -1;
            }
            return binarySearch(value, left, newEnd);
        } else if (array[newEnd] < value) {
            if (left == newEnd) {
                return -1;
            }
            return binarySearch(value, newEnd, right);
        } else {
            return newEnd;
        }
    }

    public SortedArray merge(SortedArray other) {
        // TODO: произвести слияние двух сортированных массивов
        int[] bigArrayInt = new int[array.length + other.size()];
        int io = 0;
        int ia = 0;
        for (int i = 0; i < bigArrayInt.length; i++) {
            if (array[ia] < other.get(io)) {
                bigArrayInt[i] = array[ia];
                ia++;
            } else {
                bigArrayInt[i] = other.get(io);
                io++;
            }
        }
        var bigArray = new SortedArray(bigArrayInt);
        return bigArray;
    }

    public SortedArray mergeAll(SortedArray... others) {
        // TODO: произвести слиянеие N + 1 сортированных массивов
        var bigArray = new SortedArray(array);
        for (SortedArray current : others) {
            bigArray = bigArray.merge(current);
        }
        return bigArray;
    }
}
