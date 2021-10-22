package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.myapplication.search.BTree.BTree;

public class BTreeTest {

    @Test(timeout = 1000)
    public void insertInOrderWithoutSplitTest() {
        // Avoid inserting to the point of causing a split.
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(2);

        // Check insertion was successful
        assertEquals(
                "{order=3, root={keys=[2]}}",
                bTree.toString()
        );

        bTree.insert(1);
        assertEquals(
                "{order=3, root={keys=[1, 2]}}",
                bTree.toString()
        );

        // Try the same but with order 6
        bTree = new BTree<>(6);
        bTree.insert(10);
        bTree.insert(7);
        bTree.insert(8);
        assertEquals(
                "{order=6, root={keys=[7, 8, 10]}}",
                bTree.toString()
        );

        bTree.insert(15);
        bTree.insert(9);
        assertEquals(
                "{order=6, root={keys=[7, 8, 9, 10, 15]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void singleSplitAtRootTest() {
        // Cause a split at the root node.
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);

        assertEquals(
                "{order=3, root={keys=[2], children=[{keys=[1, 2]}, {keys=[3]}]}}",
                bTree.toString()
        );

        // Attempt the same with order 5
        bTree = new BTree<>(5);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);

        assertEquals(
                "{order=5, root={keys=[3], children=[{keys=[1, 2, 3]}, {keys=[4, 5]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void multiSplitTest() {
        // Cause a split at a node that is not the root (will cause a split at root to get to this point).
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);

          assertEquals(
                "{order=3, root={keys=[4], children=[{keys=[2], children=[{keys=[1, 2]}, {keys=[3, 4]}]}, {keys=[6], children=[{keys=[5, 6]}, {keys=[7]}]}]}}",
                bTree.toString()
        );

        // Cause split the other way (left child) with greater order
        bTree = new BTree<>(7);
        bTree.insert(16);
        bTree.insert(15);
        bTree.insert(14);
        bTree.insert(13);
        bTree.insert(12);
        bTree.insert(11);
        bTree.insert(10);
        bTree.insert(9);
        bTree.insert(8);
        bTree.insert(7);
        bTree.insert(6);
        bTree.insert(5);
        bTree.insert(4);
        bTree.insert(3);
        bTree.insert(2);
        bTree.insert(1);

        assertEquals(
                "{order=7, root={keys=[4, 7, 10, 13], children=[{keys=[1, 2, 3, 4]}, {keys=[5, 6, 7]}, {keys=[8, 9, 10]}, {keys=[11, 12, 13]}, {keys=[14, 15, 16]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void splitMiddleChild() {
        // Cause a situation where you must split the middle child.
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(5);
        bTree.insert(12);
        bTree.insert(75);
        bTree.insert(125);
        bTree.insert(220);
        bTree.insert(100);
        bTree.insert(105);

        assertEquals(
                "{order=3, root={keys=[100], children=[{keys=[12], children=[{keys=[5, 12]}, {keys=[75, 100]}]}, {keys=[125], children=[{keys=[105, 125]}, {keys=[220]}]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void manyInsertTest() {
        // Insert a lot of numbers with order 3
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);
        bTree.insert(8);
        bTree.insert(9);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(12);
        bTree.insert(13);
        bTree.insert(14);
        bTree.insert(15);

        // Check that the insertion is correct.
        assertEquals(
                "{order=3, root={keys=[8], children=[{keys=[4], children=[{keys=[2], children=[{keys=[1, 2]}, {keys=[3, 4]}]}, {keys=[6], children=[{keys=[5, 6]}, {keys=[7, 8]}]}]}, {keys=[12], children=[{keys=[10], children=[{keys=[9, 10]}, {keys=[11, 12]}]}, {keys=[14], children=[{keys=[13, 14]}, {keys=[15]}]}]}]}}",
                bTree.toString()
        );

        // Insert a lot of numbers with order 5
        bTree = new BTree<>(5);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);
        bTree.insert(8);
        bTree.insert(9);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(12);
        bTree.insert(13);
        bTree.insert(14);
        bTree.insert(15);

        // Check that the insertion is correct.
        assertEquals(
                "{order=5, root={keys=[3, 6, 9, 12], children=[{keys=[1, 2, 3]}, {keys=[4, 5, 6]}, {keys=[7, 8, 9]}, {keys=[10, 11, 12]}, {keys=[13, 14, 15]}]}}",
                bTree.toString()
        );
    }

    @Test(timeout = 1000)
    public void maxOnEmptyBTree() {		
        // Create a BTree with no keys or children.		
        BTree<Integer> bTree = new BTree<>(3);		
        // Call .max() and assert null is returned. If an exception is thrown, than the user will get an exception on the test.		
        assertNull(		
                "\n.max() failed on empty Btree. Your return should be: null but is not",		
                bTree.max()		
        );		
    }

    @Test(timeout = 1000)
    public void maxWithoutSplitTest() {
        // Create a BTree and ensure the root has no children
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);

        String expected = "[1, 2]";

        // Check that the maximum is correct.
        assertEquals(
                2,
                (int) bTree.max()
        );

        // Try again with different order
        bTree = new BTree<>(7);
        bTree.insert(4);
        bTree.insert(12);
        bTree.insert(8);
        bTree.insert(6);
        bTree.insert(1);

        expected = "[1, 4, 6, 8, 12]";

        // Check that the maximum is correct.
        assertEquals(
                12,
                (int) bTree.max()
        );

        // Add to limit of BTree before it splits
        bTree.insert(75);

        expected = "[1, 4, 6, 8, 12, 75]";

        // Check that the maximum is correct.
        assertEquals(
                75,
                (int) bTree.max()
        );
    }

    @Test(timeout = 1000)
    public void maxWithSplitTest() {
        // Create a BTree and ensure the root has children (splits at one point).
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);

        String expected = "[2]\n├─[1]\n├─[3]";

        // Check that the maximum is correct.
        assertEquals(
                3,
                (int) bTree.max()
        );

        // Create mote splits
        bTree.insert(7);
        bTree.insert(5);
        bTree.insert(80);
        bTree.insert(10);

        expected = "[5]\n├─[2]\n	├─[1]\n	├─[3]\n├─[10]\n	├─[7]\n	├─[80]";

        // Check that the maximum is correct.
        assertEquals(
                80,
                (int) bTree.max()
        );


        // Try again with different order
        bTree = new BTree<>(5);
        bTree.insert(14);
        bTree.insert(12);
        bTree.insert(87);
        bTree.insert(62);
        bTree.insert(11);
        bTree.insert(16);
        bTree.insert(90);
        bTree.insert(75);
        bTree.insert(30);
        bTree.insert(20);
        bTree.insert(10);
        bTree.insert(58);
        bTree.insert(20);
        bTree.insert(18);
        bTree.insert(68);
        bTree.insert(38);
        bTree.insert(8);
        bTree.insert(2);
        bTree.insert(77);
        bTree.insert(43);
        bTree.insert(23);
        bTree.insert(42);

        expected = "[30]\n├─[10, 14, 20]\n	├─[2, 8]\n	├─[11, 12]\n	├─[20, 23]\n	├─[16, 18]\n├─[58, 75]\n	├─[38, 42, 43]\n	├─[62, 68]\n	├─[77, 87, 90]";

        // Check that the maximum is correct.
        assertEquals(
                90,
                (int) bTree.max()
        );
    }


    @Test(timeout = 1000)
    public void multipleSame() {
        BTree<Integer> bTree = new BTree<>(3);
        bTree.insert(1);
        assertEquals(1, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(2, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(3, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(4, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(5, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(6, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(7, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(8, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(9, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(10, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(11, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(12, bTree.get(1).size());
        bTree.insert(1);
        assertEquals(13, bTree.get(1).size());

    }
}
