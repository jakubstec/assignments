// Jakub Steć
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

struct SET {
    int num;
    string txt;
    string ch;
    struct COMPONENT1 {
        bool c1;
        unsigned int c2;
        float c3;
    }; COMPONENT1 comp1, comp2, comp3, comp4;
};

int compareInts(SET& set1, SET& set2) {
    if(set1.num > set2.num) { return -1; }
    if(set1.num < set2.num) { return 1; }
    return 0;
}

int compareStrings(SET& set1, SET& set2) {
    if(set1.txt > set2.txt) { return -1; }
    if(set1.txt < set2.txt) { return 1; }
    return 0;
}

int sumChar(SET& set1) {
    unsigned char sum1 = (set1.comp1.c2 + set1.comp2.c2 + set1.comp3.c2 + set1.comp4.c2)%256;
    return sum1;
}

int compareChars(SET& set1, SET& set2) {
    unsigned char sum1 = (set1.comp1.c2 + set1.comp2.c2 + set1.comp3.c2 + set1.comp4.c2)%256;
    unsigned char sum2 = (set2.comp1.c2 + set2.comp2.c2 + set2.comp3.c2 + set2.comp4.c2)%256;
    if(sum1 > sum2) { return -1; }
    if(sum1 < sum2) { return 1; }
    return 0;
}
string findBiggestNum(fstream& f, string fname) {
    string line;
    f.open(fname.c_str(), fstream::in);
    getline(f,line);
    string big = line;
    int currline = 8;
    while(getline(f,line)) {
        if(currline%7==0) {
            if(line>big) {
                big = line;
            }
        }
        currline++;
    }
    f.close();
    return big;

}

int findrepetition(fstream& f1 , string f1name, int& currfileindex) {
    f1.open(f1name.c_str(), ios::in);
    int repcounter = 1;
    int currline = 0;
    string currnum;
    string line;
    string tmpline;
    if(currfileindex==0) {
        getline(f1,tmpline);
        currnum = tmpline;
    }
    f1.clear();
    f1.seekg(0, ios::beg);
    for(int i = 0; i < currfileindex; i++) {
        if(!getline(f1,tmpline)) {
            return 0;
        }
        if(i == currfileindex) {
            currnum = tmpline;
        }
    }

    currline = currfileindex+1;
    getline(f1,tmpline);
    currnum = tmpline;
    while(getline(f1,line)) {
        if(currline%7 == 0 ) {
            if(line == currnum) {
                repcounter++;
            }
        }
        currline++;
    }
    f1.close();
    return repcounter;
}

void fillSet(SET& s, fstream& f, string fname, int& currfileindex) {

    f.open(fname.c_str(), fstream::in | fstream::out);
    string line;
    // line skip
    for(int i = 0; i < currfileindex; i++) {
        string tmpline;
        getline(f,tmpline);
    }
    f >> s.num;
    getline(f,line);
    getline(f,line);
    s.txt = line;
    getline(f,line);
    s.ch = line;
    f >> s.comp1.c1;
    f >> s.comp1.c2;
    f >> s.comp1.c3;
    getline(f,line);
    f >> s.comp2.c1;
    f >> s.comp2.c2;
    f >> s.comp2.c3;
    getline(f,line);
    f >> s.comp3.c1;
    f >> s.comp3.c2;
    f >> s.comp3.c3;
    getline(f,line);
    f >> s.comp4.c1;
    f >> s.comp4.c2;
    f >> s.comp4.c3;
    getline(f,line);
    f.close();
}

string currindexnumber(fstream& f, string fname, int& currfileindex) {
    // line skip
    f.open(fname.c_str(), ios::in);
    string tmpline;
    for(int i = 0; i < currfileindex; i++) {
        getline(f,tmpline);
    }
    getline(f,tmpline);
    f.close();
    return tmpline;

}

bool comparesets(fstream& f1, fstream& f2, string f1name, string f2name, int& currindexfile1, int& currindexfile2) {

    SET s1, s2; // s1 - file1, s2 - file2

    fillSet(s1, f1, f1name, currindexfile1);
    fillSet(s2, f2, f2name, currindexfile2);

    if(
            s1.num == s2.num &&
            s1.txt == s2.txt &&
            s1.ch == s2.ch &&
            s1.comp1.c1 == s2.comp1.c1 &&
            s1.comp1.c2 == s2.comp1.c2 &&
            s1.comp1.c3 == s2.comp1.c3 &&
            s1.comp2.c1 == s2.comp2.c1 &&
            s1.comp2.c2 == s2.comp2.c2 &&
            s1.comp2.c3 == s2.comp2.c3 &&
            s1.comp3.c1 == s2.comp3.c1 &&
            s1.comp3.c2 == s2.comp3.c2 &&
            s1.comp3.c3 == s2.comp3.c3 &&
            s1.comp4.c1 == s2.comp4.c1 &&
            s1.comp4.c2 == s2.comp4.c2 &&
            s1.comp4.c3 == s2.comp4.c3
            )
    {
        return true;
    }
    return false;
}

int currindex(fstream& f, string fname, string& currnumber) {
    int index = 0;
    string line;
    f.open(fname.c_str(), ios::in);
    while(getline(f,line)) {
        if(line == currnumber) {
            f.close();
            return index;
        }
        index++;
    }
    f.close();
    return index;
}

void SymmetricDifference(string f1name, string f2name, string f3name) {
    fstream f1,f2,f3;
    f2.open(f2name.c_str(), fstream::in);
    f3.open(f3name.c_str(), fstream::out);
    string l;

    // move data from file2 -> file3
    while(getline(f2, l)) {
        f3 << l << "\n";
    }
    f2.close();
    f3.close();

    // clear file2
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::in | fstream::out);
    f3.open(f3name.c_str(), fstream::in | fstream::out);

    int currindexfile1 = 0;
    int currindexfile3 = 0;

    string f1line;
    string f3line;
    int f1linecounter = 0;
    int f3linecounter = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    while (getline(f3, f3line)) {
        f3linecounter++;
    }

    f1.close();
    f3.close();

    bool contains;
    int i = 0;

    while(currindexfile3<f3linecounter) {

        contains = false;
        currindexfile1 = 0;
        while(currindexfile1<f1linecounter) {
            if(comparesets(f1,f3,f1name,f3name,currindexfile1,currindexfile3) == 1) {
                contains = true;
                break;
            }
            currindexfile1+=7;
        }
        if(contains) {
            f2.open(f2name.c_str(), fstream::out | fstream::app);
            SET tmpset;
            fillSet(tmpset,f3,f3name,currindexfile3);
            f2 << tmpset.num << "\n";
            f2 << tmpset.txt << "\n";
            f2 << tmpset.ch << "\n";
            f2 << tmpset.comp1.c1 << " ";
            f2 << tmpset.comp1.c2 << " ";
            f2 << tmpset.comp1.c3 << "\n";
            f2 << tmpset.comp2.c1 << " ";
            f2 << tmpset.comp2.c2 << " ";
            f2 << tmpset.comp2.c3 << "\n";
            f2 << tmpset.comp3.c1 << " ";
            f2 << tmpset.comp3.c2 << " ";
            f2 << tmpset.comp3.c3 << "\n";
            f2 << tmpset.comp4.c1 << " ";
            f2 << tmpset.comp4.c2 << " ";
            f2 << tmpset.comp4.c3 << "\n";
            f2.close();
        }
        else {
            f1.open(f1name.c_str(), fstream::out | fstream::app);
            SET tmpset;
            fillSet(tmpset,f3,f3name,currindexfile3);
            f1 << tmpset.num << "\n";
            f1 << tmpset.txt << "\n";
            f1 << tmpset.ch << "\n";
            f1 << tmpset.comp1.c1 << " ";
            f1 << tmpset.comp1.c2 << " ";
            f1 << tmpset.comp1.c3 << "\n";
            f1 << tmpset.comp2.c1 << " ";
            f1 << tmpset.comp2.c2 << " ";
            f1 << tmpset.comp2.c3 << "\n";
            f1 << tmpset.comp3.c1 << " ";
            f1 << tmpset.comp3.c2 << " ";
            f1 << tmpset.comp3.c3 << "\n";
            f1 << tmpset.comp4.c1 << " ";
            f1 << tmpset.comp4.c2 << " ";
            f1 << tmpset.comp4.c3 << "\n";
            f1.close();
            f1linecounter+=7;
        }
        currindexfile3+=7;
    }

    // clear file3
    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

void SortIntDesc(string f1name, string f2name, string f3name) {

    fstream f1,f2,f3;

    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

    f1.open(f1name.c_str(), fstream::in);
    string f1line;
    int f1linecounter = 0;
    int currline = 0;
    int tmpline = 0;
    int minindex = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    f1.close();

    int numofsets = f1linecounter/7;


    while(numofsets--) {
        f1linecounter = 0;
        f1.open(f1name.c_str(), fstream::in);
        while (getline(f1, f1line)) {
            f1linecounter++;
        }
        f1.close();
        if(f1linecounter == 0 ) {break;}
        numofsets = f1linecounter/7;
        string biggie = findBiggestNum(f1,f1name);
        int biggieindex = currindex(f1, f1name,biggie);
        SET min;
        fillSet(min, f1, f1name, biggieindex);
        f3.open(f3name.c_str(), fstream::out | fstream::app);
        f3 << min.num << "\n";
        f3 << min.txt << "\n";
        f3 << min.ch << "\n";
        f3 << min.comp1.c1 << " ";
        f3 << min.comp1.c2 << " ";
        f3 << min.comp1.c3 << "\n";
        f3 << min.comp2.c1 << " ";
        f3 << min.comp2.c2 << " ";
        f3 << min.comp2.c3 << "\n";
        f3 << min.comp3.c1 << " ";
        f3 << min.comp3.c2 << " ";
        f3 << min.comp3.c3 << "\n";
        f3 << min.comp4.c1 << " ";
        f3 << min.comp4.c2 << " ";
        f3 << min.comp4.c3 << "\n";
        f3.close();

        for (int i = 0; i < f1linecounter; i += 7) {
            if (i != biggieindex) {
                SET s;
                fillSet(s, f1, f1name, i);
                f2.open(f2name.c_str(), fstream::out | fstream::app);
                f2 << s.num << "\n";
                f2 << s.txt << "\n";
                f2 << s.ch << "\n";
                f2 << s.comp1.c1 << " ";
                f2 << s.comp1.c2 << " ";
                f2 << s.comp1.c3 << "\n";
                f2 << s.comp2.c1 << " ";
                f2 << s.comp2.c2 << " ";
                f2 << s.comp2.c3 << "\n";
                f2 << s.comp3.c1 << " ";
                f2 << s.comp3.c2 << " ";
                f2 << s.comp3.c3 << "\n";
                f2 << s.comp4.c1 << " ";
                f2 << s.comp4.c2 << " ";
                f2 << s.comp4.c3 << "\n";
                f2.close();
            }
        }


        f1.open(f1name.c_str(), fstream::out);
        f2.open(f2name.c_str(), fstream::in);
        string es;
        while (getline(f2, es)) {
            f1 << es << "\n";
        }
        f1.close();
        f2.close();

        f2.open(f2name.c_str(), fstream::out | fstream::trunc);
        f2.close();
    }

    f1.open(f1name.c_str(), fstream::out | fstream::trunc);
    f1.close();
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::out);
    f3.open(f3name.c_str(), fstream::in);
    string sa;
    while (getline(f3, sa)) {
        f1 << sa << "\n";
    }
    f1.close();
    f3.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

int findBiggestChar(fstream& f, string fname, int linecount) {
    SET s1,s2;
    int zero = 0;
    int currindex = 0;
    fillSet(s1,f,fname,zero);
    int big = sumChar(s1);
    for(int i = 7; i < linecount; i += 7) {
        fillSet(s2,f,fname,i);
        int tmp = sumChar(s2);
        if(tmp>big) {
            big = tmp;
            currindex = i;
        }
    }
    return currindex;

}

void SortChars2(string f1name, string f2name, string f3name) {
    fstream f1,f2,f3;

    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

    f1.open(f1name.c_str(), fstream::in);
    string f1line;
    int f1linecounter = 0;
    int currline = 0;
    int tmpline = 0;
    int minindex = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    f1.close();

    int numofsets = f1linecounter/7;

    while(numofsets--) {
        f1linecounter = 0;
        f1.open(f1name.c_str(), fstream::in);
        while (getline(f1, f1line)) {
            f1linecounter++;
        }
        f1.close();
        if(f1linecounter == 0 ) {break;}
        numofsets = f1linecounter/7;
        int biggieindex = findBiggestChar(f1,f1name,f1linecounter);
        SET min;
        fillSet(min, f1, f1name, biggieindex);
        f3.open(f3name.c_str(), fstream::out | fstream::app);
        f3 << min.num << "\n";
        f3 << min.txt << "\n";
        f3 << min.ch << "\n";
        f3 << min.comp1.c1 << " ";
        f3 << min.comp1.c2 << " ";
        f3 << min.comp1.c3 << "\n";
        f3 << min.comp2.c1 << " ";
        f3 << min.comp2.c2 << " ";
        f3 << min.comp2.c3 << "\n";
        f3 << min.comp3.c1 << " ";
        f3 << min.comp3.c2 << " ";
        f3 << min.comp3.c3 << "\n";
        f3 << min.comp4.c1 << " ";
        f3 << min.comp4.c2 << " ";
        f3 << min.comp4.c3 << "\n";
        f3.close();

        for (int i = 0; i < f1linecounter; i += 7) {
            if (i != biggieindex) {
                SET s;
                fillSet(s, f1, f1name, i);
                f2.open(f2name.c_str(), fstream::out | fstream::app);
                f2 << s.num << "\n";
                f2 << s.txt << "\n";
                f2 << s.ch << "\n";
                f2 << s.comp1.c1 << " ";
                f2 << s.comp1.c2 << " ";
                f2 << s.comp1.c3 << "\n";
                f2 << s.comp2.c1 << " ";
                f2 << s.comp2.c2 << " ";
                f2 << s.comp2.c3 << "\n";
                f2 << s.comp3.c1 << " ";
                f2 << s.comp3.c2 << " ";
                f2 << s.comp3.c3 << "\n";
                f2 << s.comp4.c1 << " ";
                f2 << s.comp4.c2 << " ";
                f2 << s.comp4.c3 << "\n";
                f2.close();
            }
        }


        f1.open(f1name.c_str(), fstream::out);
        f2.open(f2name.c_str(), fstream::in);
        string es;
        while (getline(f2, es)) {
            f1 << es << "\n";
        }
        f1.close();
        f2.close();

        f2.open(f2name.c_str(), fstream::out | fstream::trunc);
        f2.close();
    }

    f1.open(f1name.c_str(), fstream::out | fstream::trunc);
    f1.close();
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::out);
    f3.open(f3name.c_str(), fstream::in);
    string sa;
    while (getline(f3, sa)) {
        f1 << sa << "\n";
    }
    f1.close();
    f3.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

void SortChars(string f1name, string f2name, string f3name) {

    fstream f1,f2,f3;

    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

    f1.open(f1name.c_str(), fstream::in);
    string f1line;
    int f1linecounter = 0;
    int currline = 0;
    int tmpline = 0;
    int minindex = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    f1.close();

    int numofsets = f1linecounter/7;


    while(numofsets--) {
        SET s1,s2;
        minindex = 0;
        currline = 0;
        tmpline = minindex + 7;
        f1linecounter = numofsets * 7;
        while (currline < f1linecounter) {

            fillSet(s1, f1, f1name, minindex);
            while (tmpline <= f1linecounter) {
                fillSet(s2, f1, f1name, tmpline);

                if (compareChars(s1, s2) == 1) {
                    minindex = tmpline;
                    break;
                }
                tmpline += 7;
            }
            currline += 7;
            tmpline = minindex + 7;
        }
        SET min;
        fillSet(min, f1, f1name, minindex);
        f3.open(f3name.c_str(), fstream::out | fstream::app);
        f3 << min.num << "\n";
        f3 << min.txt << "\n";
        f3 << min.ch << "\n";
        f3 << min.comp1.c1 << " ";
        f3 << min.comp1.c2 << " ";
        f3 << min.comp1.c3 << "\n";
        f3 << min.comp2.c1 << " ";
        f3 << min.comp2.c2 << " ";
        f3 << min.comp2.c3 << "\n";
        f3 << min.comp3.c1 << " ";
        f3 << min.comp3.c2 << " ";
        f3 << min.comp3.c3 << "\n";
        f3 << min.comp4.c1 << " ";
        f3 << min.comp4.c2 << " ";
        f3 << min.comp4.c3 << "\n";
        f3.close();

        for (int i = 0; i <= f1linecounter; i += 7) {
            if (i != minindex) {
                SET s;
                fillSet(s, f1, f1name, i);
                f2.open(f2name.c_str(), fstream::out | fstream::app);
                f2 << s.num << "\n";
                f2 << s.txt << "\n";
                f2 << s.ch << "\n";
                f2 << s.comp1.c1 << " ";
                f2 << s.comp1.c2 << " ";
                f2 << s.comp1.c3 << "\n";
                f2 << s.comp2.c1 << " ";
                f2 << s.comp2.c2 << " ";
                f2 << s.comp2.c3 << "\n";
                f2 << s.comp3.c1 << " ";
                f2 << s.comp3.c2 << " ";
                f2 << s.comp3.c3 << "\n";
                f2 << s.comp4.c1 << " ";
                f2 << s.comp4.c2 << " ";
                f2 << s.comp4.c3 << "\n";
                f2.close();
            }
        }


        f1.open(f1name.c_str(), fstream::out);
        f2.open(f2name.c_str(), fstream::in);
        string es;
        while (getline(f2, es)) {
            f1 << es << "\n";
        }
        f1.close();
        f2.close();

        f2.open(f2name.c_str(), fstream::out | fstream::trunc);
        f2.close();
    }

    f1.open(f1name.c_str(), fstream::out | fstream::trunc);
    f1.close();
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::out);
    f3.open(f3name.c_str(), fstream::in);
    string sa;
    while (getline(f3, sa)) {
        f1 << sa << "\n";
    }
    f1.close();
    f3.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

void SortInt(string f1name, string f2name, string f3name) {

    fstream f1,f2,f3;

    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

    f1.open(f1name.c_str(), fstream::in);
    string f1line;
    int f1linecounter = 0;
    int currline = 0;
    int tmpline = 0;
    int minindex = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    f1.close();

    int numofsets = f1linecounter/7;


    while(numofsets--) {
        SET s1,s2;
        minindex = 0;
        currline = 0;
        tmpline = minindex + 7;
        f1linecounter = numofsets * 7;
        while (currline < f1linecounter) {

            fillSet(s1, f1, f1name, minindex);
            while (tmpline <= f1linecounter) {
                fillSet(s2, f1, f1name, tmpline);

                if (compareInts(s1, s2) == -1) {
                    minindex = tmpline;
                    break;
                }
                tmpline += 7;
            }
            currline += 7;
            tmpline = minindex + 7;
        }
        SET min;
        fillSet(min, f1, f1name, minindex);
        f3.open(f3name.c_str(), fstream::out | fstream::app);
        f3 << min.num << "\n";
        f3 << min.txt << "\n";
        f3 << min.ch << "\n";
        f3 << min.comp1.c1 << " ";
        f3 << min.comp1.c2 << " ";
        f3 << min.comp1.c3 << "\n";
        f3 << min.comp2.c1 << " ";
        f3 << min.comp2.c2 << " ";
        f3 << min.comp2.c3 << "\n";
        f3 << min.comp3.c1 << " ";
        f3 << min.comp3.c2 << " ";
        f3 << min.comp3.c3 << "\n";
        f3 << min.comp4.c1 << " ";
        f3 << min.comp4.c2 << " ";
        f3 << min.comp4.c3 << "\n";
        f3.close();

        for (int i = 0; i <= f1linecounter; i += 7) {
            if (i != minindex) {
                SET s;
                fillSet(s, f1, f1name, i);
                f2.open(f2name.c_str(), fstream::out | fstream::app);
                f2 << s.num << "\n";
                f2 << s.txt << "\n";
                f2 << s.ch << "\n";
                f2 << s.comp1.c1 << " ";
                f2 << s.comp1.c2 << " ";
                f2 << s.comp1.c3 << "\n";
                f2 << s.comp2.c1 << " ";
                f2 << s.comp2.c2 << " ";
                f2 << s.comp2.c3 << "\n";
                f2 << s.comp3.c1 << " ";
                f2 << s.comp3.c2 << " ";
                f2 << s.comp3.c3 << "\n";
                f2 << s.comp4.c1 << " ";
                f2 << s.comp4.c2 << " ";
                f2 << s.comp4.c3 << "\n";
                f2.close();
            }
        }


        f1.open(f1name.c_str(), fstream::out);
        f2.open(f2name.c_str(), fstream::in);
        string es;
        while (getline(f2, es)) {
            f1 << es << "\n";
        }
        f1.close();
        f2.close();

        f2.open(f2name.c_str(), fstream::out | fstream::trunc);
        f2.close();
    }

    f1.open(f1name.c_str(), fstream::out | fstream::trunc);
    f1.close();
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::out);
    f3.open(f3name.c_str(), fstream::in);
    string sa;
    while (getline(f3, sa)) {
        f1 << sa << "\n";
    }
    f1.close();
    f3.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

void SortString(string f1name, string f2name, string f3name) {

    fstream f1,f2,f3;

    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

    f1.open(f1name.c_str(), fstream::in);
    string f1line;
    int f1linecounter = 0;
    int currline = 0;
    int tmpline = 0;
    int minindex = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    f1.close();

    int numofsets = f1linecounter/7;


    while(numofsets--) {
        SET s1,s2;
        minindex = 0;
        currline = 0;
        tmpline = minindex + 7;
        f1linecounter = numofsets * 7;
        while (currline < f1linecounter) {

            fillSet(s1, f1, f1name, minindex);
            while (tmpline <= f1linecounter) {
                fillSet(s2, f1, f1name, tmpline);

                if (compareStrings(s1, s2) == -1) {
                    minindex = tmpline;
                    break;
                }
                tmpline += 7;
            }
            currline += 7;
            tmpline = minindex + 7;
        }
        SET min;
        fillSet(min, f1, f1name, minindex);
        f3.open(f3name.c_str(), fstream::out | fstream::app);
        f3 << min.num << "\n";
        f3 << min.txt << "\n";
        f3 << min.ch << "\n";
        f3 << min.comp1.c1 << " ";
        f3 << min.comp1.c2 << " ";
        f3 << min.comp1.c3 << "\n";
        f3 << min.comp2.c1 << " ";
        f3 << min.comp2.c2 << " ";
        f3 << min.comp2.c3 << "\n";
        f3 << min.comp3.c1 << " ";
        f3 << min.comp3.c2 << " ";
        f3 << min.comp3.c3 << "\n";
        f3 << min.comp4.c1 << " ";
        f3 << min.comp4.c2 << " ";
        f3 << min.comp4.c3 << "\n";
        f3.close();

        for (int i = 0; i <= f1linecounter; i += 7) {
            if (i != minindex) {
                SET s;
                fillSet(s, f1, f1name, i);
                f2.open(f2name.c_str(), fstream::out | fstream::app);
                f2 << s.num << "\n";
                f2 << s.txt << "\n";
                f2 << s.ch << "\n";
                f2 << s.comp1.c1 << " ";
                f2 << s.comp1.c2 << " ";
                f2 << s.comp1.c3 << "\n";
                f2 << s.comp2.c1 << " ";
                f2 << s.comp2.c2 << " ";
                f2 << s.comp2.c3 << "\n";
                f2 << s.comp3.c1 << " ";
                f2 << s.comp3.c2 << " ";
                f2 << s.comp3.c3 << "\n";
                f2 << s.comp4.c1 << " ";
                f2 << s.comp4.c2 << " ";
                f2 << s.comp4.c3 << "\n";
                f2.close();
            }
        }


        f1.open(f1name.c_str(), fstream::out);
        f2.open(f2name.c_str(), fstream::in);
        string es;
        while (getline(f2, es)) {
            f1 << es << "\n";
        }
        f1.close();
        f2.close();

        f2.open(f2name.c_str(), fstream::out | fstream::trunc);
        f2.close();
    }

    f1.open(f1name.c_str(), fstream::out | fstream::trunc);
    f1.close();
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::out);
    f3.open(f3name.c_str(), fstream::in);
    string sa;
    while (getline(f3, sa)) {
        f1 << sa << "\n";
    }
    f1.close();
    f3.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

int search(fstream& f, string fname, string tmpnum, int& currposs) {
    f.open(fname.c_str(),fstream::in);
    string line;
    int linecounter = 0;
    for(int i = 0; i < currposs; i++) {
        getline(f,line);
        linecounter++;
    }
    while(getline(f,line)) {
        if(line == tmpnum) {
            currposs = linecounter;
            break;
        }
        linecounter++;
    }
    f.close();
    return linecounter;
}

void SortRepetition(string f1name, string f2name, string f3name) {
    fstream f1,f2,f3;

    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

    f1.open(f1name.c_str(), fstream::in);
    string f1line;
    int f1linecounter = 0;
    int currline = 0;
    int tmpline = 0;
    int minindex = 0;
    while (getline(f1, f1line)) {
        f1linecounter++;
    }
    f1.close();

    int numofsets = f1linecounter/7;


    while(numofsets>0) {
        SET s1,s2;
        minindex = 0;
        currline = 0;
        int repetition1 = 0;
        int repetition2 = 0;
        tmpline = minindex + 7;
        f1linecounter = numofsets * 7;
        while (currline < f1linecounter) {
            fillSet(s1, f1, f1name, minindex);
            repetition1 = findrepetition(f1, f1name, minindex);
            while (tmpline <= f1linecounter) {
                fillSet(s2, f1, f1name, tmpline);
                repetition2 = findrepetition(f1, f1name, tmpline);
                if (repetition1<repetition2) {
                    minindex = tmpline;
                    break;
                }
                tmpline += 7;
            }
            currline += 7;
            tmpline = minindex + 7;
        }
        int tmprepet = findrepetition(f1, f1name, minindex);

        numofsets -= tmprepet;
        string tmpnum = currindexnumber(f1,f1name,minindex);
        int currposs = 0;
        while(tmprepet--) {
            int idx = currindex(f1, f1name, tmpnum);
            minindex = search(f1,f1name,tmpnum,currposs);
            SET min;
            fillSet(min, f1, f1name, minindex);
            f3.open(f3name.c_str(), fstream::out | fstream::app);
            f3 << min.num << "\n";
            f3 << min.txt << "\n";
            f3 << min.ch << "\n";
            f3 << min.comp1.c1 << " ";
            f3 << min.comp1.c2 << " ";
            f3 << min.comp1.c3 << "\n";
            f3 << min.comp2.c1 << " ";
            f3 << min.comp2.c2 << " ";
            f3 << min.comp2.c3 << "\n";
            f3 << min.comp3.c1 << " ";
            f3 << min.comp3.c2 << " ";
            f3 << min.comp3.c3 << "\n";
            f3 << min.comp4.c1 << " ";
            f3 << min.comp4.c2 << " ";
            f3 << min.comp4.c3 << "\n";
            f3.close();
            currposs++;
        }

        for (int i = 0; i < f1linecounter; i += 7) {
            string numnum = currindexnumber(f1, f1name, i);
            if (numnum != tmpnum) {
                SET s;
                fillSet(s, f1, f1name, i);
                f2.open(f2name.c_str(), fstream::out | fstream::app);
                f2 << s.num << "\n";
                f2 << s.txt << "\n";
                f2 << s.ch << "\n";
                f2 << s.comp1.c1 << " ";
                f2 << s.comp1.c2 << " ";
                f2 << s.comp1.c3 << "\n";
                f2 << s.comp2.c1 << " ";
                f2 << s.comp2.c2 << " ";
                f2 << s.comp2.c3 << "\n";
                f2 << s.comp3.c1 << " ";
                f2 << s.comp3.c2 << " ";
                f2 << s.comp3.c3 << "\n";
                f2 << s.comp4.c1 << " ";
                f2 << s.comp4.c2 << " ";
                f2 << s.comp4.c3 << "\n";
                f2.close();
            }
        }


        f1.open(f1name.c_str(), fstream::out);
        f2.open(f2name.c_str(), fstream::in);
        string es;
        while (getline(f2, es)) {
            f1 << es << "\n";
        }
        f1.close();
        f2.close();

        f2.open(f2name.c_str(), fstream::out | fstream::trunc);
        f2.close();
    }

    f1.open(f1name.c_str(), fstream::out | fstream::trunc);
    f1.close();
    f2.open(f2name.c_str(), fstream::out | fstream::trunc);
    f2.close();

    f1.open(f1name.c_str(), fstream::out);
    f3.open(f3name.c_str(), fstream::in);
    string sa;
    while (getline(f3, sa)) {
        f1 << sa << "\n";
    }
    f1.close();
    f3.close();

    f3.open(f3name.c_str(), fstream::out | fstream::trunc);
    f3.close();

}

void SortCount(string f1name, string f2name, string f3name) {
    fstream f1, f2, f3;
    SortChars2(f1name,f2name,f3name);
    SortIntDesc(f1name,f2name,f3name);
    SortRepetition(f1name,f2name,f3name);
}