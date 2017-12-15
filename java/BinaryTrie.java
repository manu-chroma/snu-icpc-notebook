// General impl http://codeforces.com/contest/858/submission/32363725
class BinaryTrie {
    static class Node {
        int size;
        Node zero;
        Node one;
        Node() {
            size = 1;   // Take this into consideration
        }
    }
    Node root;
    static final int offset = 27;  //Maximum index of set bit
    BinaryTrie(){
        root = new Node();
    }
    void insert(int num){
        root = insert(root, num, offset);
    }    
    int size(Node n) {
        return n == null ? 0 : n.size;
    }    
    Node insert(Node curr , int num , int idx){
        if(idx < 0) {
            if(curr != null)
                curr.size++;
            else
                curr = new Node();
            return curr;
        }
        else{
            if(curr == null)
                curr = new Node();
            if((num & (1 << idx)) == 0)
                curr.zero = insert(curr.zero, num, idx - 1);
            else
                curr.one = insert(curr.one, num, idx - 1);
            
            curr.size = size(curr.zero) + size(curr.one);
            return curr;
        }
    }    
    void remove(int num){
        remove(root, null, num, offset);
    }    
    void remove(Node curr , Node parent , int num , int idx){
        if(idx >= 0){
            if((num & (1 << idx)) == 0)
                remove(curr.zero, curr, num, idx - 1);
            else
                remove(curr.one, curr, num, idx - 1);
        }
        curr.size--;
    }
}
