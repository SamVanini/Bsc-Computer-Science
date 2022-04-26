#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <stdint.h>
#include <sys/time.h>

/* Inserite eventuali extern modules qui */

extern void elaborazione(char in[], char out[]);

enum { MAXLINES = 200 };
enum { LIN_LEN = 5 };
enum { LOUT_LEN = 15 };

long long current_timestamp() {
    struct timespec tp;
	clock_gettime(CLOCK_REALTIME, &tp);
	/* te.tv_nsec nanoseconds divide by 1000 to get microseconds*/
	long long nanoseconds = tp.tv_sec*1000LL + tp.tv_nsec; // caculate nanoseconds
    return nanoseconds;
}


int main(int argc, char *argv[]) {
    int i = 0;
    char bufferin[MAXLINES*LIN_LEN+1] ;
    char line[1024];
    long long tic_c, toc_c, tic_asm, toc_asm;

    char bufferout_c[MAXLINES*LOUT_LEN+1] = "" ;
    char bufferout_asm[MAXLINES*LOUT_LEN+1] = "" ;

    FILE *inputFile = fopen(argv[1], "r");

    if(argc != 3)
    {
        fprintf(stderr, "Syntax ./test <input_file> <output_file>\n");
        exit(1);
    }

    if (inputFile == 0)
    {
        fprintf(stderr, "failed to open the input file. Syntax ./test <input_file> <output_file>\n");
        exit(1);
    }

    while (i < MAXLINES && fgets(line, sizeof(line), inputFile))
    {
        i = i + 1;
        strcat( bufferin, line) ;
    }

    bufferin[MAXLINES*LIN_LEN] = '\0' ;

    fclose(inputFile);


    /* ELABORAZIONE in C */
    tic_c = current_timestamp();
   
	printf("\n\t\t--- ELABORAZIONE C ---\n\n");

    int c = 0;
	int k = 0;
	int j = 0;
	int A = 0;
	int B = 0;
	int C = 0;
	int riga = 1;
	char Numeri[32][2] ={
							{'0','0'}, {'0','1'}, {'0','2'}, {'0','3'}, {'0','4'}, {'0','5'}, {'0','6'}, {'0','7'}, {'0','8'}, {'0','9'},
							{'1','0'}, {'1','1'}, {'1','2'}, {'1','3'}, {'1','4'}, {'1','5'}, {'1','6'}, {'1','7'}, {'1','8'}, {'1','9'},
							{'2','0'}, {'2','1'}, {'2','2'}, {'2','3'}, {'2','4'}, {'2','5'}, {'2','6'}, {'2','7'}, {'2','8'}, {'2','9'},
							{'3','0'}, {'3','1'}
						};
	char rigaIn[LIN_LEN];
	char rigaOut[LOUT_LEN] = {'C', 'C', '-', '0', '0', '-', '0', '0', '-', '0', '0', '-', '0', '0', '0'};

    while ( bufferin[c] != '\0') {
		k = 0;
		while(bufferin[c + k] != '\n'){
			rigaIn[k] = bufferin[k+c];
			k++;
		}
			
	
		write(1, "> ", 2);
		write(1, rigaIn, k);
		printf("\n\n");	
		rigaIn[k] = '\0';

		if(riga <= 3){
			if(rigaIn[3] == '\0'){
				if (rigaIn[0] == 'A')
					for (j = 0; j <= 9; j++)
						if (Numeri[j][1] == rigaIn[2]) 
							A = j;
				if (rigaIn[0] == 'B')
					for (j = 0; j <= 9; j++)
						if (Numeri[j][1] == rigaIn[2])
							B = j;

				if (rigaIn[0] == 'C')
					for (j = 0; j <= 9; j++)
						if (Numeri[j][1] == rigaIn[2])
							C = j;
			}else{
				if (rigaIn[0] == 'A')
					for (j = 0; j < 32; j++)
						if ((Numeri[j][0] == rigaIn[2]) && (Numeri[j][1] == rigaIn[3]))
							A = j;

				if (riga == 2)
					for (j = 0; j < 32; j++)
						if ((Numeri[j][0] == rigaIn[2]) && (Numeri[j][1] == rigaIn[3]))
							B = j;

				if (riga == 3)
					for (j = 0; j < 25; j++)
						if ((Numeri[j][0] == rigaIn[2]) && (Numeri[j][1] == rigaIn[3]))
							C = j;
			}	
		}else{
			if (rigaIn[0] == 'I' && rigaIn[1] == 'N' && rigaIn[2] == '-'){
				if (rigaIn[3] == 'A' && A <= 30){
					A ++;
					rigaOut[0] = 'O';
				}
				if (rigaIn[3] == 'B' && B <= 30){
					B ++;
					rigaOut[0] = 'O';
				}
				if (rigaIn[3] == 'C' && C <= 23){
					C ++;
					rigaOut[0] = 'O';
				}
					
			}else{
				rigaOut[0] = 'C';
				rigaOut[1] = 'C';
			}
			
			if (rigaIn[0] == 'O' && rigaIn [1] == 'U' && rigaIn[2] == 'T' && rigaIn[3] == '-'){
				if (rigaIn[4] == 'A' && A > 0){
					A --;
					rigaOut[0] = 'C';
					rigaOut[1] = 'O';
				}
				if (rigaIn[4] == 'B' && B > 0){
					B --;
					rigaOut[0] = 'C';
					rigaOut[1] = 'O';
				}
				if (rigaIn[4] == 'C' && C > 0){
					C --;
					rigaOut[0] = 'C';
					rigaOut[1] = 'O';
				}
			}
		}

		rigaOut[3] = Numeri[A][0];
		rigaOut[4] = Numeri[A][1];
		rigaOut[6] = Numeri[B][0];
		rigaOut[7] = Numeri[B][1];
		rigaOut[9] = Numeri[C][0];
		rigaOut[10] = Numeri[C][1];

		if(A == 31)
			rigaOut[12] = '1';
		if(B == 31)
			rigaOut[13] = '1';
		if(C == 24)
			rigaOut[14] = '1';
		
		write(1, rigaOut, 15);
		riga ++;
		c = c + k + 1;
		rigaOut[0] = 'C';
		rigaOut[1] = 'C';
		rigaOut[12] = '0';
		rigaOut[13] = '0';
		rigaOut[14] = '0';
		printf("\n\n");	
    }
  
    toc_c = current_timestamp();

  	long long c_time_in_nanos = toc_c - tic_c;

    /* FINE ELABORAZIONE C */


    /* INIZIO ELABORAZIONE ASM */

	printf("\t\t--- ELABORAZIONE ASSEMBLY ---\n\n");

    tic_asm = current_timestamp();

	elaborazione(bufferin, bufferout_asm);

    toc_asm = current_timestamp();

  	long long asm_time_in_nanos = toc_asm - tic_asm;

    /* FINE ELABORAZIONE ASM */
	
	//stampa di controllo
  	write(1, bufferout_asm, 600);

	printf("\n\t\t--- CONFRONTO TEMPI ---\n\n");
    printf("C time elapsed: %lld ns\n", c_time_in_nanos);
    printf("ASM time elapsed: %lld ns\n", asm_time_in_nanos);

    /* Salvataggio dei risultati ASM */
  	FILE *outputFile;
    outputFile = fopen (argv[2], "w");
    fprintf (outputFile, "%s", bufferout_asm);
    fclose (outputFile);

    return 0;
}
