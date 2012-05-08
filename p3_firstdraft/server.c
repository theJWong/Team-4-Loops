/* udpserver.c */ 

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>

int main()
{
	//Setup variables for the socket
	int sock;
	int addr_len;
	struct sockaddr_in server_addr , client_addr;

	//GET Message size
	printf("Please enter max message size to receive: ");
	char temp[100];
	gets(temp);
	int buffer_size = atoi(temp);

	//Char buffer (1 byte each) for receive
	char recv_data[buffer_size];
	
	/**** Create/establish Socket ****/
	if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
		perror("Socket");
		exit(1);
	}

	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(5000);
	server_addr.sin_addr.s_addr = INADDR_ANY;
	bzero(&(server_addr.sin_zero),8);


	if (bind(sock,(struct sockaddr *)&server_addr,sizeof(struct sockaddr)) == -1)
	{
		perror("Bind");
		exit(1);
	}

	/***************************************/

	addr_len = sizeof(struct sockaddr);
		
	printf("\nUDPServer Waiting for client on port 5000\n");
	fflush(stdout);

	int bytes_read = 0;

	int num_trans = 0;
	//Keep looping to wait for data sent, CTRL-C to quit
	while (1)
	{
		num_trans++;
		
 		bytes_read = recvfrom(sock,recv_data,buffer_size,0,(struct sockaddr *)&client_addr, &addr_len);

		printf("Transaction #: %d\n", num_trans);
		printf("%d bytes received from (%s , %d)\n", bytes_read,inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));

		
		 //SEND THE DATA BACK
 		if (bytes_read != 0)
		{
			sendto(sock, recv_data, buffer_size, 0, (struct sockaddr *)&client_addr, sizeof(struct sockaddr));
			printf("%d bytes sent back\n", bytes_read);
			printf("CTRL-C to quit the server\n\n");		
		}

		fflush(stdout);
		bytes_read = 0;
 	}
	return 0;
}
