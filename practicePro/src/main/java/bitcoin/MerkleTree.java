package bitcoin;

import sun.plugin2.message.Message;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i311352 on 5/20/2017.
 */
public class MerkleTree {
    List<String> txList;

    // merkle root
    String root;

    public MerkleTree(List<String> tx) {
        this.txList = tx;
        root = "";
    }

    public void merkle_tree() {
        List<String> tmpTx = new ArrayList<>();
        this.txList.stream().forEach(str -> {
            tmpTx.add(str);
        });
        List<String> newTx = getNewTxList(tmpTx);
        while (newTx.size() != 1) {
            newTx = getNewTxList(newTx);
        }
        this.root = newTx.get(0);
    }

    private List<String> getNewTxList(List<String> tx) {
        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tx.size()) {
            String left = tx.get(index);
            index ++;
            //
            String right = "";
            if (index != tx.size()) {
                right = tx.get(index);
            }
            String sha2str = getSHA2HexValue(left+right);
            System.out.println("hexvalue:"+sha2str);
            newTxList.add(sha2str);
            index++;
        }
        return newTxList;
    }

    public String getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2*cipher_byte.length);
            for (byte b:cipher_byte) {
                System.out.println("byte:" + b);
                sb.append(String.format("%02x", b&0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getRoot() {
        return this.root;
    }

    public static void main(String [] args) {
        List<String> tempTxList = new ArrayList<String>();
        tempTxList.add("a");
        tempTxList.add("b");
        tempTxList.add("c");
        tempTxList.add("d");
        tempTxList.add("e");

        MerkleTree merkleTrees = new MerkleTree(tempTxList);
        merkleTrees.merkle_tree();
        System.out.println("root : " + merkleTrees.getRoot());
    }
}

