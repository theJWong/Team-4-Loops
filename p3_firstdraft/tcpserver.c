/* tcpserver.c */

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>


int main()
{
        int sock, connected, bytes_read , true = 1;  

        struct sockaddr_in server_addr,client_addr;    
        int sin_size;
        
        if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
            perror("Socket");
            exit(1);
        }

        if (setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&true,sizeof(int)) == -1) {
            perror("Setsockopt");
            exit(1);
        }
        
        server_addr.sin_family = AF_INET;         
        server_addr.sin_port = htons(5001);     
        server_addr.sin_addr.s_addr = INADDR_ANY; 
        bzero(&(server_addr.sin_zero),8); 

        if (bind(sock, (struct sockaddr *)&server_addr, sizeof(struct sockaddr))
                                                                       == -1) {
            perror("Unable to bind");
            exit(1);
        }

        if (listen(sock, 5) == -1) {
            perror("Listen");
            exit(1);
        }
		
	printf("\nTCPServer Waiting for client on port 5001");
        fflush(stdout);


        while(1)
        {  

            sin_size = sizeof(struct sockaddr_in);

            connected = accept(sock, (struct sockaddr *)&client_addr,&sin_size);

            printf("\nConnection established from (%s , %d)",
                   inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));
            while (1)
            {
              char recv_data[64000];       
              bytes_read = recv(connected,recv_data,64000,0);
 		      if (bytes_read > 0) {
                  recv_data[bytes_read] = '\0';
			      printf("%lu bytes received back from the client (%s)\n", sizeof(recv_data), inet_ntoa(client_addr.sin_addr));
                  fflush(stdout);
                  send(connected, recv_data,strlen(recv_data), 0);  
		          printf("%lu bytes sent back to client (%s)\n", sizeof(recv_data), inet_ntoa(client_addr.sin_addr));
                }
            }
              char prompt[100];
              printf("\n SEND (q or Q to quit) : ");
              gets(prompt);

                close(connected);
              if (strcmp(prompt , "q") == 0 || strcmp(prompt , "Q") == 0)
              {
                break;
              }
        }       

      close(sock);
      return 0;
} 
