# 📚 AVL Tree Book Manager (Java)

This project implements a self-balancing **AVL Tree** in Java to store and manage a list of books using their ISBNs. It reads data from a structured text file (`booklist.txt`) and inserts each book into the AVL tree, automatically rebalancing the tree with rotations as needed.

---

## 📂 Project Structure

| File Name        | Description                                      |
|------------------|--------------------------------------------------|
| `AVLTree.java`   | Core implementation of the AVL tree and `main()` driver. Contains logic for insertion, rotation, and pretty-printing. |
| `booklist.txt`   | Input file of books with ISBN, Title, and Author per 3 lines. Example data included. |

---

## ✨ Features

- 📥 Reads book data from a file and stores it in a tree structure.
- 🌲 Self-balancing AVL Tree using Left, Right, LeftRight, and RightLeft rotations.
- 🔁 Prints real-time rebalancing info to the console.
- 🌿 Traversals: Preorder, Inorder, Postorder.
- 🌟 Tree structure visualization with indentation.

---

## 📘 Sample `booklist.txt` Format

Each book must have **3 lines**:

<ISBN> <Title> <Author> ```
Example:

9780134685991
Effective Java
Bloch, Joshua
9781491910771
Head First Java
Sierra, Kathy - Bates, Bert
🚀 How to Compile and Run
Make sure both AVLTree.java and booklist.txt are in the same folder.

Open terminal or command prompt in that folder.

✅ Compile:
javac AVLTree.java
▶️ Run:
java AVLTree
🌲 AVL Tree Rotation Output
When imbalances occur during insertions, the program reports how it fixed them:

Imbalance condition occurred at inserting ISBN 9781579550813; fixed in Right Rotation
Imbalance condition occurred at inserting ISBN 9780137673629; fixed in LeftRight Rotation
Imbalance condition occurred at inserting ISBN 9781098125974; fixed in RightLeft Rotation
This shows the tree is actively balancing itself to maintain O(log n) operations.
