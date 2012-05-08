#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <fcntl.h>


int main()
{
	//Setup variables for the socket
	int sock;
	struct sockaddr_in server_addr;
	struct hostent *host;
    struct timeval tv_start;
    struct timeval tv_end;

	//GET Message size
	printf("Please enter max message size to send: ");
	char temp[100];
	gets(temp);
	int buffer_size = atoi(temp);

	//Char buffer (1 byte each) for send
	char send_data[buffer_size];
	//Char buffer (1 byte each) for receive
	char recv_data[buffer_size];


	//Variables to measure RTT
	long start,end;
	
	
	//GET server ip address
	printf("Please enter IP Address of server: ");
	char hostname[100];
	gets(hostname);

	//Set # of transactions here
	int num_loop = 1000;


	/**** Create/establish Socket ****/
	host= (struct hostent *) gethostbyname((char*) hostname);

	if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) == -1)
	{
		perror("socket");
		exit(1);
	}

	/**** ADDED THIS FLAG CONFIGURATION FOR THE SOCKET (FOUND IT ONLINE) 
	        SO THAT THE CLIENT DOESN'T KEEP WAITING ( FOR THE SERVER TO SEND 
	        THE MESSAGE BACK (IT SENSES IF A PACKET IS LOST) -- RECVFROM BY DEFAULT
	        KEEPS WAITING.
	****/
	int flags = fcntl(sock, F_GETFL);
	flags |= O_NONBLOCK;
	fcntl(sock, F_SETFL, flags);
	/********************************************/

	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(5000);
	server_addr.sin_addr = *((struct in_addr *)host->h_addr);
	bzero(&(server_addr.sin_zero),8);

	/***************************************/

	printf("Sending one byte to server %d times\n", num_loop);

	//Begin "timer"
    gettimeofday(&tv_start, NULL);
    start = tv_start.tv_usec;

	//Variable to keep count of # packets successfully sent and received back
	int successful_packets  = 0;

	//Set # of bytes to send here
	int bytes_sent = buffer_size;

	int counter = 0;
	//Loop for # of transactions
	while (counter < num_loop)
	{
		printf("Transaction #%d\n", counter+1);
		//Send the data				
		int sent = sendto(sock, send_data, bytes_sent, 0, (struct sockaddr *)&server_addr, sizeof(struct sockaddr));
		printf("%d bytes sent to server (%s)\n", bytes_sent, hostname);

		// PROBLEM HERE: Need to figure out how to set timeout on recvfrom because it locks up (keeps waiting) if no response is received 
		//recvfrom keeps waiting until it receives something ----> FIXED ABOVE

		//# of bytes read, if after calling recvfrom it is > 0, then successfullly received back
		int bytes_read;
		int addr_len = sizeof(struct sockaddr);
		bytes_read = recvfrom(sock,recv_data,buffer_size,0,(struct sockaddr *)&server_addr, &addr_len);
		
		if (bytes_read > 0)
		{
			printf("%d bytes received back from the server (%s)\n", bytes_read, hostname);
			successful_packets++;
		}
		else
		 	printf("Packet lost\n");

		counter++;
		fflush(stdout);
	}
	

	//End "timer"
    gettimeofday(&tv_end, NULL);
    end = tv_end.tv_usec;

	//Get difference between times
	//double dif = difftime (end,start);
    double dif = labs(start - end);


	printf("Avg RTT for %d transactions: %.9lf microseconds.\n", num_loop, dif/num_loop);
	printf("%d packets lost.\n", num_loop-successful_packets);
}
