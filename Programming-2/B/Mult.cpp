// Jakub Steć
#include <string>
#include <cstdarg>

using namespace std;

string Mult(int, const string*);
string Mult(int, ...);
void Mult(string*, int, const string*);
void Mult(string*, int, ...);
void Mult(string&, int, const string*);
void Mult(string&, int, ...);

string cutZerosMult(string& str, int index) {
    if(index == (str).size()) {
        return "0";
    }
    if((str)[index] == '0' || (str)[index] == '+' || (str)[index] == '-' ) {
        return cutZerosMult(str,index+1);
    }

    return (str).substr(index);
}

string cutZerosMultMain(string& str) {
    return cutZerosMult(str,0);
}

string reverseStringMult(string& str, int index) {
    if(index >= str.length()) {
        return "";
    }

    return reverseStringMult(str,index + 1) + str[index];
}
int sumTwoDigAndCarryMult(int s1, int s2, int& carry) {
    int sum = (s1 + s2 + carry) % 10;
    carry = (s1+s2+carry) / 10;
    return sum;
}

int subTwoDigAndDiffMult(int s1, int s2, int& carry) {
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

void mySwapMult(string* s1, string* s2) {
    string tmp = *s2;
    *s2 = *s1;
    *s1 = tmp;
}

bool isNegMult(string str) {
    if(str[0] == '-' ) {
        return true;
    }
    return false;
}



void mySumNegHelpMult(int& lens1, int& lens2, int& carry, string& sum1, string& sum2, string& sum) {
    if (lens1 >= 0) {
        int tmpSum;
        if (lens2 < 0) { // carry
            tmpSum = subTwoDigAndDiffMult(sum1[lens1] - '0', 0, carry);
            lens1--;
        } else {
            tmpSum = subTwoDigAndDiffMult(sum1[lens1] - '0', sum2[lens2] - '0', carry);
            lens1--;
            lens2--;
        }

        sum += (tmpSum + '0');
        mySumNegHelpMult(lens1, lens2, carry, sum1, sum2, sum);
    }
}


void mySumElseHelpMult(int& lens1, int& lens2, int& carry, string& sum1, string& sum2, string& sum) {
    if (lens1 >= 0) {
        int tmpSum;
        if (lens2 < 0) { // carry
            tmpSum = sumTwoDigAndCarryMult(sum1[lens1] - '0', 0, carry);
            lens1--;
        } else {
            tmpSum = sumTwoDigAndCarryMult(sum1[lens1] - '0', sum2[lens2] - '0', carry);
            lens1--;
            lens2--;
        }
        sum += tmpSum + '0';
        mySumElseHelpMult(lens1, lens2, carry, sum1, sum2, sum);
    }
}

void multiplyDigits( string& ans, string& nums1, string& nums2, int i, int j) {
    if (j < 0)
        return;

    int p = (nums1[i] - '0') * (nums2[j] - '0') + (ans[i + j + 1] - '0');
    ans[i + j + 1] = p % 10 + '0';
    ans[i + j] += p / 10;

    multiplyDigits(ans, nums1, nums2, i, j - 1);
}

void multiplyDigitsHelper(string& ans, string& nums1, string& nums2, int i, int m) {
    if (i < 0)
        return;

    multiplyDigits(ans, nums1, nums2, i, m - 1);
    multiplyDigitsHelper(ans, nums1, nums2, i - 1, m);
}

string addZeros(int len) {
    if (len == 0) {
        return "";
    }
    return "0" + addZeros(len - 1);
}

string multiply(string nums1, string nums2) {
    int n = nums1.size();
    int m = nums2.size();
    int len = n+m;
    string ans = "";
    ans = addZeros(len);
    multiplyDigitsHelper(ans, nums1, nums2, n - 1, m);
    return ans;
}


string mySum1Mult(string s1, string s2) {

    int carry = 0;
    bool isSub = false;
    bool bothSub = false;
    bool isSubVSzero = false;
    bool rev = false;
    bool s1neg = isNegMult(s1);
    bool s2neg = isNegMult(s2);
    if((s1neg && !s2neg) || (!s1neg && s2neg)) {
        isSub = true; // - z przodu
    }
    if(s1neg && s2neg) {
        bothSub = true; // plus z przodu
    }
    string sum1 = cutZerosMultMain(s1);
    string sum2 = cutZerosMultMain(s2);

    if(sum1 == "0" || sum2 == "0") {
        return "0";
    }

    if(sum2.length() > sum1.length() || (sum2.length() == sum1.length() && sum2 > sum1)) {
        mySwapMult(&sum1,&sum2);
        bool tmp = s1neg;
        s1neg = s2neg;
        s2neg = tmp;
    }

    if(sum2 == "1" && isSub) {
        string y = "";
        y = reverseStringMult(sum1,0);
        y += "-";
        y = reverseStringMult(y,0);
        return y;
    } else if(sum2 == "1" && (!s1neg && !s2neg)) {
        return sum1;
    }

    if(s1neg && (sum2 == "0")) {
        isSubVSzero = true;
    }

    int lens1 = sum1.size() -1 ;
    int lens2 = sum2.size() -1;
    string product = "";
    string prod = "";

    product = multiply(sum1,sum2);
    product = cutZerosMultMain(product);
    product = reverseStringMult(product,0);
    if(isSub) {
        product += "-";
        prod = reverseStringMult(product,0);
    }
    else {
        prod = reverseStringMult(product,0);
    }
    return prod;
}

string mySum2Mult(const string* ar, int index, int numOfArgs) {

    if(index == numOfArgs - 1) {
        return ar[index];
    }

    return mySum1Mult(ar[index], mySum2Mult(ar, index + 1, numOfArgs));

}
void fillArrayMult(string arr[], va_list args, int i, int numOfArgs) {
    if(i >= numOfArgs) {
        return;
    }
    arr[i] = va_arg(args, char*);
    fillArrayMult(arr, args, i + 1, numOfArgs);
}


string myMult2(const string* ar, int index, int numOfArgs) {

    if(index == numOfArgs - 1) {
        return ar[index];
    }

    return mySum1Mult(ar[index], myMult2(ar, index + 1, numOfArgs));

}

string Mult(int numOfArgs, const string* args) {
    return myMult2(args,0,numOfArgs);
}

string Mult(int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArrayMult(arr,args,0,numOfArgs);
    va_end(args);
    return Mult(numOfArgs, &arr[0]);
}

void Mult(string* mult, int numOfArgs, const string* arr) {
    *mult = Mult(numOfArgs,arr);
}


void Mult(string* mult, int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArrayMult(arr,args,0,numOfArgs);
    va_end(args);
    *mult = Mult(numOfArgs, &arr[0]);
}

void Mult(string& mult, int numOfArgs, const string* arr) {
    mult = Mult(numOfArgs, arr);
}

void Mult(string& mult, int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArrayMult(arr,args,0,numOfArgs);
    va_end(args);
    mult = Mult(numOfArgs, &arr[0]);
}