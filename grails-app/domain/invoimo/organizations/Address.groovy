package invoimo.organizations

class Address {

    String line1
    String line2
    String line3

    static constraints = {
        line1 blank:false, size:10..100
        line2 nullable: true
        line3 nullable: true
    }

    @Override
    String toString() {
        StringBuilder result = new StringBuilder(line1)
        if (line2){
            result.append(", ").append(line2)
        }
        if (line3){
            result.append(", ").append(line3)
        }
        return result.toString()
    }
}
