package invoimo.connections

import grails.gorm.transactions.Transactional

@Transactional
class ConnectionService {

    static InputStream urlToInputStream(URL url, String accept) {
        HttpURLConnection con = null
        InputStream inputStream = null
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(15000)
            con.setReadTimeout(15000)
            con.setRequestProperty("Accept", accept)
            con.connect()
            inputStream = con.getInputStream()
        } catch (IOException e) {
            throw new RuntimeException(e)
        }
        inputStream
    }
}
