package fakechain;
import java.util.Date;

public class Block {
  public String hash;
  public String previousHash; // previous block will be used to calculate this block's hash
  private String data; // the contents of the block
  private long timeStamp; // as number of milliseconds since 1/1/1970
  private int nonce;

  public Block(String data, String previousHash) {
    this.data = data;
    this.previousHash = previousHash;
    this.timeStamp = new Date().getTime();

    this.hash = calculateHash(); // get the Block's hash
  }
  public String calculateHash() {
    String calculatedHash = StringUtil.applySha256(
      previousHash + 
      Integer.toString(nonce) +
      Long.toString(timeStamp) + 
      data);
    return calculatedHash;
  }
  public void mineBlock(int difficulty) {
    String target = new String(new char[difficulty]).replace('\0', '0'); // creates string with difficulty * "0"
    while(!hash.substring(0, difficulty).equals(target)) {
      nonce++;
      hash = calculateHash();
    }
    System.out.println("Block Mined!!! : " + hash);
  }
}