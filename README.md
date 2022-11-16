# IK1203 Networks and Communcation at KTH
### Socket Programming Project on JAVA
### The Project about implementing networking applications – programs that communicate over the network.
* Task 1 
* Task 2
* Task 3
* Task 4 

## Testing commando
* Test task 1 (TCP)
  `javac TCPAsk.java && java TCPAsk whois.iis.se 43 kth.se`

* Test task 2(TCP)
  `javac TCPAsk.java && java TCPAsk --timeout 300 whois.iis.se 43 kth.se`

* Test task 3(HTTP)
  `javac HTTPAsk.java && java HTTPAsk --timeout 300 whois.iis.se 43 kth.se`
  * In the web peg(Google Chrome) : 
    `http://localhost:8888/ask?hostname=whois.iis.se&port=43&string=kth.se&limit=100`

* Test task 4(HTTP)
  `javac ConcHTTPAsk.java && java ConcHTTPAsk 8888`
  * In the web peg: 
    `http://localhost:8888/ask?hostname=whois.iis.se&port=43&string=kth.se&limit=100`

## Testing Tools
* Wireshark
* Telnet
* Netcat
