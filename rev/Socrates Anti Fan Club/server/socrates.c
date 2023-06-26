#include <stdio.h>
#include <string.h>

unsigned char enc[30] = {195, 157, 101, 171, 176, 96, 26, 7, 186, 210, 232, 157, 171, 26, 210, 143, 129, 54, 26, 210, 195, 120, 36, 242, 171, 176, 96, 26, 7, 0};
int mod = 253;

void print_flag() {
    char flag[256];
    FILE* flagfile = fopen("socrates_flag.txt", "r");
    if (flagfile == NULL) {
        puts("Cannot read socrates_flag.txt.");
    } else {
        fgets(flag, 256, flagfile);
        flag[strcspn(flag, "\n")] = '\0';
        puts(flag);
    }
}


int main(void) {
    char inp[256];
    puts("Hello, young Athenian, I am Socrates"); 
    puts("May I ask you a few questions?");
    printf("What is justice?");
    fflush(stdout);
    fgets(inp, 256, stdin);
    inp[strcspn(inp, "\n")] = '\0';
    if (strcmp(inp, "frozen water") != 0) {
        puts("Heehee hahaha you are dumb as hell");
        return 0;
    }
    printf("How old are you? ");
    fflush(stdout);
    int n;
    scanf("%d", &n);
    if (n * 420 != 28980) {
        puts("Heehee hahaha you are dumb as hell");
        return 0;
    }
    printf("How did you come to this conclusion? ");
    fflush(stdout);
    getchar();
    fgets(inp, 256, stdin);
    inp[strcspn(inp, "\n")] = '\0';
    for (char* p = inp; *p != '\0'; p ++) {
        *p = ((*p + 13) * 89 ) % mod;
    }
    if (strcmp(inp, (char*) enc) != 0) {
        puts("Heehee hahaha you are dumb as hell");
        return 0;
    }
    printf("How do you know?");
    fflush(stdout);
    fgets(inp, 256, stdin);

    for (int i = 0; i < 19; i++) {
        inp[i] = inp[i] - i;    
    }
    
    inp[strcspn(inp, "\n")] = '\0';
    if (strcmp(inp, "ynso[him`\\hThb^UO\\S") != 0) {
        puts("Heehee hahaha you are dumb as hell");
        return 0;
    }
    puts("You win! I will stop bothering people now");
    print_flag();
    return 0;
}
