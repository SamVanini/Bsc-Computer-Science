import os
import psycopg2
import re
import decimal
import datetime

pattern = "^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"

def clear():
    """ Clear the console """
    os.system("clear")

def toDict(date, description, amount):
    """ If all the requirements are met, this function will create the dictionary """
    if checkDescription(description) and isinstance(description, str) and isinstance(amount, decimal.Decimal):
        return {'Data': date, 'Voce': description, 'Importo': amount}
    else:
        print("Operation aborted, imput type doesn't meet the requirements !")

def checkDateDescription(date, description):
    """ Check if the date and the description match the requirements """
    if checkDescription(description) and re.match(pattern, date):
        return True
    return False
    
def checkDescription(description):
    """ Check if the description meets the requirements """
    if isinstance(description, str):
        return True 
    return False
    
def readDatabase():
    """ Select all the records of the database """
    result = list()
    connector = psycopg2.connect(database = "postgres" , user = "postgres", password = "password", port ="5432")

    with connector.cursor() as cur:
        cur.execute("SELECT * FROM Spese;")

        if cur.rowcount > -1:
            result = cur.fetchall()
        connector.commit()
    connector.close()

    return result

def insertIntoDatabase(date, description, amount):
    """ Insert a new record into the database """
    connector = psycopg2.connect(database = "postgres" , user = "postgres", password = "password", port ="5432")

    with connector.cursor() as cur:
        r = cur.execute("INSERT INTO Spese(Data, Voce, Importo) VALUES (%s, %s, %s)", (date, description, amount, ))

        connector.commit()
    connector.close()

def deleteFromDatabase(date, description):
    """ Delete a record from the database """
    connector = psycopg2.connect(database = "postgres" , user = "postgres", password = "password", port ="5432")

    with connector.cursor() as cur:
        cur.execute("DELETE FROM Spese WHERE Data = %s AND Voce = %s", (date, description, ))

        connector.commit()
    connector.close()

class Spese:
    """ This class repesents the set of expenditures"""
    def makeKey(date, description):
        """ Create a key for an entry """
        return str(date) + " % " + description

    def __init__(self, register = dict(), instant = datetime.datetime.now()):
        self.register = register
        self.modified = instant

    def __add__ (self, date, description, amount):
        """ Private method, used to initialize the class"""
        self.register[Spese.makeKey(date, description)] = toDict(date, description, amount)
        self.modified = datetime.datetime.now()
        
    def add(self, description, amount, date = datetime.datetime.now()):
        """ Add a new element"""
        self.register[Spese.makeKey(date, description)] = toDict(date, description, amount)
        self.modified = datetime.datetime.now()
        insertIntoDatabase(date, description, amount)

    def delete(self, date, description):
        """ Delete an element """
        del self.register[Spese.makeKey(date, description)]
        self.modified = datetime.datetime.now()
        deleteFromDatabase(str(date), description)

    def get(self, date, description):
        """ Get an element """
        return self.register[Spese.makeKey(date, description)]

    def getall(self):
        """ Get all the items contained in the class"""
        return self.register.items()

def main():
    op = -1
    while int(op) != 0:
        if int(op) == -1:
            print("Welcome to the table Spese management tool !")
            print("Loading your data, please wait ...")
            register = Spese()
            historical = readDatabase()
            for date, description, amount in historical:
                register.__add__(date, description, amount)
            
        print("Menu :")
        print("1 - Add a new entry to Spese")
        print("2 - Show all the records")
        print("3 - Delete an entry")
        print("Type 0 to exit")
        op = input("Please, type your choice : ")
        
        if int(op) == 1:
            description = input("Insert the description of the new record : ")
            amount = input("Insert the amount of the new record : ")
            amount = decimal.Decimal(amount)
            register.add(description, amount)
        elif int(op) == 2:
            print("-------------------------------------------------------------")
            print("|    Date    |          Description          |    Amount    |")
            print("-------------------------------------------------------------")
            reg = register.getall()
            for key, value in reg:
                date = value['Data']
                description = value['Voce']
                amount = value['Importo']
                print("| " + str(date) + " |  " + str(description) + "               |     " + str(amount) + "     |")

            print("")
        elif int(op) == 3: 
            date = input("Insert the date of the record to delete : ")
            description = input("Insert the description of the record to delete : ")
            if checkDateDescription(date, description):
                register.delete(date, description)
            else:
                print("Operation aborted, type mismatch !")
        elif int(op) == 0:
            print("Shut down activated")
        else:
            print("YOU MORON ! READ ME BEFORE TYPING SOMETHING")

        clear()

main()