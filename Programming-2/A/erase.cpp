// Jakub Steć
#include "bitwise_operations.h"
void Erase(char* serie, int* set) {
    if(*serie != '\0') {
        if(*serie == ' ') {
            Erase(serie+1, set);
        } else {
            int tmp = 0;
            int bit5 = (*(serie) - 48) * 16;
            int bit4 = (*(serie+1) - 48) * 8;
            int bit3 = (*(serie+2) - 48) * 4;
            int bit2 = (*(serie+3) - 48) * 2;
            int bit1 = (*(serie+4) - 48);
            tmp = bit1+bit2+bit3+bit4+bit5;
            *set &= ~(1 << tmp);
            serie+=5;
            Erase(serie, set);
        }
    }
}