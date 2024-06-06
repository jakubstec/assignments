// Jakub Steć
#include "bitwise_operations.h"
int Cardinality(int set) {

    int card = 0;
    if(set & (1 << 0))  card++;
    if(set & (1 << 1))  card++;
    if(set & (1 << 2))  card++;
    if(set & (1 << 3))  card++;
    if(set & (1 << 4))  card++;
    if(set & (1 << 5))  card++;
    if(set & (1 << 6))  card++;
    if(set & (1 << 7))  card++;
    if(set & (1 << 8))  card++;
    if(set & (1 << 9))  card++;
    if(set & (1 << 10))  card++;
    if(set & (1 << 11))  card++;
    if(set & (1 << 12))  card++;
    if(set & (1 << 13))  card++;
    if(set & (1 << 14))  card++;
    if(set & (1 << 15))  card++;
    if(set & (1 << 16))  card++;
    if(set & (1 << 17))  card++;
    if(set & (1 << 18))  card++;
    if(set & (1 << 19))  card++;
    if(set & (1 << 20))  card++;
    if(set & (1 << 21))  card++;
    if(set & (1 << 22))  card++;
    if(set & (1 << 23))  card++;
    if(set & (1 << 24))  card++;
    if(set & (1 << 25))  card++;
    if(set & (1 << 26))  card++;
    if(set & (1 << 27))  card++;
    if(set & (1 << 28))  card++;
    if(set & (1 << 29))  card++;
    if(set & (1 << 30))  card++;
    if(set & (1 << 31))  card++;

    return card;
}