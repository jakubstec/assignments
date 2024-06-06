// Jakub Steć
#include "bitwise_operations.h"


bool LessThen(int set1, int set2) {
    if(Cardinality(set1) < Cardinality(set2)) {
        return 1;
    }
    else if(Cardinality(set1) > Cardinality(set2)) {
        return 0;
    }
    else {
        if((set1>=0 && set2<0) ||(set1<0 && set2>=0)) {
            bool tmp = set1 > set2;
            return tmp;
        } else {
            bool temp = set1 < set2;
            return temp;
        }
    }
}

bool GreatEqual(int set1, int set2) {
    return !    LessThen(set1,set2);
}



bool GreatThen(int set1, int set2) {
    if(Cardinality(set1) > Cardinality(set2)) {
        return 1;
    }
    else if(Cardinality(set1) < Cardinality(set2)) {
        return 0;
    }
    else {
        if((set1>=0 && set2<0) ||(set1<0 && set2>=0)) {
            bool tmp = set1 < set2;
            return tmp;
        } else {
            bool temp = set1 > set2;
            return temp;
        }
    }
}

bool LessEqual(int set1, int set2) {
    return !GreatThen(set1,set2);
}