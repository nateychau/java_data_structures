import java.util.Arrays;

public class MinHeap{
  int size = 0; //counts the number of values actually in the heap;
  int[] array; 

  public MinHeap(int n){
    // size = n;
    array = new int[n];
  }


  //get indices of current node's children/parent
  private int getLeftChildIndex(int index){
    return (2*index+1);
  }
  private int getRightChildIndex(int index){
    return (2*index+2);
  }
  private int getParentIndex(int index){
    return (index-1)/2;
  }

  //get values of current node's children/parent
  private int leftChild(int index){
    return array[getLeftChildIndex(index)];
  }
  private int rightChild(int index){
    return array[getRightChildIndex(index)];
  }
  private int parent(int index){
    return array[getParentIndex(index)];
  }

  //check if current node has children/parent
  private boolean hasLeftChild(int index){
    return getLeftChildIndex(index) < size;
  }

  private boolean hasRightChild(int index){
    return getRightChildIndex(index) < size;
  }

  private boolean hasParent(int index){
    return getParentIndex(index) >= 0;
  }


  private void swap(int currentIndex, int indexToBeSwapped){
    int temp = array[indexToBeSwapped];
    array[indexToBeSwapped] = array[currentIndex];
    array[currentIndex] = temp; 
  }

  //create new space in amoritized O(1) time
  private void ensureSpace(){
    if(size == array.length){
      array = Arrays.copyOf(array, array.length*2); //copyOf is technically an O(n) operation
    }
  }

  //read in O(1)
  public int peek(){
    if(size == 0) throw new IllegalStateException();
    return array[0];
  }

  //poll in O(logn) (heapifyDown)
  public int poll(){
    if(size == 0) throw new IllegalStateException();
    
    int value = array[0];
    array[0] = array[size - 1];
    heapifyDown();
    size--;

    return value;
  }

  //insert in O(logn) (heapifyUp. ensureSpace has an amoritized speed of O(1))
  public int insert(int value){
    ensureSpace();

    array[size] = value;
    size++;
    heapifyUp(); 

    return value;
  }

  //bubble a value down from the root to its appropriate place by swapping with smaller children
  //O(logn) to bubble the root all the way down to the bottom of the heap
  //worst case we need to visit every level in the tree
  private void heapifyDown(){
    int index = 0; 
    while(hasLeftChild(index)){ //continue searching while the current node has at least a left child
      int smallerChildIndex = getLeftChildIndex(index); //choose left as the smaller child first (since we know it exists)
      if(hasRightChild(index) && rightChild(index) < leftChild(index)){ //if right child is smaller than left child
        smallerChildIndex = getRightChildIndex(index); //set the right child as the smaller child
      }

      //we can stop searching if the current node is smaller than its smaller child
      if(array[index] < array[smallerChildIndex]){ 
        break;
      }
      else { //otherwise we swap the current node with its smaller child, and continue searching
        swap(index, smallerChildIndex);
        index = smallerChildIndex;
      }
    }
  }

  //bubble the new node up to its correct position in the heap 
  //O(logn) worst case new value is bubbled all the way to the root
  private void heapifyUp(){
    int index = size - 1;
    //while our current has a node that is larger, swap it with its parent
    while(hasParent(index) && parent(index) > array[index]){ 
      swap(index, getParentIndex(index));
      index = getParentIndex(index);
    }
  }


  public static void main(String[] args){
    MinHeap heap = new MinHeap(5);
    for(int i = 8; i > 0; i--){
      heap.insert(i);
      System.out.println(Arrays.toString(heap.array));
    }
  }
}