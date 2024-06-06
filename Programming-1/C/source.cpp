// Jakub Steć

void Cardinality(int tab[], int *x) {
    *x = 0;
    for (int i = 0; tab[i] != -1; i++) {
        (*x)++;
    }
}

void sort(int tab[]) {
    int tmp = 0;
    bool swap;
    int k = 0;
    for(int i = 0; tab[i] != -1; i++) {
        k++;
    }

    for(int i=0; i<k-1; i++)
    {
        swap = false;
        for (int j = 0; j < k-i-1; j++)
        {
            if (tab[j] > tab[j + 1])
            {
                tmp = tab[j + 1];
                tab[j + 1] = tab[j];
                tab[j] = tmp;
                swap = true;
            }
        }
        if(!swap) {
            break;
        }
    }
}

bool Element(int x, int tab[]) {
    for (int i = 0; tab[i] != -1; i++) {
        if (tab[i] == x) {
            return true;
        }
    }
    return false;
}

bool isValid(int x) {
    if(x >= 1 && x <= 4095) {
        return true;
    }
    return false;
}

void Add(int x, int tab[]) {
    if (isValid(x) && !Element(x, tab)) {
        int j = 0;
        while(tab[j] != -1) {
            j++;
        }
        tab[j] = x;
        tab[j + 1] = -1;
    }
    sort(tab);
}

void Create(int x, int secondTab[], int tab[]) {
    int k = 0;
    int j = 0;
    while(x>0) {
        if(isValid(secondTab[k]) && !Element(secondTab[k],tab)) {
            tab[j] = secondTab[k];
            j++;
        }
        k++;
        x--;
    }
    tab[j] = -1;
    sort(tab);
}



void Union(int tab1[], int tab2[], int sum[]) {
    int k = 0;
    for(int i=0; tab1[i]!=-1; i++) {
        sum[k] = tab1[i];
        k++;
    }

    for(int j=0; tab2[j]!=-1; j++) {
        if(!Element(tab2[j],tab1)) {
            sum[k] = tab2[j];
            k++;
        }
    }
    sum[k] = -1;
    sort(sum);
}

void Intersection(int tab1[], int tab2[], int eq[]) {
    int j = 0;
    for(int i=0; tab1[i]!=-1; i++) {
        if(Element(tab1[i],tab2)) {
            eq[j] = tab1[i];
            j++;
        }
    }
    eq[j] = -1;
    sort(eq);
}

void Difference(int tab1[],int tab2[], int diff[]) {
    int j = 0;
    for(int i=0; tab1[i]!=-1; i++) {
        if(!Element(tab1[i],tab2)) {
            diff[j] = tab1[i];
            j++;
        }
    }
    diff[j] = -1;
    sort(diff);
}

void Symmetric(int tab1[],int tab2[], int symmdiff[]) {
    
    int k = 0;
    for(int i=0; tab1[i]!=-1; i++) {
        if(!Element(tab1[i],tab2)) {
            symmdiff[k] = tab1[i];
            k++;
        }
    }
    for(int i=0; tab2[i]!=-1; i++) {
        if(!Element(tab2[i],tab1)) {
            symmdiff[k] = tab2[i];
            k++;
        }
    }
    symmdiff[k] = -1;
    sort(symmdiff);
}

void Complement(int dop1[], int dop2[]) {
    int k = 0;
    for(int i=1; i<4096; i++) {
        if(!Element(i,dop1)) {
            dop2[k] = i;
            k++;
        }
    }
    dop2[k] = -1;
    sort(dop2);
}

bool Subset(int tab1[],int tab2[]) {
    bool in;
    for(int i=0; tab1[i]!=-1; i++) {
        in = false;
        for(int j=0; tab2[j]!=-1; j++) {
            if(tab1[i]==tab2[j]) {
                in = true;
                break;
            }
        }
        if(!in) {
            return false;
        }
    }

    if(in) {
        return true;
    } else {
        return false;
    }
}

bool Equal(int tab1[], int tab2[]) {
    if((Subset(tab1,tab2)) && (Subset(tab2,tab1))) {
        return true;
    }
    return false;
}

bool Empty(int tab1[]) {
    if(tab1[0]==-1) {
        return true;
    }
    return false;
}

bool Nonempty(int tab1[]) {
    if(tab1[0]!=-1) {
        return true;
    }
    return false;
}

double Arithmetic(int tab1[]) {
    int k = 0;
    double sum=0;
    double avg=0;

    if(tab1[0] == -1) {
        return avg;
    }
    else {
        while(tab1[k]!=-1) {
            k++;
        }
        for(int i=0; tab1[i]!=-1; i++) {
            sum += tab1[i];
        }
        double count = k;
        avg = sum / k;
        return avg;
    }
}

double Harmonic(int tab1[]) {
    int k = 0;
    double sum=0;
    double avgharmonic=1;

    if(tab1[0] == -1) {
        return avgharmonic;
    }
    else {
        while(tab1[k]!=-1) {
            k++;
        }
        for(int i=0; tab1[i]!=-1; i++) {
            sum += 1/(tab1[i]*1.0) ;
        }
        double count = k;
        avgharmonic = count / sum;
        return avgharmonic;
    }
}

void MinMax(int tab1[], int* min, int& max) {
    if(!Empty(tab1)) {
        *min = tab1[0], max = tab1[0];
        for(int i=1; tab1[i] != -1; i++) {
            if(tab1[i]<*min) {
                *min = tab1[i];
            }
            if(tab1[i]>max) {
                max = tab1[i];
            }
        }
    }
}

void Properties(int tab[], char oper[], double& avg, double* avgharmonic, int& min, int* max, int& power) {
    int j = 0;
    for(int i=0; oper[i] != 0; i++) {
        if(oper[i] == 'a') {
            avg = Arithmetic(tab);
        }
        if(oper[i] == 'h') {
            *avgharmonic = Harmonic(tab);
        }
        if(oper[i] == 'm') {
            MinMax(tab, &min, *max);
        }
        if(oper[i] == 'c') {
            Cardinality(tab,&power);
        }
    }
}
