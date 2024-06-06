// Jakub Stec
#include <iostream>
#include <string>

using namespace std;

class PLAYER_CLASS {
    friend class CAESAR_CLASS;
    friend class ARENA_CLASS;
    friend class HUMAN_CLASS;
    friend class BEAST_CLASS;
    friend class BERSERKER_CLASS;
    friend class SQUAD_CLASS;
private:
    unsigned int maxHealth;
    unsigned int currentHealth;
    unsigned int attack;
    unsigned int agility;
public:
    PLAYER_CLASS(unsigned int maxHealth, unsigned int currentHealth, unsigned int attack, unsigned int agility) {
        this->maxHealth = maxHealth;
        this->currentHealth = currentHealth;
        this->attack = attack;
        this->agility = agility;
    }

    PLAYER_CLASS() {
        this->maxHealth = 0;
        this->currentHealth = 0;
        this->attack = 0;
        this->agility = 0;
    };

    virtual unsigned int getMaxHealth() {
        return this->maxHealth;
    }

    virtual unsigned int getCurrentHealth() {
        return this->currentHealth;
    }

    virtual string get_id() {
        return "test";
    }

    virtual unsigned int getRemainingHealth() = 0;
    virtual unsigned int getDamage() = 0;
    virtual unsigned int getAgility() = 0;

    virtual void takeDamage(unsigned int damageTaken) = 0;
    virtual void applyWinnerReward() = 0;
    virtual void cure() = 0;
    virtual void printParams() = 0;

protected:
    virtual void die() {
        this->currentHealth = 0;
    }
};

class CAESAR_CLASS {
public:
    unsigned int numOfAttacks;
    unsigned int numOfJudges;
    void judgeDeathOrLife(PLAYER_CLASS* player) {
        this->numOfJudges++;
        if(this->numOfJudges % 3 == 0 && this->numOfAttacks % 2 == 0) {
            player->die();
            this->numOfJudges = 0;
        }
    }
};

class ARENA_CLASS {
private:
    CAESAR_CLASS* caesar;

public:
    ARENA_CLASS(CAESAR_CLASS* caesar) {
        this->caesar = caesar;
        this->caesar->numOfJudges = 0;
        this->caesar->numOfAttacks = 0;
    }
    void fight(PLAYER_CLASS* p1, PLAYER_CLASS* p2) {
        caesar->numOfAttacks = 0;
        if(p1->getRemainingHealth() > 0 && p2->getRemainingHealth() > 0) {

            if(p2->getAgility() > p1->getAgility()) {
                PLAYER_CLASS* temporary = p2;
                p2 = p1;
                p1 = temporary;
            }

            p1->printParams();
            p2->printParams();

            while(caesar->numOfAttacks < 40) {
                if(p1->getRemainingHealth() > 0) {
                    p2->takeDamage(p1->getDamage());
                    p2->printParams();
                    (caesar->numOfAttacks)++;
                }
                if(p2->getRemainingHealth() == 0) {
                    p2->die();
                }
                if(p2->getRemainingHealth() < 10) {
                    break;
                }
                if(p2->getRemainingHealth() > 0) {
                    p1->takeDamage(p2->getDamage());
                    p1->printParams();
                    (caesar->numOfAttacks)++;
                }
                if(p1->getRemainingHealth() == 0) {
                    p1->die();
                }
                if(p1->getRemainingHealth() < 10) {
                    break;
                }
            }
            if(p1->getRemainingHealth() == 0) {
                p1->die();
            }
            if(p2->getRemainingHealth() == 0) {
                p2->die();
            }

            if(p1->getRemainingHealth() > 0) {
                caesar->judgeDeathOrLife(p1);
                p1->printParams();
            }
            if(p2->getRemainingHealth() > 0) {
                caesar->judgeDeathOrLife(p2);
                p2->printParams();
            }
            if(p1->getRemainingHealth() > 0) {
                p1->applyWinnerReward();
                p1->cure();
            }
            if(p2->getRemainingHealth() > 0) {
                p2->applyWinnerReward();
                p2->cure();
            }
            p1->printParams();
            p2->printParams();
        }
    }
};

class HUMAN_CLASS : public virtual PLAYER_CLASS {
private:
    string id;
    unsigned int shield;

public:
    HUMAN_CLASS(string id) : PLAYER_CLASS(200,200,30,10), id(id), shield(10) {};
    string get_id() {
        return this->id;
    }

    unsigned int get_shield() {
        return this->shield;
    }

    void set_shield(unsigned int givenShield) {
        this->shield = givenShield;
    }

    unsigned int getRemainingHealth() {
        unsigned int remainingHealth = (this->currentHealth * 100) / this->maxHealth;
        return remainingHealth;
    }

    unsigned int getDamage() {
        return this->attack;
    }

    unsigned int getAgility() {
        return this->agility;
    }

    void takeDamage(unsigned int damageTaken) {
        unsigned int damage;
        if(damageTaken < this->shield + this->agility) {
            damage = 0;
        } else {
            damage = damageTaken - this->shield - this->agility;
        }
        if(damage < this->currentHealth) {
            this->currentHealth -= damage;
        }
        else {
            this->die();
            //this->currentHealth = 0;
        }
    }


    void applyWinnerReward() {
        this->attack += 2;
        this->agility += 2;
    }

    void cure() {
        this->currentHealth = this->maxHealth;
    }

    void printParams() {
        if(this->currentHealth > 0) {
            cout << this->id << ":" << this->maxHealth << ":" << this->currentHealth << ":" << this->getRemainingHealth() << "%:" << this->attack << ":" << this->agility <<":" << this->shield << endl;
        }
        else {
            cout << this->id << ":R.I.P." << endl;
        }
    }
};

class BEAST_CLASS : public virtual PLAYER_CLASS {
private:
    string id;
public:
    BEAST_CLASS(string id) : id(id), PLAYER_CLASS(150,150,40,20) {};

    string get_id() {
        return this->id;
    }

    unsigned int getRemainingHealth() {
        unsigned int remainingHealth = (this->currentHealth * 100) / this->maxHealth;
        return remainingHealth;
    }

    unsigned int getDamage() {
        if(this->getRemainingHealth() < 25) {
            return this->attack*2;
        }
        else {
            return this->attack;
        }
    }

    unsigned int getAgility() {
        return this->agility;
    }

    void takeDamage(unsigned int damageTaken) {
        unsigned int damage;
        if(damageTaken < (this->agility/2)) {
            damage = 0;
        }
        else {
            damage = damageTaken - (this->agility/2);
        }
        if(damage < this->currentHealth) {
            this->currentHealth -= damage;
        }
        else {
            this->die();
            //this->currentHealth = 0;
        }
    }

    void applyWinnerReward() {
        this->attack += 2;
        this->agility += 2;
    }

    void cure() {
        this->currentHealth = this->maxHealth;
    }

    void printParams() {
        unsigned int beastAttack = attack;
        if(this->getRemainingHealth() < 25) {
            beastAttack = attack*2;
        }
        if(this->currentHealth > 0) {
            cout << this->id << ":" << this->maxHealth << ":" << this->currentHealth << ":" << this->getRemainingHealth() << "%:" << beastAttack << ":" << this->agility << endl;
        }
        else {
            cout << this->id << ":R.I.P." << endl;
        }
    }
};

class BERSERKER_CLASS : public HUMAN_CLASS, public BEAST_CLASS {
private:
    bool beast;
public:
    BERSERKER_CLASS(string human_id, string beast_id) : PLAYER_CLASS(200,200,35,5), HUMAN_CLASS(human_id), BEAST_CLASS(beast_id){
        HUMAN_CLASS::set_shield(15);
        this->beast = false;
    }

    string get_id() {
        if(this->beast) {
            return BEAST_CLASS::get_id();
        }
        else {
            return HUMAN_CLASS::get_id();
        }
    }

    void turnIntoBeast() {
        if(!this->beast) {
            if(HUMAN_CLASS::getRemainingHealth() < 25) {
                this->beast = true;
            }
        }
    }

    void turnIntoHuman() {
        this->beast = false;
    }

    unsigned int getRemainingHealth() {
        unsigned int remainingHealth = (this->currentHealth * 100) / this->maxHealth;
        return remainingHealth;
    }

    unsigned int getDamage() {
        turnIntoBeast();
        if(beast) {
            return BEAST_CLASS::getDamage();
        } else {
            return HUMAN_CLASS::getDamage();
        }
    }

    unsigned int getAgility() {
        turnIntoBeast();
        if(beast) {
            return BEAST_CLASS::getAgility();
        } else {
            return HUMAN_CLASS::getAgility();
        }
    }

    void takeDamage(unsigned int damageTaken) {
        turnIntoBeast();
        if(beast) {
            BEAST_CLASS::takeDamage(damageTaken);
            turnIntoHuman();
        } else {
            HUMAN_CLASS::takeDamage(damageTaken);
        }

    }

    void applyWinnerReward() {
        this->attack += 2;
        this->agility += 2;
    }

    void cure() {
        this->currentHealth = this->maxHealth;
        this->beast = false;
    }

    void printParams() {
        turnIntoBeast();
        if(getRemainingHealth() == 0) {
            turnIntoHuman();
        }
        if(beast) {
            BEAST_CLASS::printParams();
        }
        else {
            HUMAN_CLASS::printParams();
        }
    }

    void die() {
        this->currentHealth = 0;
        this->beast = false;
    }
};

class Node {
private:
    PLAYER_CLASS* player;
    Node* next;
    Node* prev;
public:
    Node(PLAYER_CLASS* p) {
        this->player = p;
        this->next = NULL;
        this->prev = NULL;
    }

    PLAYER_CLASS* getPlayer() {
        return this->player;
    }

    void setPlayer(PLAYER_CLASS* p) {
        this->player = p;
    }

    Node* getNext() {
        return this->next;
    }

    Node* getPrev() {
        return this->prev;
    }

    void setNext(Node* n) {
        this->next = n;
    }

    void setPrev(Node* n) {
        this->prev = n;
    }

};

class SQUAD_CLASS : public virtual PLAYER_CLASS {
private:
    Node* head;
    unsigned int size;
    string squadID;
public:
    SQUAD_CLASS(string squadName) : PLAYER_CLASS(0,0,0,0){
        this->squadID = squadName;
        this->head = NULL;
        this->size = 0;
    }

    ~SQUAD_CLASS() {
        Node* current = this->head;
        while (current != NULL) {
            Node* next = current->getNext();
            delete current;
            current = next;
        }
        this->head = NULL;
    }

    unsigned int getSize() {
        return this->size;
    }

    void addPlayer(PLAYER_CLASS* player) {
        if(player->getRemainingHealth() != 0) {
            Node* current = this->head;
            while(current != NULL) {
                if(current->getPlayer() == player) {
                    return;
                }
                current = current->getNext();
            }
            this->size++;
            Node* newNode = new Node(player);
            if (this->head == NULL) {
                this->head = newNode;
            } else {
                newNode->setNext(this->head);
                this->head->setPrev(newNode);
                this->head = newNode;
            }
        }
    }

    void removePlayer(PLAYER_CLASS* player) {
        if(this->head == NULL) {
            return;
        }

        Node *current = this->head;
        Node *prev = NULL;
        while (current != NULL) {
            if (current->getPlayer() == player) {
                if(prev == NULL) {
                    this->head = current->getNext();
                    if(this->head != NULL) {
                        this->head->setPrev(NULL);
                    }
                } else {
                    prev->setNext(current->getNext());
                    if(current->getNext() != NULL) {
                        current->getNext()->setPrev(prev);
                    }
                }
                this->size--;
                delete current;
                return;
            }
            prev = current;
            current = current->getNext();
        }
    }

    unsigned int getAgility() {
        unsigned int smallestAgility = -1;
        unsigned int tmp;
        Node* current = this->head;
        while(current != NULL) {
            tmp = current->getPlayer()->getAgility();
            if(tmp < smallestAgility) {
                smallestAgility = tmp;
            }
            current = current->getNext();
        }
        return smallestAgility;
    }

    unsigned int getDamage() {
        unsigned int sumOfDamage = 0;
        Node* current = this->head;
        while(current != NULL) {
            sumOfDamage += current->getPlayer()->getDamage();
            current = current->getNext();
        }
        return sumOfDamage;
    }

    void takeDamage(unsigned int damageTaken) {
        unsigned int damage = damageTaken / this->size;
        Node* current = this->head;
        Node* tmp;
        while(current != NULL) {
            current->getPlayer()->takeDamage(damage);
            if(current->getPlayer()->getRemainingHealth() == 0) {
                tmp = current->getNext();
                removePlayer(current->getPlayer());
                current = tmp;
            }
            else {
                current = current->getNext();
            }
        }
    }

    unsigned int getRemainingHealth() {
        unsigned int biggestHealth = 0;
        unsigned int tmp;
        Node* current = this->head;
        while(current != NULL) {
            tmp = current->getPlayer()->getRemainingHealth();
            if(tmp > biggestHealth) {
                biggestHealth = tmp;
            }
            current = current->getNext();
        }
        return biggestHealth;
    }

    bool comparePlayers(PLAYER_CLASS* p1, PLAYER_CLASS* p2) {
        if (p1->get_id() == p2->get_id()) {
            if (p1->getMaxHealth() == p2->getMaxHealth()) {
                if (p1->getCurrentHealth() == p2->getCurrentHealth()) {
                    if (p1->getRemainingHealth() == p2->getRemainingHealth()) {
                        if (p1->getDamage() == p2->getDamage()) {
                            if(p1->getAgility() > p2->getAgility()) {
                                return 1;
                            }
                            else {
                                return 0;
                            }
                        }
                        if(p1->getDamage() > p2->getDamage()) {
                            return 1;
                        }
                        else {
                            return 0;
                        }
                    }
                    if(p1->getRemainingHealth() > p2->getRemainingHealth()) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
                if(p1->getCurrentHealth() > p2->getCurrentHealth()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
            if(p1->getMaxHealth() > p2->getMaxHealth()) {
                return 1;
            }
            else {
                return 0;
            }
        }
        if(p1->get_id() > p2->get_id()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    void applyWinnerReward() {
        Node* current = this->head;
        while(current != NULL) {
            current->getPlayer()->applyWinnerReward();
            current = current->getNext();
        }
    }

    void cure() {
        Node* current = this->head;
        while(current != NULL) {
            current->getPlayer()->cure();
            current = current->getNext();
        }
    }

    void bubbleSort() {
        Node* current;
        bool swapped = true;
        while(swapped) {
            swapped = false;
            current = this->head;
            while (current != NULL && current->getNext() != NULL) {
                if (comparePlayers(current->getPlayer(), current->getNext()->getPlayer())) {
                    PLAYER_CLASS* temp = current->getPlayer();
                    current->setPlayer(current->getNext()->getPlayer());
                    current->getNext()->setPlayer(temp);
                    swapped = true;
                }
                current = current->getNext();
            }
        }
    }

    void printParams() {
        bubbleSort();
        Node* current = this->head;
        if(this->size == 0) {
            cout << this->squadID << ":nemo" << endl;
            return;
        }
        cout << this->squadID << ":" << this->size << ":" << this->getRemainingHealth() << "%:" << this->getDamage() << ":" << this->getAgility() << endl;
        while (current != NULL) {
            current->getPlayer()->printParams();
            current = current->getNext();
        }
    }
};
