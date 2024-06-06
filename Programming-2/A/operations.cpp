// Jakub Steć
#include "bitwise_operations.h"
void Union(int set1, int set2, int* set3) {
    *set3 = set1 | set2;
}

void Intersection(int set1, int set2, int* set3) {
    *set3 = set1 & set2;
}

void Symmetric(int set1, int set2, int* set3) {
    *set3 = set1 ^ set2;
}

void Difference(int set1, int set2, int* set3) {
    *set3 = set1 & ~set2;
}

void Complement(int set1, int* set2) {
    *set2 = ~set1;
}