// Jakub Steć
#include <string>
#include <cstdarg>

using namespace std;

string cutZeros(string& str, int index) {
    if(index == (str).size()) {
        return "0";
    }
    if((str)[index] == '0' || (str)[index] == '+' || (str)[index] == '-' ) {
        return cutZeros(str,index+1);
    }

    return (str).substr(index);
}

string cutZerosMain(string& str) {
    return cutZeros(str,0);
}

string reverseString(string& str, int index) {
    if(index >= str.length()) {
        return "";
    }

    return reverseString(str,index + 1) + str[index];
}

int sumTwoDigAndCarry(int s1, int s2, int& carry) {
    int sum = (s1 + s2 + carry) % 10;
    carry = (s1+s2+carry) / 10;
    return sum;
}

int subTwoDigAndDiff(int s1, int s2, int& carry) {
    int diff = (s1 - s2 - carry);
    if(diff < 0) {
        diff += 10;
        carry = 1;
    }
    else {
        carry = 0;
    }
    if((s1 - s2 - carry) < 0) {
        carry = 1;
    }
    else {
        carry = 0;
    }
    return diff;
}

void mySwap(string* s1, string* s2) {
    string tmp = *s2;
    *s2 = *s1;
    *s1 = tmp;
}

bool isNeg(string str) {
    if(str[0] == '-' ) {
        return true;
    }
    return false;
}

void mySumNegHelp(int& lens1, int& lens2, int& carry, string& sum1, string& sum2, string& sum, bool& s1neg, bool& isSubVSzero, bool& bothSub,bool& s2neg, bool& isSub) {
    if (lens1 >= 0) {
        int tmpSum;
        if (lens2 < 0) { // carry
            tmpSum = subTwoDigAndDiff(sum1[lens1] - '0', 0, carry);
            lens1--;
        } else {
            tmpSum = subTwoDigAndDiff(sum1[lens1] - '0', sum2[lens2] - '0', carry);
            lens1--;
            lens2--;
        }
        sum += (tmpSum + '0');
        mySumNegHelp(lens1, lens2, carry, sum1, sum2, sum, s1neg, isSubVSzero,bothSub, s2neg, isSub);
    }
}

void mySumElseHelp(int& lens1, int& lens2, int& carry, string& sum1, string& sum2, string& sum, bool& s1neg, bool& isSubVSzero,bool& bothSub, bool& s2neg, bool& isSub) {
    if (lens1 >= 0) {
        int tmpSum;
        if (lens2 < 0) { // carry
            tmpSum = sumTwoDigAndCarry(sum1[lens1] - '0', 0, carry);
            lens1--;
        } else {
            tmpSum = sumTwoDigAndCarry(sum1[lens1] - '0', sum2[lens2] - '0', carry);
            lens1--;
            lens2--;
        }
        if((s1neg && !s2neg) || (!s1neg && s2neg)) {
            isSub = true;
        }
        if(s1neg && s2neg) {
            bothSub = true;
        }
        sum += (tmpSum + '0');
        mySumElseHelp(lens1, lens2, carry, sum1, sum2, sum, s1neg,isSubVSzero,bothSub, s2neg, isSub);
    }
}

string mySum1(string s1, string s2) {

    string sum = "";
    int carry = 0;
    bool isSub = false;
    bool bothSub = false;
    bool isSubVSzero = false;
    bool rev = false;
    bool s1neg = isNeg(s1);
    bool s2neg = isNeg(s2);
    if((s1neg && !s2neg) || (!s1neg && s2neg)) {
        isSub = true;
    }
    if(s1neg && s2neg) {
        bothSub = true;
    }
    string sum1 = cutZerosMain(s1);
    string sum2 = cutZerosMain(s2);

    if(sum1 == "0" && sum2 == "0") {
        return "0";
    }

    if(sum2.length() > sum1.length() || (sum2.length() == sum1.length() && sum2 > sum1)) {
        mySwap(&sum1,&sum2);
        bool tmp = s1neg;
        s1neg = s2neg;
        s2neg = tmp;
    }

    if(s1neg && (sum2 == "0")) {
        isSubVSzero = true;
    }


    int lens1 = sum1.length() - 1;
    int lens2 = sum2.length() - 1;

    if(lens1 == lens2 && isSub) {
        rev = true;
    }

    if(isSub) {
        mySumNegHelp(lens1, lens2, carry, sum1, sum2, sum, s1neg,isSubVSzero,bothSub, s2neg, isSub);
    }
    else {
        mySumElseHelp(lens1, lens2, carry, sum1, sum2, sum,s1neg,isSubVSzero,bothSub, s2neg, isSub);
    }
    if(carry == 1) {
        sum += '1';
    }

    string y = "";
    if(rev) {
        y = reverseString(sum,0);
        y = cutZerosMain(y);
        y = reverseString(y,0);
    }
    else {
        cutZerosMain(sum);
    }
//    cutZerosMain(sum);

    if(y != "") {
        sum = y;
    }

    if(bothSub || (s1neg && !s2neg) || isSubVSzero) {
        if(sum != "0") {
            sum += '-';
        }
    }
    string x = reverseString(sum,0);
    string z = "";
    if(x[0] == '-' && x[1] == '0') {
        z = cutZerosMain(x);
        z = reverseString(z,0);
        z += '-';
        z = reverseString(z,0);
    }
    else if(x[0] == '0') {
        z = cutZerosMain(x);

    }
    if(z != "") {
        x = z;
    }

    return x;
}

string mySum2(const string* ar, int index, int numOfArgs) {

    if(index == numOfArgs - 1) {
        return ar[index];
    }

    return mySum1(ar[index], mySum2(ar, index + 1, numOfArgs));

}

string Sum(int, const string*);
string Sum(int, ...);
void Sum(string*, int, const string*);
void Sum(string*, int, ...);
void Sum(string&, int, const string* );
void Sum(string&, int, ...);

void fillArray(string arr[], va_list args, int i, int numOfArgs) {
    if(i >= numOfArgs) {
        return;
    }
    arr[i] = va_arg(args, char*);
    fillArray(arr, args, i + 1, numOfArgs);
}

string Sum(int numOfArgs, const string* args) {
    return mySum2(args,0,numOfArgs);
}

string Sum(int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArray(arr,args,0,numOfArgs);
    va_end(args);
    return Sum(numOfArgs, &arr[0]);
}

void Sum(string* sum, int numOfArgs, const string* arr) {
    *sum = Sum(numOfArgs,arr);
}


void Sum(string* sum, int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArray(arr,args,0,numOfArgs);
    va_end(args);
    *sum = Sum(numOfArgs, &arr[0]);
}

void Sum(string& sum, int numOfArgs, const string* arr) {
    sum = Sum(numOfArgs, arr);
}

void Sum(string& sum, int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArray(arr,args,0,numOfArgs);
    va_end(args);
    sum = Sum(numOfArgs, &arr[0]);
}
