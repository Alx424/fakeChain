package fakechain;
import java.security.*;
import java.util.ArrayList;

public class Transaction {
    public String transactionId; // also hash of transaction
    public PublicKey sender;
    public PublicKey recipient;
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

    private static int sequence; // rough count of how many transactions have been created

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    // calculates transaction hash (used as Id)
    private String calculateHash() {
        sequence++; // increases sequence to avoid having the same hash
        return StringUtil.applySha256(
            StringUtil.getStringFromKey(sender) + 
            StringUtil.getStringFromKey(recipient) + 
            Float.toString(value) + sequence
        );
    }
    //Signs all the data we dont wish to be tampered with.
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value)	;
	    return StringUtil.verifyECDSASig(sender, data, signature);
    }
}
