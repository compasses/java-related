package rxTesting;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by I311352 on 7/27/2016.
 */
public class Computer {
    private Optional<Soundcard> soundcard;
    public Optional<Soundcard> getSoundcard() {
        return this.soundcard;
    }
    public static void main(String[] args) {
        Computer computer = new Computer();
//        List<Computer> computers = new ArrayList<>(Arrays.asList(computer));

        Optional<Computer> computerOptional = Optional.of(computer);
        Optional<Soundcard> maybeSoundcard = Optional.empty();

        Soundcard soundcard = maybeSoundcard.orElse(new Soundcard("defaut"));


        String name = computerOptional
                .flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUSB)
                .map(USB::getVersion)
                .orElse("unknow");

        System.out.println("Got name " + name);

    }
}

 class Soundcard {
     Soundcard(String name) {
        this.name = name;
     }
     private String name;
    private Optional<USB> usb;
    public Optional<USB> getUSB() {
        return this.usb;
    }

}

 class USB{
    public String getVersion(){ return "3.3"; }
}