package innerclass;

/**
 * Created by i311352 on 6/29/16.
 */
public class parcel {
    public Destination
    destination(final String dest, final float price) {
        return new Destination() {
            private int cost;
            // Instance initialization for each object:
            {
                cost = Math.round(price);
                if(cost > 100)
                    System.out.println("Over budget!");
            }
            private String label = dest;
            public String readLabel() { return label; }
        };
    }
    public static void main(String[] args) {
        parcel p = new parcel();
        Destination d = p.destination("Tasmania", 101.395F);
    }
}

interface Contents {
    int value();
}

interface Destination {
    String readLabel();
}