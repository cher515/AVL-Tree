
import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;


class Book{
    long ISBN;
    String author;
    String title;

    public Book(long ISBN, String author, String title){
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
    }
}


class AVLNode {
    Book bookObj;
	
	AVLNode parent; // pointer to the parent
	AVLNode left; // pointer to left child
	AVLNode right; // pointer to right child
	int bf; // balance factor of the node

	public AVLNode(Book book) {
        this.bookObj = book;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.bf = 0;
	}
}

public class AVLTree {
	private AVLNode root;

	public AVLTree() {
		root = null;
	}

	private void printHelper(AVLNode currPtr, String indent, boolean last) {
		// print the tree structure on the screen
	   	if (currPtr != null) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }

		   System.out.println(currPtr.bookObj.ISBN + "(BF = " + currPtr.bf + ")");

		   printHelper(currPtr.left, indent, false);
		   printHelper(currPtr.right, indent, true);
		}
	}

	private AVLNode searchTreeHelper(AVLNode node, int key) {
		if (node == null || key == node.bookObj.ISBN) {
			return node;
		}

		if (key < node.bookObj.ISBN) {
			return searchTreeHelper(node.left, key);
		}
		return searchTreeHelper(node.right, key);
	}

	private AVLNode deleteNodeHelper(AVLNode node, long key) {
		// search the key
		if (node == null) return node;
		else if (key < node.bookObj.ISBN) node.left = deleteNodeHelper(node.left, key);
		else if (key > node.bookObj.ISBN) node.right = deleteNodeHelper(node.right, key);
		else {
			// the key has been found, now delete it

			// case 1: node is a leaf node
			if (node.left == null && node.right == null) {
				node = null;
			}

			// case 2: node has only one child
			else if (node.left == null) {
				node = node.right;
			}

			else if (node.right == null) {
				node = node.left;
			}

			// case 3: has both children
			else {
				AVLNode temp = minimum(node.right);
				node.bookObj.ISBN = temp.bookObj.ISBN;
				node.right = deleteNodeHelper(node.right, temp.bookObj.ISBN);
			}

		}


		return node;
	}

	// update the balance factor the node
	private void updateBalance(AVLNode node) {
		if (node.bf < -1 || node.bf > 1) {
			rebalance(node);
			return;
		}

		if (node.parent != null) {
			if (node == node.parent.left) {
				node.parent.bf -= 1;
			}

			if (node == node.parent.right) {
				node.parent.bf += 1;
			}

			if (node.parent.bf != 0) {
				updateBalance(node.parent);
			}
		}
	}

	// rebalance the tree
	void rebalance(AVLNode node) {
		if (node.bf > 0) {
			if (node.right.bf < 0) {
				rightRotate(node.right);
				leftRotate(node);
                System.out.println("Imbalance condition occurred at inserting ISBN "+ node.bookObj.ISBN + "; fixed in RightLeft Rotation");
			} else {
				leftRotate(node);
                System.out.println("Imbalance condition occurred at inserting ISBN "+ node.bookObj.ISBN + "; fixed in Left Rotation");
			}
		} else if (node.bf < 0) {
			if (node.left.bf > 0) {
				leftRotate(node.left);
				rightRotate(node);
                System.out.println("Imbalance condition occurred at inserting ISBN "+ node.bookObj.ISBN + "; fixed in LeftRight Rotation");
			} else {
				rightRotate(node);
                System.out.println("Imbalance condition occurred at inserting ISBN "+ node.bookObj.ISBN + "; fixed in Right Rotation");
			}
		}
	}


	private void preOrderHelper(AVLNode node) {
		if (node != null) {
			System.out.print(node.bookObj.ISBN + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		}
	}

	private void inOrderHelper(AVLNode node) {
		if (node != null) {
			inOrderHelper(node.left);
			System.out.print(node.bookObj.ISBN + " ");
			inOrderHelper(node.right);
		}
	}

	private void postOrderHelper(AVLNode node) {
		if (node != null) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.bookObj.ISBN + " ");
		}
	}

	// Pre-Order traversal
	// Node.Left Subtree.Right Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// Left Subtree . Node . Right Subtree
	public void inorder() {
		inOrderHelper(this.root);
	}

	// Post-Order traversal
	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding node
	public AVLNode searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public AVLNode minimum(AVLNode node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public AVLNode maximum(AVLNode node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public AVLNode successor(AVLNode x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != null) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		AVLNode y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public AVLNode predecessor(AVLNode x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the
		// left subtree
		if (x.left != null) {
			return maximum(x.left);
		}

		AVLNode y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	void leftRotate(AVLNode x) {
		AVLNode y = x.right;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf - 1 - Math.max(0, y.bf);
		y.bf = y.bf - 1 + Math.min(0, x.bf);
	}

	// rotate right at node x
	void rightRotate(AVLNode x) {
		AVLNode y = x.left;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf + 1 - Math.min(0, y.bf);
		y.bf = y.bf + 1 + Math.max(0, x.bf);
	}


	// insert the key to the tree in its appropriate position
	public void insert(Book book) {
		// PART 1: Ordinary BST insert
		AVLNode node = new AVLNode(book);
		AVLNode y = null;
		AVLNode x = this.root;

		while (x != null) {
			y = x;
			if (node.bookObj.ISBN < x.bookObj.ISBN) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.bookObj.ISBN < y.bookObj.ISBN) {
			y.left = node;
		} else {
			y.right = node;
		}

		// PART 2: re-balance the node if necessary
		updateBalance(node);
	}

	// delete the node from the tree
	AVLNode deleteNode(int k) {
		return deleteNodeHelper(this.root, k);
	}

	// print the tree structure on the screen
	public void prettyPrint() {
		printHelper(this.root, "", true);
	}


    public static void func(File fileName, AVLTree tAvlTree) throws FileNotFoundException{
        Scanner scnr = new Scanner(fileName);
        while(scnr.hasNextLine()){
            long ISBN = Long.parseLong(scnr.nextLine());
            String title = scnr.nextLine();
            String author = scnr.nextLine();
            Book book = new Book(ISBN, author, title);
            tAvlTree.insert(book);
        }
        scnr.close();
    }

    public static void main(String [] args) throws FileNotFoundException{
        AVLTree tree = new AVLTree();
        File book = new File("booklist.txt");

        func(book, tree);
    }
	
}