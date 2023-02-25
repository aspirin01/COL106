import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {
    int m,size;

    private LinkedList<DictionaryEntry<K, V>> dict;
    /*
     * dict is a Linked-List, where every node of linked-list is of type DictionaryEntry.
     * DictionaryEntry is a key-value pair, where the type of key and value is K and V respectively.
     */

    public LinkedList<HashTableEntry<K, V>>[] hashTable;
    /*
     * hashTable is an array of Linked-Lists which is initialized by the COL106Dictionary constructor.
     * Each index of hashTable stores a linked-list whose nodes are of type HashTableEntry
     * HashTableEntry is a key-address pair, where the type of key is K and the corresponding address is the address of the DictionaryEntry in the linked-list corresponding to the key of HashTableEntry
     */

    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        m = hashTableSize;
        dict = new LinkedList<DictionaryEntry<K, V>>();
        // This statement initiailizes a linked-list where each node is of type DictionaryEntry with key and value of type K and V respectively.
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        // This statement initiailizes the hashTable with an array of size hashTableSize where at each index the element is an instance of LinkedList class and
        // this array is type-casted to an array of LinkedList where the LinkedList contains nodes of type HashTableEntry with key of type K.



    }

    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */
        // checking null key exception
        if (key == null) {
            throw new NullKeyException();
        }
        int hashPos = hash(key);
        // check if the hashPos is null
        if (hashTable[hashPos] == null) {
            hashTable[hashPos] = new LinkedList<HashTableEntry<K, V>>();
        }
        LinkedList<HashTableEntry<K, V>> list = hashTable[hashPos];
        // checking if the key already exists in the list
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).key.equals(key)){
                throw new KeyAlreadyExistException();
            }
        }
        DictionaryEntry<K, V> dictEntry = new DictionaryEntry<>(key, value);
        HashTableEntry<K, V> hashEntry = new HashTableEntry<>(key, dictEntry);
        // adding the dictEntry to the dict
        dict.add(dictEntry);
        // adding the hashEntry to the list
        list.add(hashEntry);
        size++;

    }

    public V delete(K key) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key
         * Working: Deletes the key-value pair from the Dictionary in O(1)
         */
        if (key == null) {
            throw new NullKeyException();
        }
        // traverse dict and delete the key by index
        V val2=null;
        boolean lol =false;

        int hashPos = hash(key);
        // // check if hashPos is negative
        // if (hashPos < 0) {
        //     hashPos = -hashPos;
        // }

        // remove the key from the hashTable
        LinkedList<HashTableEntry<K, V>> list = hashTable[hashPos];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).key.equals(key)) {
                DictionaryEntry<K, V> entry = list.get(i).dictEntry;
                val2 = list.remove(i).dictEntry.value;
                lol = dict.remove(entry);
                break;
            }
        }
        val2=null;

        size--;
        // assert val1.equals(val2);
        return val2;
    }
    // public V delete(K key) throws NullKeyException, KeyNotFoundException{
    //     /*
    //      * To be filled in by the student
    //      * Input: A key of type K
    //      * Return: Returns the associated value of type V with the argumented key
    //      * Working: Deletes the key-value pair from the Dictionary in O(1)
    //      */
    //     if (key == null) {
    //         throw new NullKeyException();
    //     }
    //     // traverse dict and delete the key by index
    //     V val1=null;
    //     V val2=null;
    //     for (int i = 0; i < dict.size(); i++) {
    //         if (dict.get(i).key.equals(key)) {
    //             val1 = dict.remove(i).value;
    //             break;
    //         }
    //     }
    //     int hashPos = hash(key);
    //     // // check if hashPos is negative
    //     // if (hashPos < 0) {
    //     //     hashPos = -hashPos;
    //     // }
    //     if(val1==null){
    //         throw new KeyNotFoundException();
    //     }
    //     // remove the key from the hashTable
    //     LinkedList<HashTableEntry<K, V>> list = hashTable[hashPos];
    //     for (int i = 0; i < list.size(); i++) {
    //         if (list.get(i).key.equals(key)) {
    //             val2 = list.remove(i).dictEntry.value;
    //             break;
    //         }
    //     }
    //     val2=null;

    //     size--;
    //     assert val1.equals(val2);
    //     return val2;
    // }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the previously associated value of type V with the argumented key
         * Working: Updates the value associated with argumented key with the argumented value in O(1)
         */
        // update the value of the key in the dict
        V origVal = null,origValHT=null;
        for(int i=0;i<dict.size();i++){
            if(dict.get(i).key.equals(key)){
                origVal = dict.get(i).value;
                dict.get(i).value = value;
            }
        }
        // update the value of the key in hashTable
        int hashPos = hash(key);
        if(origVal==null){
            throw new KeyNotFoundException();
        }
        // remove the key from the hashTable
        LinkedList<HashTableEntry<K, V>> list = hashTable[hashPos];

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).key.equals(key)) {
                origValHT = dict.get(i).value;

                list.get(i).dictEntry.value = value;
                break;
            }
        }
        // assert statement that the previous values in hash table and dictionary are the same
        assert origVal.equals(origValHT);
        return origVal;
    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */
        // traverse the dict and return the value of the key
        int hashPos = hash(key);
        // // check if hashPos is negative
        // if (hashPos < 0) {
        //     hashPos = -hashPos;
        // }
        // traverse the hashtable and return the value of the key
        LinkedList<HashTableEntry<K, V>> list = hashTable[hashPos];
        if(list==null){
            throw new NullKeyException();
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).key.equals(key)) {
                return list.get(i).dictEntry.value;
            }
        }
        throw new KeyNotFoundException();

    }

    public int size() {
        /*
         * To be filled in by the student
         * Return: Returns the size of the Dictionary in O(1)
         */
        return size;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        K[] ans = (K[]) Array.newInstance(cls, dict.size());
        for(int i=0;i<dict.size();i++){
            ans[i] = dict.get(i).key;
            // System.out.print(ans[i]+" ");
        }
        return ans;
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        V[] ans = (V[]) Array.newInstance(cls, dict.size());
        for(int i=0;i<dict.size();i++){
            ans[i] = dict.get(i).value;
            // System.out.print(ans[i]+" ");
        }

        return ans;
    }

    public int hash(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the hash of the argumented key using the Polynomial Rolling
         * Hash Function.
         */
        String s= key.toString();
        // System.out.println(s);
        int ans=0,p=131;
        for(int i=s.length()-1;i>-1;i--){
            ans=ans*p;
            ans=ans+ (s.charAt(i)+1);
            ans=ans%m;

            // System.out.print(ans+" ");
            // ans=ans%m;
        }
        ans=ans%m;
        if(ans<0){
            ans+=m;
        }
        return ans;

    }
    // function to display the hashTable in a formatted manner with each index and the corresponding linked list and print the values of linked list nodes
    // public void displayHashTable() {
    //     for (int i = 0; i < m; i++) {
    //     //    print linked list at each i
    //     if(hashTable[i]!=null){
    //         // print all values of the linkedList
    //         System.out.print("hashTable["+i+"] = "+"head->");
    //         for(int j=0;j<hashTable[i].size();j++){
    //             System.out.print("("+hashTable[i].get(j).key+", "+hashTable[i].get(j).dictEntry.key+", "+hashTable[i].get(j).dictEntry.value+")->");
    //         }
    //         System.out.print("null");
    //         System.out.println();
    //     }
    //     }
    // }
        // display dictionary
        // public void displayDict(){
        //     System.out.print("dict = head->" );
        //     for(int i=0;i<dict.size();i++){
        //         System.out.print("("+dict.get(i).key+", "+dict.get(i).value+")->");
        //     }
        //     System.out.print("null");
        //     System.out.println();
        // }

        public int getCollisionSize(){
            int sz = 0;
            for (int i = 0; i < m; i++) {
                if(hashTable[i]!=null){
                    sz+=hashTable[i].size()-1;
                }
            }
            return sz;
        }


    public static void main(String[] args){
        // initialize a dictionary with hashTableSize = 10
        COL106Dictionary<String, Integer> dict = new COL106Dictionary<String, Integer>(10);
        // insert key-value pairs in the dictionary
        try {
            dict.insert("COL106", 6);
            dict.insert("COL202", 4);
            dict.insert("CLL271", 3);
            dict.insert("COV889", 1);
            dict.insert("CLL352", 4);
            dict.insert("CLL361", 3);
            dict.insert("CLP302", 2);
            dict.insert("HUL271", 4);
        } catch (KeyAlreadyExistException e) {
            // System.out.println("KeyAlreadyExistException");
        } catch (NullKeyException e) {
            // System.out.println("NullKeyException");
        }
        // print the size of the dictionary
        System.out.println("Size of the dictionary = " + dict.size());
        // print the hashTable
        // dict.displayHashTable();
        // dict.displayDict();
        // delete a key from the dictionary
        try {
            dict.delete("COL202");
        } catch (NullKeyException e) {
            System.out.println("NullKeyException");
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException");
        }
        // print the size of the dictionary
        System.out.println("Size of the dictionary = " + dict.size());
        // print the hashTable
        // dict.displayHashTable();
        // dict.displayDict();

        // update the value of a key in the dictionary
        try {
            System.out.println("Prev val: "+dict.update("CLL352", 5));
        } catch (NullKeyException e) {
            System.out.println("NullKeyException");
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException");
        }
        // print the size of the dictionary
        System.out.println("Size of the dictionary = " + dict.size());
        // print the hashTable
        // dict.displayHashTable();
        // dict.displayDict();

        // get the value of a key in the dictionary
        try {
            System.out.println("Val: "+dict.get("CLL352"));
        } catch (NullKeyException e) {
            System.out.println("NullKeyException");
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException");
        }
        System.out.println("Size of the dictionary = " + dict.size());
        // print the hashTable
        // dict.displayHashTable();

        // dict.displayDict();
        String[]ans = dict.keys(String.class);
        Integer[] val = dict.values(Integer.class);
    }
}

