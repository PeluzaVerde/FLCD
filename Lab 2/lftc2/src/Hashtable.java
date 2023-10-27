import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


class HashNode<K, V>{
    K key;
    V value;
    HashNode<K, V> next;

    public HashNode(K key, V value){
        this.key = key;
        this.value = value;
        next = null;


    }
}

public class Hashtable<K, V> {

    private List<HashNode<K, V>> bucketArray;
    private final int capacity;
    private int size;

    public Hashtable( int capacity){
        this.bucketArray = new ArrayList<>();
        this.capacity = capacity;


        for (int i = 0; i < capacity; i++){
            bucketArray.add(null);
        }
    }


    public int size() {
        return size;
    }
    public boolean isEmpty(){
        return size() == 0;
    }

    private int hash(K key){
        return Objects.hashCode(key) % capacity;
    }

    public V add (K key, V value){
        this.size++;

        int position = hash(key);
        HashNode<K, V> currentNode = bucketArray.get(position); //, previousNode = null;

//        if (currentNode.key.equals(key)){
//            currentNode.value = value;
//            return;
//        }
//        while (currentNode != null){
//            previousNode = currentNode;
//            currentNode = currentNode.next;
//            }
//            if ( previousNode != null){
//                previousNode.next = new HashNode<>(key, value);
//            }
//            else {
//                bucketArray.add(position, new HashNode<>(key, value));
//            }
        while (currentNode != null){
            if (currentNode.key.equals(key)){
                V oldValue = currentNode.value;
                currentNode.value = value;
                return oldValue;
            }
            currentNode = currentNode.next;
        }
        currentNode = bucketArray.get(position);
        if(currentNode == null){
            bucketArray.set(position, new HashNode<>(key, value));
        }
        else {
            while(currentNode.next != null){
                currentNode = currentNode.next;
            }
            currentNode.next = new HashNode<>(key, value);
        }
    return null;
    }
    public V get(K key)
    {
        int position = hash(key);

        HashNode<K, V> head = bucketArray.get(position);

        // Search key in chain
        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }
        // If key not found
        return null;
    }

    public void remove(K key){
        this.size--;
        int position = hash(key);
        HashNode<K,V> currentNode = bucketArray.get(position), previousNode = null;
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                // Found the node to remove
                if (previousNode == null) {
                    // If it's the first node in the list, update the bucketArray
                    bucketArray.set(position, currentNode.next);
                } else {
                    previousNode.next = currentNode.next;
                }
                return; // Exit the method after removing the node
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

    }

}