package legacyfigher.dietary.newproducts;

import java.math.BigDecimal;
import java.util.UUID;

// Nazwa klasy troche niejasna, old bo stara wersja produktu i teraz mam jakas nowa klase od tego czy dlaczego?
// - jesli bysmy chcieli zmieniac nazwe tej klasy no to moze byc problematyczne, bo ludzie moga byc przyzwyczajeni,
// a jak ma wiele zaleznosci to moze byc ciezko dokonac taka zmiane
public class OldProduct {

    // - pole nie prywatne, w teorii mozna by to latwo nadpisac
    // w teorii jesli nie jest ono prywatne, to jesli to zmienimy to sie moze okazac ze cos zepsujemy w innych klasach
    // - to pole tez nie jest uzywane wiec mozna by je usunac, no chyba ze jest do czegos potrzebne?
    UUID serialNumber = UUID.randomUUID();

    // - pole nie prywatne, w teorii mozna by to latwo nadpisac
    // w teorii jesli nie jest ono prywatne, to jesli to zmienimy to sie moze okazac ze cos zepsujemy w innych klasach
    // - mozna by bylo uzyc klasy Money, do zamodelowania price, ale niekoniecnzie na tym etapie jest nam to potrzebne,
    // a poza tym trzeba by dokonac zmian tez w innych czesciach systemu
    BigDecimal price;
    // wolalbym pelna nazwe w stylu 'description'
    private String desc;

    // - wolalbym pelna nazwe w stylu 'fullDescription'
    // - pole nie prywatne, w teorii mozna by to latwo nadpisac
    // w teorii jesli nie jest ono prywatne, to jesli to zmienimy to sie moze okazac ze cos zepsujemy w innych klasach
    String longDesc;

    // - pole nie prywatne, w teorii mozna by to latwo nadpisac
    // w teorii jesli nie jest ono prywatne, to jesli to zmienimy to sie moze okazac ze cos zepsujemy w innych klasach
    // - nazwa counter troche nie pasuje jak na zmienna opisujaca stan magazynowy, mozna by bylo ta nazwe zmienic na cos
    // bardziej intuicyjnego, w stylu: `productCount`
    // - pytanie czy Integer to dobry typ dla tego pola? A co jak bedziemy mieli wiecej rzeczy niz Integer pozwala?
    // Moze byc ciezko przeniesc sie na inny typ ze wzgledu na istniejace zaleznosci.
    // - Potrzebujemy tu Integer'a? Dlaczego nie zwykly int? Znowu tak jak powyzej moze byc problem ze zmigrowaniem sie
    // do innego typu, choc chyba mniejszy effort nic z Integera na longa

    Integer counter;



    // metoda nie private ani nie public, nie jest tez uzywana. Potrzebujemy jej?
    void decrementCounter() {
        if (price != null && price.signum() > 0) {

            // nie lepiej sprawdzac ten counter na samym poczatku metody? Wtedy zrobimy szybki fail
            if
            (counter == null) {
                throw new IllegalStateException("null counter");
            }
            counter = counter - 1;
            // nie podoba mi sie rzucanie tego wyjatku, nie lepiej by bylo sprawdzac to zanim obnizymy counter i dawac lepszy feedback?
            if (counter < 0) {
                throw new IllegalStateException("Negative counter");
            }
        } else {
            throw new IllegalStateException("Invalid price");

        }

    }

    // konstruktor bym przesunal na sama gore, tuz po definicji pol
    public OldProduct(BigDecimal price, String desc, String longDesc, Integer counter) {
        this.price = price;
        this.desc = desc;
        this.longDesc = longDesc;
        this.counter = counter;
    }

    // - metoda nie private ani nie public, nie jest tez uzywana. Potrzebujemy jej?
    // - wyglada bardzo podobnie do `decrementCounter`. Warto by bylo wyciagnac wspolny kod, co by uniknac duplikacji kodu
    // mozna wspomoc sie IDE wiec nie bedzie to duzy challenge
    void incrementCounter() {
        if (price != null && price.signum() > 0) {
            if (counter == null) {
                throw new IllegalStateException("null counter");
            }
            if (counter +1 < 0) {
                throw new IllegalStateException("Negative counter");
            }
            counter = counter + 1;

        }
        else {
            throw new IllegalStateException("Invalid price");

        }
    }

    // - metoda nie private ani nie public, nie jest tez uzywana. Potrzebujemy jej?
    void changePriceTo(BigDecimal newPrice) {
        // znowu sprawdzamy counter w ten sam sposob. Mozna by bylo go sprawdzac przy inicjalizacji obiektu i wtedy tylko tam rzucac wyjatek
        if (counter == null) {
            throw new IllegalStateException("null counter");
        }
        if
        (counter > 0) {
            if (newPrice == null) {
                throw new IllegalStateException("new price null");
            }
            this.price = newPrice;
        }
    }

    // - metoda nie private ani nie public, nie jest tez uzywana. Potrzebujemy jej?
    // - zmienilbym nazwe na bardziej okreslajaca co robi czyli replaceCharactersFromDescription oraz takze nazwy parametrow na bardziej mowiace co robia
    void replaceCharFromDesc(String charToReplace, String replaceWith) {
        // - sprawdzamy zarowno null i isEmpty, mozna by wykorzystac bilbioteke do tego aby to sprawdzac krocej -> StringUtils
        // ewewntualnie mozna samemu takiego utilsa zrobic, tylko na potrzeby tej klasy i pozniej to wyniesc jak by byla potrzeba
        if (longDesc == null || longDesc.isEmpty() ||

                desc == null || desc.isEmpty()) {
            throw new IllegalStateException("null or empty desc");
        }
        // podmieniamy to zarowno longDesc jak i desc, pytanie czy to jest ok, czy nie lepiej miec dwie metody od tego.
        longDesc = longDesc.replace(charToReplace, replaceWith);
        desc = desc.replace(charToReplace, replaceWith);
    }

    // - metoda nie private ani nie public, nie jest tez uzywana. Potrzebujemy jej?
    // - zmienilbym nazwe metody na `formatDescription`
    String formatDesc() {
        // - znowu jest sprawdzana ta sama walidacja co w: `replaceCharFromDesc`. Mozna skorzystac ze wspolnej metody do tego.
        // nie byloby z tym duzo zachodu bo to IDE moze za nas zrobic, a inne systemy nie zaleza od tego
        if (longDesc == null ||
                longDesc.isEmpty() ||
                desc == null
                || desc.isEmpty() ) {
            // - czy na pewno chcemy zwracac nic? W innych przypadkach walimy wyjatkiem...
            return "";
        }
        // - styl zwracanej wartosci jest dziwna i moze sie wydawac nieczytelna. Moze byc to problematyczne do zmiany bo inne czesci systemu moga sie na tym opierac
        return desc + " *** " + longDesc;
    }


}
