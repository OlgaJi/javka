package course.task;

/**
 * Обертка над статическим массивом
 */
public class StaticArray implements Array {

    protected int[] array;

    public StaticArray(int[] a) {
        this.array = new int[a.length];
        System.arraycopy(a, 0, this.array, 0, a.length);
    }

    @Override
    public int size() {
        // TODO: вернуть длину массива
        return array.length;
    }

    @Override
    public boolean contains(int value) {
        // TODO: проверить, что элемент есть в массиве
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int get(int index) {
        // TODO: получить элемент по индексу
        return array[index];
    }

    @Override
    public int set(int index, int value) {
        // TODO: присвоить значение по индексу. Вернуть значение элемента, которое заменили
        int res = array[index];
        array[index] = value;
        return res;
    }

    @Override
    public int indexOf(int value) {
        // TODO: получить индекс первого подходящего элемента
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
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

    @Override
    public void reverse() {
        // TODO: перевернуть массив
        int temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

    @Override
    public Array subArray(int fromIndex, int toIndex) {
        // TODO: вернуть подмассив, начиная с индекса fromIndex включительно и заканчивая индексом toIndex невкоючительно
        int[] massiv = new int[toIndex - fromIndex];
        for (int i = fromIndex; i < toIndex; i++) {
            massiv[i - fromIndex] = array[i];
        }
        StaticArray subArray = new StaticArray(massiv);
        return subArray;
    }

    @Override
    public void sort() {
        bubbleSort();
    }

    @Override
    public void sort(ArraySort sort) {
        switch (sort) {
            case BUBBLE:
                bubbleSort();
                break;
            case INSERTION:
                insertionSort();
                break;
            case SELECTION:
                selectionSort();
                break;
            case MERGE:
                mergeSort();
                break;
            case QUICK:
            default:
                sort();
        }
    }

    @Override
    public String toString() {
        // TODO: вернуть массив в виде строки. Например, [3, 4, 6, -2]
        String arrayString = new String();
        for (int i = 0; i < array.length; i++) {
            arrayString = arrayString + array[i];
        }
        return arrayString;
    }

    private void bubbleSort() {
        // TODO: сортировка пузырьком (по возрастанию)
        int temp;
        for (int j = 0; j < array.length; j++) {
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    swap(array, i, i - 1);
                }
            }
        }
    }

    private void insertionSort() {
        //TODO*: сортировка вставками (по возрастанию)
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j - 1, j);
                }
            }
        }
    }

    private void selectionSort() {
        //TODO*: сортировка выбором (по возрастанию)
        int max;
        for (int i = 0; i < array.length; i++) {
            max = 0;
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[max]) {
                    max = j;
                }
            }
            swap(array, max, array.length - i - 1);
        }
    }

    private void mergeSort() {
        //TODO**: сортировка слиянием (по возрастанию)
        mergeSortRec(array);
    }

    private void quickSort() {
        //TODO**: быстрая сортировка (по возрастанию)
    }

    public boolean isAscSorted() {
        // TODO: проверить, что массив отсортирован по возрастанию
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private int[] mergeSortedArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        int i1 = 0;
        int i2 = 0;
        for (int i = 0; i < result.length; i++) {
            if (i1 == array1.length) {
                result[i] = array2[i2];
                i2++;
                continue;
            }
            if (i2 == array2.length) {
                result[i] = array1[i1];
                i1++;
                continue;
            }
            if (array1[i1] < array2[i2]) {
                result[i] = array1[i1];
                i1++;
            } else {
                result[i] = array2[i2];
                i2++;
            }
        }
        return result;
    }

    private int[] copy(int[] source, int index1, int index2) {
        int[] destination = new int[index2 - index1];
        for (int i = 0; i < index2 - index1; i++) {
            destination[i] = source[index1 + i];
        }
        return destination;
    }

    private int[] mergeSortRec(int[] array) {
        int[] result = new int[array.length];
        if (array.length > 1) {
            int[] array1 = copy(array, 0, array.length / 2);
            int[] array2 = copy(array, array.length / 2, array.length);
            array1 = mergeSortRec(array1);
            array2 = mergeSortRec(array2);
            result = mergeSortedArrays(array1, array2);
            return result;
        } else {
            return array;
        }
    }

}