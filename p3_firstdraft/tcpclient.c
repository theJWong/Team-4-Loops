/* tcpclient.c */

#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

int main() {

    int sock;  
    struct hostent *host;
    struct sockaddr_in server_addr;  
    struct timeval tv_start;
    struct timeval tv_end;

	//GET Message size
	printf("Please enter max message size to send(bytes): ");
	char temp[100];
	gets(temp);
	int buffer_size = atoi(temp);

	char send_data[buffer_size];
	char recv_data[buffer_size];

	//Variables to measure RTT
	long start,end;

	//GET server ip address
	printf("Please enter IP Address of server: ");
	char hostname[100];
	gets(hostname);

	//Set # of transactions here
	int num_loop = 1000;
	
    host = gethostbyname(hostname);

    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        perror("Socket");
        exit(1);
    }

    server_addr.sin_family = AF_INET;     
    server_addr.sin_port = htons(5001);   
    server_addr.sin_addr = *((struct in_addr *)host->h_addr);
    bzero(&(server_addr.sin_zero),8); 

    if (connect(sock, (struct sockaddr *)&server_addr, sizeof(struct sockaddr)) == -1) {
        perror("Connect");
        exit(1);
    } else {
        printf("\nConnection established to (%s:%d)\n",hostname,ntohs(server_addr.sin_port));
    }

	printf("Sending messages to server %d times\n", num_loop);

	//Begin "timer"
    gettimeofday(&tv_start, NULL);
    start = tv_start.tv_usec;

	//Variable to keep count of # packets successfully sent and received back
	int successful_packets  = 0;

	//Set # of bytes to send here
	int bytes_sent = buffer_size;
    long total_bytes = 0;
    int count = 0;
    while(count < num_loop) {

		printf("Transaction #%d\n", count+1);
        int sent = send(sock,recv_data,strlen(recv_data), 0); 
		printf("%d bytes sent to server (%s)\n", bytes_sent, hostname);
        
		int addr_len = sizeof(struct sockaddr);
        int bytes_read = recv(sock,recv_data,buffer_size,0);
        recv_data[bytes_read] = '\0';
		if (bytes_read > 0)
		{
			printf("%lu bytes received back from the server (%s)\n", sizeof(recv_data), hostname);
			successful_packets++;
            total_bytes += buffer_size;
		}
		else
		 	printf("Packet lost\n");

          count++; 
		fflush(stdout);
        }   
        close(sock);

	//End "timer"
    gettimeofday(&tv_end, NULL);
    end = tv_end.tv_usec;

	//Get difference between times
    double dif = labs(start - end);

    double throughput = (total_bytes*8)/(dif/1000000.0);

	printf("Avg RTT for %d transactions: %.3lf microseconds.\n", num_loop, dif/num_loop);
    printf("Effective throughput: %.3lf bits/sec.\n", throughput);
	printf("%d packets lost.\n", num_loop-successful_packets);
return 0;
}
