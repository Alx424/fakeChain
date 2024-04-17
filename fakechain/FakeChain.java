package fakechain;
import java.util.ArrayList;


public class FakeChain {
  public static ArrayList<Block> blockchain = new ArrayList<Block>(); // create an arraylist to store blocks
  public static int difficulty = 5;
  
  public static void main(String[] args) {

    blockchain.add(new Block("Hi im the first block", "0"));
    System.out.println("Trying to Mine block 1... ");
    blockchain.get(0).mineBlock(difficulty);
    blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size()-1).hash));
    System.out.println("Trying to Mine block 2... ");
    blockchain.get(1).mineBlock(difficulty);
    blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size()-1).hash));
    System.out.println("Trying to Mine block 3... ");
    blockchain.get(2).mineBlock(difficulty);

    System.out.println("\nBlockchain is Valid: " + isChainValid());
  }
  public static Boolean isChainValid() {
    Block currentBlock;
    Block previousBlock;
    String hashTarget = new String(new char[difficulty]).replace('\0', '0');

    for(int i = 0; i < blockchain.size(); i++){
      currentBlock = blockchain.get(i);
      previousBlock = i!=0?blockchain.get(i-1):null;
      
      if (!currentBlock.hash.equals(currentBlock.calculateHash())) 
      {
        System.out.println("Current Hashes not equal");
        return false;
      }
      if(previousBlock != null){ 
        if(!previousBlock.hash.equals(previousBlock.calculateHash()))
        {
          System.out.println("Previous Hashes not equal");
          return false;
        }
      }
      if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
        System.out.println("This block hasn't been mined");
        return false;
      }
    }
    return true;
  } 
}