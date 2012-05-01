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

int main() {
    int sock;
    int addr_len, bytes_read;
    char recv_data[1];
    struct sockaddr_in server_addr , client_addr;


    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
        perror("Socket");
        exit(1);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(5000);
    server_addr.sin_addr.s_addr = INADDR_ANY;
    bzero(&(server_addr.sin_zero),8);


    if (bind(sock,(struct sockaddr *)&server_addr,
        sizeof(struct sockaddr)) == -1) {
        perror("Bind");
        exit(1);
    }

    addr_len = sizeof(struct sockaddr);
		
	printf("\nUDPServer Waiting for client on port 5000");
    fflush(stdout);

	while (1) {
        bytes_read = recvfrom(sock,recv_data,1,0,
            (struct sockaddr *)&client_addr, &addr_len);

	    recv_data[bytes_read] = '\0';

        printf("\nClient(%s , %d) sent message:",inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));
        printf("%s\n", recv_data);

	    fflush(stdout);
        printf("Sending message:%s back to client\n",recv_data);
        sendto(sock, recv_data, strlen(recv_data), 0,
            (struct sockaddr *)&client_addr, sizeof(struct sockaddr));

        char cont[1];
        printf("Continue?(y|n)");
        gets(cont);

        if ((strcmp(cont , "n") == 0) || strcmp(cont , "y") != 0)
           break;
    }
    return 0;
}
