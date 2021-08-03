public class ReLinkedList {
    public static void main(String[] args) {
        Node node = new Node();
        Node head =node;
        for (int i = 1; i <= 5; i++) {
            Node newnode = new Node();
            node.setNext(newnode);
            node.setValue(i);
            node=node.getNext();
        }
        head=reverseLinkedList(head);
        while(head!=null){
            System.out.println(head.getValue());
            head=head.getNext();
        }

    }
    public static Node reverseLinkedList(Node node){
        if(node.getNext()==null)
            return node;
        Node newNode = reverseLinkedList(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return newNode;
    }

}
class Node{
    private int value;
    private Node next=null;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
