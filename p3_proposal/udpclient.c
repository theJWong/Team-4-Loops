#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>

int main() {
    int sock;
    int addr_len, bytes_read;
    struct sockaddr_in server_addr;
    struct hostent *host;
    char send_data[1];
    char recv_data[1];

    host= (struct hostent *) gethostbyname((char *)"127.0.0.1");


    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
        perror("socket");
        exit(1);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(5000);
    server_addr.sin_addr = *((struct in_addr *)host->h_addr);
    bzero(&(server_addr.sin_zero),8);
    addr_len = sizeof(struct sockaddr);

    while (1) {
        printf("Sending one-byte message to server: ");
        gets(send_data);
        sendto(sock, send_data, strlen(send_data), 0,
            (struct sockaddr *)&server_addr, sizeof(struct sockaddr));

        bytes_read = recvfrom(sock,recv_data,1,0,
            (struct sockaddr *)&server_addr, &addr_len);

	    recv_data[bytes_read] = '\0';

        printf("\nServer(%s , %d) sent message:",inet_ntoa(server_addr.sin_addr),
            ntohs(server_addr.sin_port));
        printf("%s\n", recv_data);

	    fflush(stdout);
        char cont[1];
        printf("Continue?(y|n)");
        gets(cont);

        if ((strcmp(cont , "n") == 0) || strcmp(cont , "y") != 0)
           break;
    }

}
