// Jakub Steć
#include "bitwise_operations.h"
void Print(int set, char* serie) {

    if(set==0) {
        *(serie) = 'e';
        *(serie+1) = 'm';
        *(serie+2) = 'p';
        *(serie+3) = 't';
        *(serie+4) = 'y';
        *(serie+5) = '\0';
    }
    else {

        if(set & (1 << 31)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 30)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 29)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 28)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 27)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 26)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 25)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 24)) {
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 23)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 22)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 21)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 20)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 19)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 18)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 17)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 16)) {
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 15)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 14)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 13)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 12)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 11)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 10)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 9)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 8)) {
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 7)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 6)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 5)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 4)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 3)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 2)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = '0';
            *(serie++) = ' ';
        }

        if(set & (1 << 1)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '1';
            *(serie++) = ' ';
        }

        if(set & (1 << 0)) {
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = '0';
            *(serie++) = ' ';
        }
        *(serie-1) = '\0';
    }
}