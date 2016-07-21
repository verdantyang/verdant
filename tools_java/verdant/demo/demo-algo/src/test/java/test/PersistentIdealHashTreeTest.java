package test;

import java.io.IOException;

import com.verdant.demo.algo.PersistentHashTree;
import org.junit.Test;

import junit.framework.TestCase;

public class PersistentIdealHashTreeTest extends TestCase {
	@Test
	public void testInsert() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		long time = System.currentTimeMillis();
		for(int i = 0;i < 1000000;i++){
			tree.insert(i, String.valueOf(i));
		}
		System.out.println("-----------------------testInsert-------------------------------");
		System.out.println(System.currentTimeMillis() - time);
		System.out.println("-----------------------testInsert-------------------------------");
	}
	
	@Test
	public void testInsertConflict() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		tree.insert(1, "111");
		tree.insert(1, "222");
		tree.insert(1, "333");
		for(int i = 0;i < 100;i++){
			tree.insert(i, String.valueOf(i));
		}
		System.out.println("-----------------------testInsertConflict-------------------------------");
		tree.print();
		System.out.println("-----------------------testInsertConflict-------------------------------");
	}
	
	@Test
	public void testInsertLayer() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		tree.insert(1, "1");
		tree.insert(33, "33");
		tree.insert(1057, "1057");
		tree.insert(2081, "2081");
		tree.insert(1, "duplicate_1");
		tree.insert(2081, "duplicate_2081");
		System.out.println("-----------------------testInsertLayer-------------------------------");
		tree.print();
		System.out.println("-----------------------testInsertLayer-------------------------------");
	}
	
	@Test
	public void testGet() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		for(int i = 0;i < 1000000;i++){
			tree.insert(i, String.valueOf(i));
		}
		long time = System.currentTimeMillis();
		for(int i = 0;i < 1000000;i++){
			tree.get(i);
			tree.get(1000000 - i);
		}
		System.out.println("-----------------------testGet-------------------------------");
		System.out.println(System.currentTimeMillis() - time);
		System.out.println("-----------------------testGet-------------------------------");
	}
	
	@Test
	public void testGetConfilict() throws IOException{
		PersistentHashTree<MyKey, String> tree = new PersistentHashTree<>();
		MyKey key1 = new MyKey(1);
		MyKey key2 = new MyKey(2);
		MyKey key3 = new MyKey(3);
		tree.insert(key1, "111");
		tree.insert(key2, "222");
		tree.insert(key3, "333");
		System.out.println("-----------------------testGetConfilict-------------------------------");
		System.out.println(tree.get(key1));
		System.out.println(tree.get(key2));
		System.out.println(tree.get(key3));
		System.out.println("-----------------------testGetConfilict-------------------------------");
	}
	
	@Test
	public void testGetNotExist() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		for(int i = 0;i < 1000;i++){
			tree.insert(i, String.valueOf(i));
		}
		System.out.println("-----------------------testGetNotExist-------------------------------");
		System.out.println(tree.get(567));
		System.out.println(tree.get(1567));
		System.out.println("-----------------------testGetNotExist-------------------------------");
	}
	
	@Test
	public void testDelete() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		for(int i = 0;i < 100;i++){
			tree.insert(i, String.valueOf(i));
		}
		tree.delete(66);
		System.out.println("-----------------------testDelete-------------------------------");
		tree.print();
		System.out.println("-----------------------testDelete-------------------------------");
	}
	
	@Test
	public void testBatchDelete() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		for(int i = 0;i < 100;i++){
			tree.insert(i, String.valueOf(i));
		}
		System.out.println("-----------------------testBatchDelete-------------------------------");
		tree.print();
		System.out.println("-----------------------testBatchDelete-------------------------------");
		for(int i = 25;i < 88;i++){
			tree.delete(i);
		}
		tree.print();
		System.out.println("-----------------------testBatchDelete-------------------------------");
	}
	
	@Test
	public void testDeleteSpaceRetrieve() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		int length = 10000;
		for(int i = 0;i < length;i++){
			tree.insert(i, String.valueOf(i));
		}
		for(int i = 0;i < length;i++){
			tree.delete(i);
		}
		System.out.println("-----------------------testDeleteSpaceRetrieve-------------------------------");
		tree.print();
		System.out.println("-----------------------testDeleteSpaceRetrieve-------------------------------");
	}
	
	@Test
	public void testDeleteConflict() throws IOException{
		PersistentHashTree<MyKey, String> tree = new PersistentHashTree<>();
		MyKey key1 = new MyKey(1);
		MyKey key2 = new MyKey(2);
		MyKey key3 = new MyKey(3);
		tree.insert(key1, "111");
		tree.insert(key2, "222");
		tree.insert(key3, "333");
		System.out.println("--------------------------testDeleteConflict----------------------------");
		tree.print();
		System.out.println("--------------------------testDeleteConflict----------------------------");
		tree.delete(key2);
		tree.print();
		System.out.println("---------------------------testDeleteConflict---------------------------");
		tree.delete(key1);
		tree.print();
		System.out.println("-----------------------------testDeleteConflict-------------------------");
		tree.delete(key3);
		tree.print();
		System.out.println("------------------------------testDeleteConflict------------------------");
	}
	
	@Test
	public void testDeleteNotExist() throws IOException{
		PersistentHashTree<MyKey, String> tree = new PersistentHashTree<>();
		MyKey key1 = new MyKey(1);
		MyKey key2 = new MyKey(2);
		MyKey key3 = new MyKey(3);
		tree.insert(key1, "111");
		tree.insert(key2, "222");
		tree.insert(key3, "333");
		String temp = tree.delete(new MyKey(4));
		System.out.println("--------------------------testDeleteNotExist----------------------------");
		System.out.println(temp);
		System.out.println("----------------------------testDeleteNotExist--------------------------");
		tree.print();
		System.out.println("----------------------------testDeleteNotExist--------------------------");
	}
	
	@Test
	public void testModify() throws IOException{
		PersistentHashTree<Integer, String> tree = new PersistentHashTree<>();
		for(int i = 0;i < 100;i++){
			tree.insert(i, String.valueOf(i));
		}
		System.out.println("----------------------------testModify--------------------------");
		System.out.println(tree.get(66));
		System.out.println(tree.modify(66, "999"));
		System.out.println(tree.get(66));
		System.out.println(tree.modify(123, "999"));
		System.out.println("----------------------------testModify--------------------------");
	}
	
	@Test
	public void testModifyConflict() throws IOException{
		PersistentHashTree<MyKey, String> tree = new PersistentHashTree<>();
		MyKey key1 = new MyKey(1);
		MyKey key2 = new MyKey(2);
		MyKey key3 = new MyKey(3);
		tree.insert(key1, "111");
		tree.insert(key2, "222");
		tree.insert(key3, "333");
		System.out.println("----------------------------testModifyConflict--------------------------");
		tree.print();
		System.out.println("----------------------------testModifyConflict--------------------------");
		tree.modify(key3, "modify_333");
		tree.print();
		System.out.println("-----------------------------testModifyConflict-------------------------");
		tree.modify(key3, "modify_444");
		tree.print();
		System.out.println("------------------------------testModifyConflict------------------------");
		tree.modify(key2, "modify_12345");
		tree.print();
		System.out.println("-----------------------------testModifyConflict-------------------------");
		tree.modify(key1, "modify_key1");
		tree.print();
		System.out.println("-------------------------------testModifyConflict-----------------------");
		tree.modify(key2, "modify_key1_key1");
		tree.print();
		System.out.println("-------------------------testModifyConflict-----------------------------");
	}
	
	@Test
	public void testModifyMix() throws IOException{
		PersistentHashTree<MyKey, String> tree = new PersistentHashTree<>();
		MyKey key1 = new MyKey(1);
		MyKey key2 = new MyKey(2);
		MyKey key3 = new MyKey(3);
		tree.insert(key1, "111");
		tree.insert(key2, "222");
		tree.insert(key3, "333");
		for(int i = 0;i < 10;i++){
			tree.insert(new MyKey1(i + 100), "test_" + i);
		}
		System.out.println("-------------------------testModifyMix-----------------------------");
		tree.print();
		System.out.println("-------------------------testModifyMix-----------------------------");
		tree.modify(key3, "modify_333");
		tree.print();
		System.out.println("--------------------------testModifyMix----------------------------");
		tree.modify(key3, "modify_444");
		tree.print();
		System.out.println("-------------------------testModifyMix-----------------------------");
		tree.modify(key2, "modify_12345");
		tree.print();
		System.out.println("------------------------testModifyMix------------------------------");
		tree.modify(key1, "modify_key1");
		tree.print();
		System.out.println("-----------------------testModifyMix-------------------------------");
		tree.modify(key2, "modify_key1_key1");
		tree.print();
		System.out.println("------------------------testModifyMix------------------------------");
		tree.modify(new MyKey1(105), "modify_key1_key1");
		tree.print();
		System.out.println("----------------------testModifyMix--------------------------------");
	}
	
	public class MyKey{
		protected int key;
		
		public MyKey(int key){
			this.key = key;
		}
		
		@Override
		public boolean equals(Object o){
			if(o instanceof MyKey){
				return this.key == ((MyKey)o).key;
			}
			return false;
		}
		
		@Override
		public int hashCode(){
			return 1;
		}
	}
	
	public class MyKey1 extends MyKey{
		public MyKey1(int key){
			super(key);
		}
		
		@Override
		public int hashCode(){
			return this.key;
		}
	}
}
