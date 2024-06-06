// Jakub Steć
#include <string>
#include <cstdarg>

using namespace std;

string Operation (string (*)(int, const string*),int, const string*);
string Operation (string(*)(int,const string*),int,...);
void Operation(string*, string(*)(int,const string*),int,...);
void Operation(string*, string(*)(int,const string*),int,const string*);
void Operation(string&, void(*)(string*, int, const string*), int, const string*);
void Operation(string&, void(*)(string*, int, const string*), int, ...);

void fillArrayOper(string arr[], va_list args, int i, int numOfArgs) {
    if(i >= numOfArgs) {
        return;
    }
    arr[i] = va_arg(args, char*);
    fillArrayOper(arr, args, i + 1, numOfArgs);
}

string Operation (string (*oper)(int, const string*),int numOfArgs, const string* arr) {
    return oper(numOfArgs,arr);
}

string Operation (string (*oper)(int,const string*),int numOfArgs,...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArrayOper(arr,args,0,numOfArgs);
    va_end(args);
    return oper(numOfArgs, &arr[0]);
}

void Operation(string* result, string(*oper)(int,const string*),int numOfArgs,...) {;
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    fillArrayOper(arr,args,0,numOfArgs);
    va_end(args);
    *result = oper(numOfArgs, &arr[0]);
}

void Operation(string* result, string(*oper)(int,const string*),int numOfArgs,const string* arr) {
    *result = oper(numOfArgs,arr);
}

void Operation(string& result, void(*oper)(string*, int, const string*), int numOfArgs, const string* arr) {
    string* ptr = new string[numOfArgs];
    oper(ptr,numOfArgs, arr);
    result = *ptr;
    delete[] ptr;
}
void Operation(string& result, void(*oper)(string*, int, const string*), int numOfArgs, ...) {
    va_list args;
    va_start(args, numOfArgs);
    string arr[numOfArgs];
    string* ptr = new string[numOfArgs];
    fillArrayOper(arr,args,0,numOfArgs);
    oper(ptr,numOfArgs,arr);
    va_end(args);
    result = *ptr;
    delete[] ptr;
}