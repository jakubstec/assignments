// Jakub Steć
#include "bitwise_operations.h"
bool Disjoint(int set1, int set2) {
    return (set1 & set2) == 0 ? 1 : 0;
}

bool Conjunctive(int set1, int set2) {
    return (set1 & set2) == 0 ? 0 : 1;
}