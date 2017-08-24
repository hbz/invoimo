package invoimo.organizations

class Address {

    String line1
    String line2
    String line3

    static constraints = {
        line1(blank:false, size:20..100)
    }
}
