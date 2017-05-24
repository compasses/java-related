package index;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;

/**
 * Created by i311352 on 5/24/2017.
 */
public class FSTApp {
    public static void main(String[] args) {
        try {
            String inputValues[] = {"cat", "deep", "do", "dog", "dogs"};
            long outputValues[] = {5, 7, 17, 18, 21};
            PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
            Builder<Long> builder = new Builder<Long>(FST.INPUT_TYPE.BYTE1, outputs);
            BytesRefBuilder scratchBytes = new BytesRefBuilder();
            IntsRefBuilder scratchInts = new IntsRefBuilder();
            for (int i = 0; i < inputValues.length; i++) {
                scratchBytes.copyChars(inputValues[i]);
                builder.add(Util.toIntsRef(scratchBytes.get(), scratchInts), outputValues[i]);
            }
            FST<Long> fst = builder.finish();
            Long value = Util.get(fst, new BytesRef("dog"));
            System.out.println(value); // 18
        } catch (Exception e) {
            ;
        }
    }
}
