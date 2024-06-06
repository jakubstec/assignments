// Jakub Steć

#include <iostream>

using namespace std;

long long int obliczWyznacznik(long long temparray[32][32], int dlugoscKrawedziSzescianu) {
    long long int wyz = 1;
    long long int tmp;
    long long int ratio;

    for (int i = 0; i < dlugoscKrawedziSzescianu; i++) {
        for (int j = i + 1; j < dlugoscKrawedziSzescianu; j++) {
            while (temparray[j][i] != 0) {
                ratio = temparray[i][i] / temparray[j][i];

                for (int k = 0; k < dlugoscKrawedziSzescianu; k++) {
                    temparray[i][k] = temparray[i][k] - (ratio * temparray[j][k]);
                    tmp = temparray[i][k];
                    temparray[i][k] = temparray[j][k];
                    temparray[j][k] = tmp;
                }

                wyz = wyz * (-1);
            }
        }
        wyz = wyz * temparray[i][i];
    }
    return wyz;
}


int main() {
    int dlugoscKrawedziSzescianu = 0;
    int indeks = 0;
    char operacja = 'X';
    char wymiar = 'X';
    cin >> dlugoscKrawedziSzescianu;

    long long int szesciandanych [32] [32] [32];
    long long int temparray[32][32];
    long long int suma = 0;

    for(int i = 0; i<dlugoscKrawedziSzescianu; i++) {
        for(int j=0; j<dlugoscKrawedziSzescianu; j++) {
            for(int k=0; k<dlugoscKrawedziSzescianu; k++) {
                cin >> szesciandanych[i][j][k]; // zyx
            }
        }
    }


    while(operacja!='E') {

        cin >> operacja;

        if(operacja=='C') {
            int polowa = dlugoscKrawedziSzescianu/2;
            int l=0;
            int v=0;
            int p=0;
            int h=0;
            int w=0;
            int d=0;
            int tmpl=1;
            int tmpv=1;
            int tmpp=1;
            cin >> l >> v  >> p >> h  >> w  >> d;

            if(l >= polowa) {
                tmpl = l * (-1);
            }

            if(v >= polowa) {
                tmpv = v * (-1);
            }

            if(p >= polowa) {
                tmpp = p * (-1);
            }

            if(l<0) {
                h = h - (l * (-1));
                l = 0;
            }
            if(v<0) {
                w = w - (v * (-1));
                v = 0;
            }
            if(p<0) {
                d = d - (p * (-1));
                p = 0;
            }
            suma = 0;


            if(l>=0 && v >= 0 && p >= 0) {
                for (int i = 0; i <= d; i++) {
                    for (int j = 0; j <= h; j++) {
                        for (int k = 0; k <= w; k++) {
                            suma += szesciandanych[p+(i*tmpp)][l+(j*tmpl)][v+(k*tmpv)];
                        }
                    }
                }
            }

            cout << suma << endl;
        }

        if(operacja=='T') {
            int polowa = dlugoscKrawedziSzescianu/2;
            int l=0;
            int v=0;
            int p=0;
            int e=0;
            int tmpl=1;
            int tmpv=1;
            int tmpp=1;
            cin >> l >> v  >> p >> e;

            if(l >= polowa) {
                tmpl = l * (-1);
            }

            if(v >= polowa) {
                tmpv = v * (-1);
            }

            if(p >= polowa) {
                tmpp = p * (-1);
            }

            if(l<0) {
                e = e - (l * (-1));
                l = 0;
            }
            if(v<0) {
                e = e - (v * (-1));
                v = 0;
            }
            if(p<0) {
                e = e - (p * (-1));
                p = 0;
            }

            suma = 0;
            if(l>=0 && v >= 0 && p>= 0) {
                for (int i = 0; i <= e; i++) {
                    for (int j = 0; j <= e-i; j++) {
                        for (int k = 0; k <= e-i-j; k++) {
                            suma += szesciandanych[p+(i*tmpp)][l+(j*tmpl)][v+(k*tmpv)];
                        }
                    }
                }
            }
            cout << suma << endl;
        }

        if(operacja=='O') {

            int polowa = dlugoscKrawedziSzescianu/2;
            int l=0;
            int v=0;
            int p=0;
            int r=0;
            int tmpl=1;
            int tmpv=1;
            int tmpp=1;
            long long suma = 0;
            cin >> l >> v  >> p >> r;

            if(l >= polowa) {
                tmpl = -1;
            }

            if(v >= polowa) {
                tmpv = -1;
            }

            if(p >= polowa) {
                tmpp = -1;
            }
                    for (int i = l; i <= l + r && i >= l - r; i = i + tmpl) {
                        for (int j = v; j <= v + r && j >= v - r; j = j + tmpv) {
                            for (int k = p; k <= p + r && k >= p - r; k = k + tmpp) {
                                if (i >= 0 && i < dlugoscKrawedziSzescianu && j >= 0 && j < dlugoscKrawedziSzescianu && k >= 0 && k < dlugoscKrawedziSzescianu) {
                                    if((p-k) * (p-k) + (l-i) * (l-i) + (v-j) * (v-j) <= r*r) {
                                        suma += szesciandanych[k][i][j];
                                    }
                                }
                            }
                        }
                    }

            cout << suma << endl;

        }

        if(operacja=='D') {
            cin >> wymiar >> indeks;

            if(wymiar == 'l') {

                for(int i=0; i<dlugoscKrawedziSzescianu; i++) {

                    for(int j=0; j<dlugoscKrawedziSzescianu; j++) {

                        temparray[i][j] = szesciandanych[i][indeks][j];

                    }
                }

                cout << obliczWyznacznik(temparray,dlugoscKrawedziSzescianu) << endl;

            }

            if(wymiar == 'v') {

                for(int i=0; i<dlugoscKrawedziSzescianu; i++) {

                    for(int j=0; j<dlugoscKrawedziSzescianu; j++) {

                        temparray[i][j] = szesciandanych[i][j][indeks];

                    }
                }
                cout << obliczWyznacznik(temparray,dlugoscKrawedziSzescianu) << endl;
            }
            if(wymiar == 'p') {
                for(int i=0; i<dlugoscKrawedziSzescianu; i++) {
                    for(int j=0; j<dlugoscKrawedziSzescianu; j++) {
                        temparray[i][j] = szesciandanych[indeks][i][j];
                    }
                }
                cout << obliczWyznacznik(temparray,dlugoscKrawedziSzescianu) << endl;
            }
        }
    }
    return 0;
}